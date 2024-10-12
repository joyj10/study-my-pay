package com.mypay.membership.application.port.in;

import com.mypay.membership.domain.Membership;
import common.UseCase;
import org.apache.coyote.BadRequestException;

@UseCase
public interface ModifyMembershipUseCase {
    Membership modifyMembership(ModifyMembershipCommand command);
}
