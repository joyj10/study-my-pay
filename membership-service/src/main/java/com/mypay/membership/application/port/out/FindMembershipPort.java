package com.mypay.membership.application.port.out;

import com.mypay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.mypay.membership.domain.Membership;

import java.util.Optional;

public interface FindMembershipPort {
    Optional<MembershipJpaEntity> findMembership(Membership.MembershipId membershipId);
}
