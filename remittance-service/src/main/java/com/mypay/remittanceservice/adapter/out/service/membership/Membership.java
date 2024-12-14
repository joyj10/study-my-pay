package com.mypay.remittanceservice.adapter.out.service.membership;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Membership {
    private String membershipId;
    private  String name;
    private  String email;
    private  String address;
    private  boolean isValid;
    private  boolean isCorp;

    @Override
    public String toString() {
        return "Membership from Remittance {" +
                "membershipId='" + membershipId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", isValid=" + isValid +
                ", isCorp=" + isCorp +
                '}';
    }
}
