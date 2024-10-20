package com.mypay.banking.application.service;

import com.mypay.banking.adapter.out.persistence.MembershipJpaEntity;
import com.mypay.banking.adapter.out.persistence.MembershipMapper;
import com.mypay.banking.application.port.in.FindMembershipCommand;
import com.mypay.banking.application.port.in.FindMembershipUseCase;
import com.mypay.banking.application.port.out.FindMembershipPort;
import com.mypay.banking.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FindMembershipService implements FindMembershipUseCase {

    private final FindMembershipPort findMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership findMembership(FindMembershipCommand command) throws BadRequestException {
        MembershipJpaEntity entity = findMembershipPort.findMembership(new Membership.MembershipId(command.getMembershipId()))
                .orElseThrow(() -> new BadRequestException("Membership not found with ID: " + command.getMembershipId()));
        return membershipMapper.mapToDomainEntity(entity);
    }

}
