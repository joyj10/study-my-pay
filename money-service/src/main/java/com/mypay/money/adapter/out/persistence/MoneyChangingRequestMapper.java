package com.mypay.money.adapter.out.persistence;

import com.mypay.money.code.MoneyChangingStatus;
import com.mypay.money.code.MoneyChangingType;
import com.mypay.money.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

@Component
public class MoneyChangingRequestMapper {
    public MoneyChangingRequest mapToDomainEntity(MoneyChangingRequestJpaEntity entity) {
        return MoneyChangingRequest.generateMoneyChangingRequest(
                new MoneyChangingRequest.MoneyChangingRequestId(entity.getMoneyChangingRequestId() + ""),
                new MoneyChangingRequest.TargetMembershipId(entity.getTargetMembershipId()),
                new MoneyChangingRequest.ChangingType(MoneyChangingType.from(entity.getMoneyChangingType())),
                new MoneyChangingRequest.ChangingMoneyAmount(entity.getMoneyAmount()),
                new MoneyChangingRequest.ChangingMoneyStatus(MoneyChangingStatus.from(entity.getChangingMoneyStatus())),
                entity.getUuid()
        );
    }
}
