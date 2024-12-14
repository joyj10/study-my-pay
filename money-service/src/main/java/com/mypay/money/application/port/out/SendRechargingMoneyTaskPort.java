package com.mypay.money.application.port.out;

import com.mypay.common.kafkatask.RechargingMoneyTask;

public interface SendRechargingMoneyTaskPort {
    void sendRechargingMoneyTaskPort(RechargingMoneyTask task);
}
