package com.mypay.banking.adapter.in.web;

import com.mypay.banking.application.port.in.RequestFirmBankingCommand;
import com.mypay.banking.application.port.in.RequestFirmBankingUseCase;
import com.mypay.banking.application.port.in.UpdateFirmbankingCommand;
import com.mypay.banking.application.port.in.UpdateFirmbankingUseCase;
import com.mypay.banking.domain.FirmBankingRequest;
import com.mypay.common.annotation.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmBankingController {

    private final RequestFirmBankingUseCase requestFirmBankingUseCase;
    private final UpdateFirmbankingUseCase updateFirmbankingUseCase;

    @PostMapping("/banking/firmbanking/request")
    public FirmBankingRequest requestFirmBanking(@RequestBody RequestFirmBankingRequest request) {
        RequestFirmBankingCommand command = RequestFirmBankingCommand.builder()
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();

        return requestFirmBankingUseCase.requestFirmBanking(command);
    }

    @PostMapping(path = "/banking/firmbanking/request-eda")
    void requestFirmbankingByEvent(@RequestBody RequestFirmBankingRequest request) {
        RequestFirmBankingCommand command = RequestFirmBankingCommand.builder()
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();

        requestFirmBankingUseCase.requestFirmBankingByEvent(command);
    }

    @PutMapping(path = "/banking/firmbanking/update-eda")
    void updateFirmBankingByEvent(@RequestBody UpdateFirmbankingRequest request) {
        UpdateFirmbankingCommand command = UpdateFirmbankingCommand.builder()
                .firmbankingAggregateIdentifier(request.getFirmbankingRequestAggregateIdentifier())
                .firmbankingStatus(request.getStatus())
                .build();

        updateFirmbankingUseCase.updateFirmBankingByEvent(command);
    }

}
