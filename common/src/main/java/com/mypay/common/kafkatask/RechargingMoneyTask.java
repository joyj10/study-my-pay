package com.mypay.common.kafkatask;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargingMoneyTask { // Increase Money

    private String taskId;
    private String taskName;

    private String membershipId;

    private List<SubTask> subTaskList;

    // 법인 계좌
    private String toBankName;

    // 법인 계좌 번호
    private String toBankAccountNumber;

    private int moneyAmount; // only won
}