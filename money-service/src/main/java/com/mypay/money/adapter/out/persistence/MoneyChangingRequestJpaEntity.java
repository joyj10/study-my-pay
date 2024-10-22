package com.mypay.money.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "money_changing_request")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MoneyChangingRequestJpaEntity {

    @Id
    @GeneratedValue
    private Long moneyChangingRequestId;

    private String targetMembershipId;

    private int moneyChangingType; // 0: 증액, 1: 감액

    private int moneyAmount;

    private int changingMoneyStatus;  // 0: 요청, 1: 성공, 2: 실패, 3:취소

    private String uuid;

    @CreatedDate
    private LocalDateTime createdAt;

    public MoneyChangingRequestJpaEntity(String targetMembershipId, int moneyChangingType, int moneyAmount, int changingMoneyStatus, String uuid) {
        this.targetMembershipId = targetMembershipId;
        this.moneyChangingType = moneyChangingType;
        this.moneyAmount = moneyAmount;
        this.changingMoneyStatus = changingMoneyStatus;
        this.uuid = uuid;
    }
}
