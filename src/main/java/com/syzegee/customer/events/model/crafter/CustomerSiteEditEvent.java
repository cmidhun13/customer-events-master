package com.syzegee.customer.events.model.crafter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerSiteEditEvent {

    private Long customerId;
    private String customerTemplateId;
    private String state;
    private String correlationId;
    private String customerRequestId;

}
