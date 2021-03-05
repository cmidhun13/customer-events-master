package com.syzegee.customer.events.domain.ruleengine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRuleEvent {
    private Long customerId;
    private Long projectId;
    private String state;
    private String correlationId;
    private String customerRequestId;
}
