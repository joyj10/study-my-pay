package com.mypay.banking.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "request_firm_banking")
@AllArgsConstructor
@NoArgsConstructor
public class FirmBankingRequestJpaEntity {

    @Id
    @GeneratedValue
    private Long requestFirmBankingId;

    private String firmBankingRequestId;

    private String fromBankName;

    private String fromBankAccountNumber;

    private String toBankName;

    private String toBankAccountNumber;

    private int moneyAmount;

    private int firmBankingStatus; // 0:요청, 1:완료, 2:실패

    private String uuid;

    private String aggregateIdentifier;

    public FirmBankingRequestJpaEntity(String fromBankName, String fromBankAccountNumber, String toBankName, String toBankAccountNumber, int moneyAmount, int firmBankingStatus, UUID uuid, String aggregateIdentifier) {
        this.fromBankName = fromBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;
        this.firmBankingStatus = firmBankingStatus;
        this.uuid = uuid.toString();
        this.aggregateIdentifier = aggregateIdentifier;
    }

    @Override
    public String toString() {
        return "FirmbankingRequestJpaEntity{" +
                "requestFirmbankingId=" + requestFirmBankingId +
                ", fromBankName='" + fromBankName + '\'' +
                ", fromBankAccountNumber='" + fromBankAccountNumber + '\'' +
                ", toBankName='" + toBankName + '\'' +
                ", toBankAccountNumber='" + toBankAccountNumber + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", firmbankingStatus=" + firmBankingStatus +
                ", uuid='" + uuid + '\'' +
                ", aggregateIdentifier='" + aggregateIdentifier + '\'' +
                '}';
    }
}
