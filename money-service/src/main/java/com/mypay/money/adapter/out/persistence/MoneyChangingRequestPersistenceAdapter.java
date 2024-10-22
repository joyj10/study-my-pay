package com.mypay.money.adapter.out.persistence;

import com.mypay.common.PersistenceAdapter;
import com.mypay.money.application.port.out.IncreaseMoneyPort;
import com.mypay.money.domain.MemberMoney;
import com.mypay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort {

    private final SpringDataMoneyChangingRequestRepository moneyChangingRequestRepository;
    private final SpringDataMemberMoneyRepository memberMoneyRepository;

    @Override
    public MoneyChangingRequestJpaEntity createMoneyChangingRequest(MoneyChangingRequest.TargetMembershipId targetMembershipId,
                                                                    MoneyChangingRequest.ChangingType changingType,
                                                                    MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount,
                                                                    MoneyChangingRequest.ChangingMoneyStatus changingMoneyStatus,
                                                                    MoneyChangingRequest.Uuid uuid) {
        return moneyChangingRequestRepository.save(
                new MoneyChangingRequestJpaEntity(
                        targetMembershipId.getTargetMembershipId(),
                        changingType.getChangingType().getCode(),
                        changingMoneyAmount.getChangingMoneyAmount(),
                        changingMoneyStatus.getChangingMoneyStatus().getCode(),
                        uuid.getUuid()
                )
        );
    }

    @Override
    public MemberMoneyJpaEntity increaseMoney(MemberMoney.MembershipId memberId,
                                              int increaseMoneyAmount) {
        long membershipId = Long.parseLong(memberId.getMembershipId());
        List<MemberMoneyJpaEntity> memberMoneyEntities = memberMoneyRepository.findByMembershipId(membershipId);

        MemberMoneyJpaEntity entity;
        if (memberMoneyEntities.isEmpty()) {
            entity = new MemberMoneyJpaEntity(membershipId, increaseMoneyAmount);
        } else {
            entity = memberMoneyEntities.get(0);
            entity.setBalance(entity.getBalance() + increaseMoneyAmount);
        }

        return memberMoneyRepository.save(entity);
    }
}
