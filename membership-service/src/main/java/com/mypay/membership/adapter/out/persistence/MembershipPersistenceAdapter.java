package com.mypay.membership.adapter.out.persistence;

import com.mypay.membership.application.port.out.FindMembershipPort;
import com.mypay.membership.application.port.out.RegisterMembershipPort;
import com.mypay.membership.domain.Membership;
import common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName,
                                                Membership.MembershipEmail membershipEmail,
                                                Membership.MembershipAddress membershipAddress,
                                                Membership.MembershipIsValid membershipIsValid,
                                                Membership.MembershipIsCorp membershipIsCorp) {
        return membershipRepository.save(
                new MembershipJpaEntity(
                        membershipName.getNameValue(),
                        membershipEmail.getEmailValue(),
                        membershipAddress.getAddressValue(),
                        membershipIsValid.isValidValue(),
                        membershipIsCorp.isCorpValue()
                )
        );
    }

    @Override
    public Optional<MembershipJpaEntity> findMembership(Membership.MembershipId membershipId) {
        return membershipRepository.findById(Long.parseLong(membershipId.getMembershipId()));
    }
}
