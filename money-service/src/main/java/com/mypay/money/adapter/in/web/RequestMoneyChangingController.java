package com.mypay.money.adapter.in.web;

import com.mypay.common.annotation.WebAdapter;
import com.mypay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.mypay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.mypay.money.code.MoneyChangingResultStatus;
import com.mypay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final MoneyChangingResultDetailMapper moneyChangingResultDetailMapper;
    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;

    @PostMapping("/money/increase")
    public MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequest(command);
        return moneyChangingResultDetailMapper.mapToMoneyChangingResultDetail(moneyChangingRequest);
    }

    @PostMapping(path = "/money/increase-async")
    MoneyChangingResultDetail increaseMoneyChangingRequestAsync(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequestAsync(command);
        return moneyChangingResultDetailMapper.mapToMoneyChangingResultDetail(moneyChangingRequest);
    }

    // 추후 작업
    @PostMapping("/money/decrease")
    public MoneyChangingResultStatus decreaseMoneyChangingRequest(@RequestBody DecreaseMoneyChangingRequest request) {
        return null;
    }

}
