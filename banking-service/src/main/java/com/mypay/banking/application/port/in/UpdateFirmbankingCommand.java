package com.mypay.banking.application.port.in;


import com.mypay.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateFirmbankingCommand extends SelfValidating<RequestFirmBankingCommand> {
    @NotNull
    private final String firmbankingAggregateIdentifier;

    @NotNull
    private final int firmbankingStatus;

}
