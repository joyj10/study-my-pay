package com.mypay.money.adapter.out.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// for banking service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Membership {

    private String membershipId;

    private String name;

    private String email;

    private String address;

    private boolean isValid;

    private boolean isCorp;
}