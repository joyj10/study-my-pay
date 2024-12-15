package com.mypay.money.application.port.in;

import com.mypay.money.domain.MoneyChangingRequest;
import com.mypay.common.annotation.UseCase;

@UseCase
public interface IncreaseMoneyRequestUseCase {
    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);
    MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command);
    void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command);
}
