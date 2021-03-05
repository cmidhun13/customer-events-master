package com.syzegee.customer.events.domain.crafter;

import lombok.Builder;
import lombok.Data;

/**
 * Sagar
 */
@Data
@Builder
public class CustomerTemplateEvent {

    private Long customerId;
    private String customerTemplateId;
    private String state;
    private String correlationId;
    private String customerRequestId;

}
