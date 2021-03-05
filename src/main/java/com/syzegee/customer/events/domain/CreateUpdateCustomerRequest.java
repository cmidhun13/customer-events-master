package com.syzegee.customer.events.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateCustomerRequest {
	    private Long customerId;
	    private String userId;
}
