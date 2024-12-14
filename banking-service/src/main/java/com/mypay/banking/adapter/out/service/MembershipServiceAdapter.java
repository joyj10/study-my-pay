package com.mypay.banking.adapter.out.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mypay.banking.application.port.out.GetMembershipPort;
import com.mypay.banking.application.port.out.MembershipStatus;
import com.mypay.common.CommonHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MembershipServiceAdapter implements GetMembershipPort {

    private final CommonHttpClient commonHttpClient;

    @Value("${service.membership.url}")
    private final String membershipServiceUrl;


    @Override
    public MembershipStatus getMembership(String membershipId) {
        String url = String.join("/", membershipServiceUrl, "membership", membershipId);
        try {
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();
            // json membership

            ObjectMapper mapper = new ObjectMapper();
            Membership membership = mapper.readValue(jsonResponse, Membership.class);

            if (membership.isValid()) {
                return new MembershipStatus(membership.getMembershipId(), true);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 실제로 http Call
        return null;
    }
}
