package com.mypay.remittanceservice.application.port.in;


import com.mypay.remittanceservice.domain.RemittanceRequest;

import java.util.List;

public interface FindRemittanceUseCase {
    List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command);
}
