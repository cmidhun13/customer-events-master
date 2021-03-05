package com.syzegee.customer.events.domain;

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

}
