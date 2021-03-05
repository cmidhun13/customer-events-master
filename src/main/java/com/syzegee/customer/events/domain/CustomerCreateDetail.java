package com.syzegee.customer.events.domain;

import com.syzegee.customer.events.domain.crafter.CustomerTemplate;
import com.syzegee.customer.events.domain.ruleengine.CustomerRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;


import java.util.List;

/**
 * @author Sagar
 */
@Data
@Builder
@AllArgsConstructor
public class CustomerCreateDetail {
    private OrganizationDetail orgDetail;
    private CustomerDetail customerDetail;
    private CustomerAddressDetail custAddressDetail;
    private TierDetail tierDetail;
    private List<BenefitDetail> benefitDetail;
    private List<PackagesDetail> packagesDetail;
    private CustomerDomainDetails domainDetails;
    private CustomerRule customerRule;
    private CustomerTemplate customerTemplate;
    private List<SolicitationDetail> solicitationDetail;

    @Tolerate
    public CustomerCreateDetail() {

    }
}
