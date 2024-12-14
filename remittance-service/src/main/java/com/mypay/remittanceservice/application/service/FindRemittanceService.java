package com.mypay.remittanceservice.application.service;

import com.mypay.common.annotation.UseCase;
import com.mypay.remittanceservice.adapter.out.persistence.RemittanceRequestMapper;
import com.mypay.remittanceservice.application.port.in.FindRemittanceCommand;
import com.mypay.remittanceservice.application.port.in.FindRemittanceUseCase;
import com.mypay.remittanceservice.application.port.out.FindRemittancePort;
import com.mypay.remittanceservice.domain.RemittanceRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindRemittanceService implements FindRemittanceUseCase {
    private final FindRemittancePort findRemittancePort;
    private final RemittanceRequestMapper mapper;

    @Override
    public List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command) {
        //
        findRemittancePort.findRemittanceHistory(command);
        return null;
    }
}
