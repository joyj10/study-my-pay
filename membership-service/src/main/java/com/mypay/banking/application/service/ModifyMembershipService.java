package com.mypay.banking.application.service;

import com.mypay.banking.adapter.out.persistence.MembershipJpaEntity;
import com.mypay.banking.adapter.out.persistence.MembershipMapper;
import com.mypay.banking.application.port.in.ModifyMembershipCommand;
import com.mypay.banking.application.port.in.ModifyMembershipUseCase;
import com.mypay.banking.application.port.out.ModifyMembershipPort;
import com.mypay.banking.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ModifyMembershipService implements ModifyMembershipUseCase {

    private final ModifyMembershipPort modifyMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership modifyMembership(ModifyMembershipCommand command) {
        MembershipJpaEntity entity = modifyMembershipPort.modifyMembership(
                new Membership.MembershipId(command.getMembershipId()),
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp())
        );

        return membershipMapper.mapToDomainEntity(entity);
    }
}