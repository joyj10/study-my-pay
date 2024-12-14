package com.mypay.remittanceservice.application.port.in;

import com.mypay.remittanceservice.domain.RemittanceRequest;

public interface RequestRemittanceUseCase {
    RemittanceRequest requestRemittance(RequestRemittanceCommand command);
}
