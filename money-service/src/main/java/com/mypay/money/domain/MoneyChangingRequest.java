package com.mypay.money.domain;

import com.mypay.money.code.MoneyChangingStatus;
import com.mypay.money.code.MoneyChangingType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.time.DateTimeException;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyChangingRequest {

    private final String moneyChangingRequestId;

    private final String targetMembershipId;    // 어떤 고객의 증액/감액 요청했는지의 멤버 정보

    private final MoneyChangingType changingType;    // 요청 타입 enum

    private final int changingMoneyAMount;      // 증액 또는 감액 요청 금액

    private final MoneyChangingStatus changingMoneyStatus;      // 머니 변액 요청에 대한 상태 enum

    private final String uuid;

    private final LocalDateTime createdAt;


    public static MoneyChangingRequest generateMoneyChangingRequest (
            MoneyChangingRequestId moneyChangingRequestId,
            TargetMembershipId targetMembershipId,
            ChangingType changingType,
            ChangingMoneyAmount changingMoneyAmount,
            ChangingMoneyStatus changingMoneyStatus,
            String uuid
    ){
        return new MoneyChangingRequest(
                moneyChangingRequestId.getMoneyChangingRequestId(),
                targetMembershipId.getTargetMembershipId(),
                changingType.getChangingType(),
                changingMoneyAmount.getChangingMoneyAmount(),
                changingMoneyStatus.getChangingMoneyStatus(),
                uuid,
                LocalDateTime.now()
        );
    }

    @Value
    public static class MoneyChangingRequestId {
        public MoneyChangingRequestId(String value) {
            this.moneyChangingRequestId = value;
        }
        String moneyChangingRequestId ;
    }

    @Value
    public static class TargetMembershipId {
        public TargetMembershipId(String value) {
            this.targetMembershipId = value;
        }
        String targetMembershipId ;
    }

    @Value
    public static class ChangingType {
        public ChangingType(MoneyChangingType value) {
            this.changingType = value;
        }
        MoneyChangingType changingType ;
    }

    @Value
    public static class ChangingMoneyAmount {
        public ChangingMoneyAmount(int value) {
            this.changingMoneyAmount = value;
        }
        int changingMoneyAmount ;
    }

    @Value
    public static class ChangingMoneyStatus {
        public ChangingMoneyStatus(MoneyChangingStatus value) {
            this.changingMoneyStatus = value;
        }
        MoneyChangingStatus changingMoneyStatus ;
    }

    @Value
    public static class Uuid {
        public Uuid(String uuid) {
            this.uuid = uuid;
        }
        String uuid ;
    }
}
