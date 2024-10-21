package com.mypay.banking.application.port.in;

import com.mypay.common.UseCase;
import com.mypay.banking.domain.Membership;

@UseCase
public interface ModifyMembershipUseCase {
    Membership modifyMembership(ModifyMembershipCommand command);
}
