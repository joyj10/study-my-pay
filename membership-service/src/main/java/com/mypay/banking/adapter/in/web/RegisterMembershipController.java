package com.mypay.banking.adapter.in.web;

import com.mypay.common.WebAdapter;
import com.mypay.banking.application.port.in.RegisterMembershipCommand;
import com.mypay.banking.application.port.in.RegisterMembershipUseCase;
import com.mypay.banking.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMembershipController {

    private final RegisterMembershipUseCase registerMembershipUseCase;

    @PostMapping("/membership/register")
    public Membership registerMembership(@RequestBody RegisterMembershipRequest request) {
        // request
        // request -> Command (추상화 계층)
        // UseCase ~~ (request X, command)

        RegisterMembershipCommand command = RegisterMembershipCommand.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .isValid(true)
                .isCorp(request.isCorp())
                .build();

        return registerMembershipUseCase.registerMembership(command);
    }

}
