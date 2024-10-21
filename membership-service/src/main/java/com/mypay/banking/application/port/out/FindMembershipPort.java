package com.mypay.banking.application.port.out;

import com.mypay.banking.adapter.out.persistence.MembershipJpaEntity;
import com.mypay.banking.domain.Membership;

import java.util.Optional;

public interface FindMembershipPort {
    Optional<MembershipJpaEntity> findMembership(Membership.MembershipId membershipId);
}
