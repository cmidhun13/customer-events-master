package com.syzegee.customer.events.model.ruleengine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * Ram Prasad
 */
@Builder
@Data
@AllArgsConstructor
public class RuleResponseDetails {
    private long projectId;
    private String correlationId;
    private String message;

    @Tolerate
    public RuleResponseDetails(){}
}
