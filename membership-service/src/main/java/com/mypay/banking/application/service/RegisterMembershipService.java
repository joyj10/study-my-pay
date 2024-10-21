package com.mypay.banking.application.service;

import com.mypay.banking.adapter.out.persistence.MembershipJpaEntity;
import com.mypay.banking.adapter.out.persistence.MembershipMapper;
import com.mypay.banking.application.port.in.RegisterMembershipCommand;
import com.mypay.banking.application.port.in.RegisterMembershipUseCase;
import com.mypay.banking.application.port.out.RegisterMembershipPort;
import com.mypay.banking.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final RegisterMembershipPort registerMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {
        MembershipJpaEntity entity = registerMembershipPort.createMembership(
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp())
        );

        return membershipMapper.mapToDomainEntity(entity);

    }
}
