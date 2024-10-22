package com.mypay.money.application.service;

import com.mypay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.mypay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.mypay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.mypay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.mypay.money.application.port.out.IncreaseMoneyPort;
import com.mypay.money.code.MoneyChangingStatus;
import com.mypay.money.code.MoneyChangingType;
import com.mypay.money.domain.MemberMoney;
import com.mypay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {

    private final IncreaseMoneyPort increaseMoneyPort;
    private final MoneyChangingRequestMapper moneyChangingRequestMapper;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {
        // 머니의 충전(증액) 과정
        // 1. 고객 정보가 정상인지 확인 (멤버)

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

        if(memberMoneyJpaEntity != null) {
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
}
