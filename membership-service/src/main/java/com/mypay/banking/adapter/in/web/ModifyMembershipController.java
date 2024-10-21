package com.mypay.banking.adapter.in.web;

import com.mypay.common.WebAdapter;
import com.mypay.banking.application.port.in.ModifyMembershipCommand;
import com.mypay.banking.application.port.in.ModifyMembershipUseCase;
import com.mypay.banking.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class ModifyMembershipController {

    private final ModifyMembershipUseCase modifyMembershipUseCase;

    @PatchMapping("/membership/{membershipId}")
    public ResponseEntity<Membership> modifyMembershipByMemberId(@PathVariable String membershipId,
                                                                 @RequestBody ModifyMembershipRequest request) {

        ModifyMembershipCommand command = ModifyMembershipCommand.builder()
                .membershipId(membershipId)
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .isValid(request.isValid())
                .isCorp(request.isCorp())
                .build();

        return ResponseEntity.ok(modifyMembershipUseCase.modifyMembership(command));
    }

}
