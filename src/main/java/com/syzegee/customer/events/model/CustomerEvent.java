package com.syzegee.customer.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEvent {

    private Long customerId;
    private String state;
    private String correlationId;
    private String customerRequestId;
    private CustomerDetail customerDetail;
    private CustomerAddressDetail custAddressDetail;
    private OrganizationDetail orgDetail;
    private TierDetail tierDetail;

}
