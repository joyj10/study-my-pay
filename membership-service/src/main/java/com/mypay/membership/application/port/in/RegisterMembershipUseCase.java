package com.mypay.membership.application.port.in;

import com.mypay.membership.adapter.out.persistence.MembershipJpaEntity;
import common.UseCase;

@UseCase
public interface RegisterMembershipUseCase {

    MembershipJpaEntity registerMembership(RegisterMembershipCommand command);
}
