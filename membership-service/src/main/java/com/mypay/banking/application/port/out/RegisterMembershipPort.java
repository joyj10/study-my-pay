package com.mypay.banking.application.port.out;

import com.mypay.banking.adapter.out.persistence.MembershipJpaEntity;
import com.mypay.banking.domain.Membership;

public interface RegisterMembershipPort {
    MembershipJpaEntity createMembership(
            Membership.MembershipName membershipName,
            Membership.MembershipEmail membershipEmail,
            Membership.MembershipAddress membershipAddress,
            Membership.MembershipIsValid membershipIsValid,
            Membership.MembershipIsCorp membershipIsCorp
    );
}
