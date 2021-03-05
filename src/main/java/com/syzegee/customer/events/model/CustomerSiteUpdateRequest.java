package com.syzegee.customer.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CustomerSiteUpdateRequest {
    private CustomerDetail customerDetail;
    private List<BenefitDetail> benefitDetail;
    @Tolerate
    public CustomerSiteUpdateRequest() {

    }
}
