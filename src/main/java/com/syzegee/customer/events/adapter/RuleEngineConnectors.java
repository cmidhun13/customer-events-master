package com.syzegee.customer.events.adapter;

import com.syzegee.customer.events.domain.CustomerCreateDetail;
import com.syzegee.customer.events.domain.ruleengine.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sagar
 */
@Component
public class RuleEngineConnectors {

    private RestTemplate restTemplate = new RestTemplate();
    private RuleResponseDetails responseDetails;

    @Value("${url.ruleEngine}")
    private String ruleEngineURL;

    public RuleResponseDetails createProject(CustomerRule ruleProjectDetail) {
        responseDetails = restTemplate.postForObject(
                ruleEngineURL + "/createproject", ruleProjectDetail,
                RuleResponseDetails.class);
        return responseDetails;
    }

    public void createCustomerRuleEvent(CustomerCreateDetail createDetail, Long customerID) {
        List<SyzegeeRuleDtlDetail> syzegeeRuleDtlDetails = new ArrayList<>();
        SyzegeeRuleDtlDetail  ruleDtlDetail1 = SyzegeeRuleDtlDetail.builder().ruleDetailCode("commuPref")
                                                .ruleDetailValue(createDetail.getCustomerDetail().getCommunicationPreferences())
                                                .createdBy("Admin")
                                                .createdDate(new Date())
                                                .build();
        SyzegeeRuleDtlDetail  ruleDtlDetail2 = SyzegeeRuleDtlDetail.builder().ruleDetailCode("enrollMethod")
                                                .ruleDetailValue("email")
                                                .createdBy("Admin")
                                                .createdDate(new Date())
                                                .build();
        syzegeeRuleDtlDetails.add(ruleDtlDetail1);
        syzegeeRuleDtlDetails.add(ruleDtlDetail2);
        List<SyzegeeProjectRulesDetail> syzegeeProjectRulesDetails = new ArrayList<>();
        SyzegeeProjectRulesDetail rulesDetail1 =SyzegeeProjectRulesDetail.builder()
                                                .ruleValue("rule value")
                                                .createdBy("Admin")
                                                .createdDate(new Date())
                                                .build();
        syzegeeProjectRulesDetails.add(rulesDetail1);
        CustomerRule ruleProjectDetail = CustomerRule.builder().customerId(customerID)
                .projectCode("PCode" + customerID)
                .projectName("Customer_" + customerID)
                .projectDesc("customer default rule desc")
                .createdBy("Admin")
                .createdDate(new Date())
                .defaultRuleName(null)
                .ruleDetails(RuleDetails.builder()
                        .ruleNameDetails(SyzegeeRuleDetail.builder()
                                .ruleCode("defaultRuleCode")
                                .ruleName(createDetail.getCustomerRule().getDefaultRuleName())
                                .ruleDesc(createDetail.getCustomerDetail().getCommunicationPreferences())
                                .ruleType("text")
                                .createdBy("Admin")
                                .build())
                        .ruleAttributeDetails(syzegeeRuleDtlDetails)
                        .ruleValueDetails(syzegeeProjectRulesDetails)
                        .build())
                .build();
        this.createProject(ruleProjectDetail);
    }
}
