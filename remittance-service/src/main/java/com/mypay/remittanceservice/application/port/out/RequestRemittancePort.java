package com.mypay.remittanceservice.application.port.out;


import com.mypay.remittanceservice.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.mypay.remittanceservice.application.port.in.RequestRemittanceCommand;

public interface RequestRemittancePort {
    RemittanceRequestJpaEntity createRemittanceRequestHistory(RequestRemittanceCommand command);
    boolean saveRemittanceRequestHistory(RemittanceRequestJpaEntity entity);
}