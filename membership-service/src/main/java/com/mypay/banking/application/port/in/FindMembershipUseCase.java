package com.mypay.banking.application.port.in;

import com.mypay.common.UseCase;
import com.mypay.banking.domain.Membership;
import org.apache.coyote.BadRequestException;

@UseCase
public interface FindMembershipUseCase {
    Membership findMembership(FindMembershipCommand command) throws BadRequestException;
}
