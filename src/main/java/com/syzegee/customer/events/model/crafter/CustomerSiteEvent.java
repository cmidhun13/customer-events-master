package com.syzegee.customer.events.model.crafter;

import com.syzegee.customer.events.model.BenefitDetail;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerSiteEvent {
    private Long customerId;
    private String customerTemplateId;
    private String state;
    private String correlationId;
    private String customerRequestId;
    private List<BenefitDetail> benefitDetailsList;
    private String firsName;
    private String lastName;
    private String groupName;
    private String email;
}
