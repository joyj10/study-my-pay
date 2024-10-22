package com.mypay.membership.application.port.in;

import com.mypay.common.UseCase;
import com.mypay.membership.domain.Membership;
import org.apache.coyote.BadRequestException;

@UseCase
public interface FindMembershipUseCase {
    Membership findMembership(FindMembershipCommand command) throws BadRequestException;
}
