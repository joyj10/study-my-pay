package com.mypay.membership.application.port.in;

import com.mypay.common.annotation.UseCase;
import com.mypay.membership.domain.Membership;

@UseCase
public interface RegisterMembershipUseCase {
    Membership registerMembership(RegisterMembershipCommand command);
}
