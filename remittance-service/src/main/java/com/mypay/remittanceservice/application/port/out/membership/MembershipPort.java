package com.mypay.remittanceservice.application.port.out.membership;

public interface MembershipPort {

    MembershipStatus getMembershipStatus(String membershipId);
}
