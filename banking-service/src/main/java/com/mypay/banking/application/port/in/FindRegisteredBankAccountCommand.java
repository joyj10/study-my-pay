package com.mypay.banking.application.port.in;

import com.mypay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class FindRegisteredBankAccountCommand extends SelfValidating<FindRegisteredBankAccountCommand> {
    private final String registeredBankAccountId;
}
