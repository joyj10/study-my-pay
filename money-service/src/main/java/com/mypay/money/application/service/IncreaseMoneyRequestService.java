package com.mypay.money.application.service;

import com.mypay.common.kafkatask.RechargingMoneyTask;
import com.mypay.common.kafkatask.SubTask;
import com.mypay.common.util.CountDownLatchManager;
import com.mypay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.mypay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.mypay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.mypay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.mypay.money.application.port.out.GetMembershipPort;
import com.mypay.money.application.port.out.IncreaseMoneyPort;
import com.mypay.money.application.port.out.MembershipStatus;
import com.mypay.money.application.port.out.SendRechargingMoneyTaskPort;
import com.mypay.money.code.MoneyChangingStatus;
import com.mypay.money.code.MoneyChangingType;
import com.mypay.money.domain.MemberMoney;
import com.mypay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {

    private final IncreaseMoneyPort increaseMoneyPort;
    private final MoneyChangingRequestMapper moneyChangingRequestMapper;
    private final GetMembershipPort getMembershipPort;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    private final CountDownLatchManager countDownLatchManager;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {
        // 머니의 충전(증액) 과정
        // 1. 고객 정보가 정상인지 확인 (멤버)
        MembershipStatus membershipStatus = getMembershipPort.getMembership(command.getTargetMembershipId());
        if (!membershipStatus.isValid()) {
            throw new RuntimeException();
        }

        // 2. 고객의 연동된 계좌 존재 및 정상 여부 확인 & 고객 연동 계좌의 잔액이 충분한지 확인 (뱅킹)

        // 3. 법인 계좌 상태 정상 여부 확인 (뱅킹)

        // 4. 증액을 위한 "기록", 요청 상태로 MoneyChangingRequest 생성

        // 5. 펌뱅킹 수행 (고객의 연동된 계좌 -> 페이 법인 계좌로 이동) (뱅킹)

        // 6-1. [결과 정상] 성공으로 MoneyChangingRequest 상태값 변경 후 리턴
        // 성공 시에 멤버의 MemberMoney 값 증액 필요
        MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId())
                ,command.getAmount()
        );

        if (memberMoneyJpaEntity != null) {
            return moneyChangingRequestMapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                            new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                            new MoneyChangingRequest.ChangingType(MoneyChangingType.INCREASING),
                            new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                            new MoneyChangingRequest.ChangingMoneyStatus(MoneyChangingStatus.SUCCEEDED),
                            new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
                    )
            );
        }
        // 6-2. [결과 실패] 실패로 MoneyChangingRequest 상태값 변경 후 리턴

        return null;
    }


    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {
        // 1. SubTask, task 만들기
        // SubTask : 각 서비스에 특정 membershipId 로 Validation 을 하기 위한 Task.
        SubTask validMemberTask = SubTask.builder()
                .subTaskName("validMemberTask : " + "멤버십 유효성 검사")
                .membershipId(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();

        // Banking Sub task
        // Banking Account Validation
        SubTask validBankingAccountTask = SubTask.builder()
                .subTaskName("validBankingAccountTask : " + "뱅킹 계좌 유효성 검사")
                .membershipId(command.getTargetMembershipId())
                .taskType("banking")
                .status("ready")
                .build();
        // Amount Money FirmBanking --> 무조건 ok 받았다고 가정.(간소화 해서 개발)

        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(validMemberTask);
        subTaskList.add(validBankingAccountTask);

        RechargingMoneyTask task = RechargingMoneyTask.builder()
                .taskId(UUID.randomUUID().toString())
                .taskName("Increase Money Task / 머니 충전 Task")
                .subTaskList(subTaskList)
                .moneyAmount(command.getAmount())
                .membershipId(command.getTargetMembershipId())
                .toBankName("mypay")    // 법인 계좌 은행 이름
                .build();

        // 2. kafka Cluster produce : Task Produce
        sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(task);
        countDownLatchManager.addCountDownLatch(task.getTaskId());

        // 3. Wait
        try {
            countDownLatchManager.getCountDownLatch(task.getTaskId()).await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 3-1. task-consumer
        // 등록된 sub-task, status 모두 ok -> task 결과를 Produce

        // 4. 다른 큐에서 Task Result Consume
        // 받은 응답을 다시, countDownLatchManager 통해서 결과 데이터를 받아야 함
        String result = countDownLatchManager.getDataForKey(task.getTaskId());
        if ("success".equals(result)) {
            // 4-1. Consume ok, Logic
            MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                    new MemberMoney.MembershipId(command.getTargetMembershipId())
                    ,command.getAmount()
            );

            if (memberMoneyJpaEntity != null) {
                return moneyChangingRequestMapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                                new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                                new MoneyChangingRequest.ChangingType(MoneyChangingType.INCREASING),
                                new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                                new MoneyChangingRequest.ChangingMoneyStatus(MoneyChangingStatus.SUCCEEDED),
                                new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
                        )
                );
            }
        } else {
            // 4-2. Consume fail, Logic
            return null;
        }

        // 5. Consume OK, Logic
        return null;
    }
}
