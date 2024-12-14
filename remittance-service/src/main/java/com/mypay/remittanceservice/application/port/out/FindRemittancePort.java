package com.mypay.remittanceservice.application.port.out;

import com.mypay.remittanceservice.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.mypay.remittanceservice.application.port.in.FindRemittanceCommand;

import java.util.List;

public interface FindRemittancePort {

    List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command);
}
