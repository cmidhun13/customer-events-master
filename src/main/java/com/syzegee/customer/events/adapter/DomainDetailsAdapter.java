package com.syzegee.customer.events.adapter;

import com.syzegee.customer.events.entity.Customer;
import com.syzegee.customer.events.entity.DomainDetails;
import com.syzegee.customer.events.model.CustomerDomainDetails;
import com.syzegee.customer.events.model.CustomerDomainStatusUpdateEvent;
import com.syzegee.customer.events.repository.DomainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DomainDetailsAdapter {

    @Autowired
    private DomainRepository domainRepository;

    public void updateDomainDetails(CustomerDomainStatusUpdateEvent event, Optional<Customer> customer) {
        DomainDetails domainDetails = DomainDetails.builder().customerId(customer.get()).correlationId(event.getCorrelationId()).siteName(event.getSiteName())
                .domainName(event.getDomainName())
                .createdBy(event.getCreatedBy())
                .isActive(event.isActive())
                .siteCode(event.getSiteName())
                .siteType(event.getSiteType())
                .siteName(event.getSiteName())
                .state(event.getState())
                .templateCode(event.getTemplateCode())
                .build();
        domainRepository.save(domainDetails);
    }

    public void updateExistingDomainDetails(CustomerDomainDetails customerDomainDetails, Customer customer,String correlationId) {
        List<DomainDetails> domainDetailsList = domainRepository.findBySiteCodeAndCustomerId(customerDomainDetails.getSiteCode(),customer.getCustomerId());
        if(!domainDetailsList.isEmpty())
        {
            List<DomainDetails> updatedDomainDetailsList = domainDetailsList.stream().map(x-> {
                x.setIsActive(Boolean.FALSE);
                x.setCreatedBy(null);
                x.setState("Site Deleted");
                x.setUpdatedBy(customerDomainDetails.getUpdatedBy());
                x.setUpdatedDate(new Date());
                return x;
            }).collect(Collectors.toList());
            updatedDomainDetailsList.forEach(x ->   domainRepository.save(x));
        }
        else {

            DomainDetails details = DomainDetails.builder()
                    .siteCode(customerDomainDetails.getSiteCode())
                    .templateCode(customerDomainDetails.getTemplateCode())
                    .siteName(customerDomainDetails.getSiteName())
                    .siteType(customerDomainDetails.getSiteType())
                    .customerId(customer)
                    .domainName(customerDomainDetails.getDomainName())
                    .logo(customerDomainDetails.getLogo())
                    .tagline(customerDomainDetails.getTagline())
                    .isActive(Boolean.TRUE)
                    .createdBy(customerDomainDetails.getCreatedBy())
                    .createdDate(new Date())
                    .correlationId(correlationId).build();
            domainRepository.save(details);
        }

    }

    /**
     * This method is used to create Tier for the customer
     *
     * @param customerDomainDetails  ,
     * @param correlationId is required to create the tier
     * @return domainDetails
     */
    public DomainDetails createDomainDetails(CustomerDomainDetails customerDomainDetails, Customer customer, String correlationId) {
        log.info("Initiate createDomainDetails in connector " + " - CorrelationId: " + correlationId);
        DomainDetails details = DomainDetails.builder()
                .siteCode(customerDomainDetails.getSiteCode())
                .templateCode(customerDomainDetails.getTemplateCode())
                .siteName(customerDomainDetails.getSiteName())
                .siteType(customerDomainDetails.getSiteType())
                .customerId(customer)
                .domainName(customerDomainDetails.getDomainName())
                .logo(customerDomainDetails.getLogo())
                .tagline(customerDomainDetails.getTagline())
                .isActive(Boolean.TRUE)
                .createdBy(customerDomainDetails.getCreatedBy())
                .createdDate(new Date())
                .updatedBy(customerDomainDetails.getUpdatedBy())
                .updatedDate(new Date())
                .correlationId(correlationId).build();
        DomainDetails domainDetails = domainRepository.save(details);
        log.info("End of createDomainDetails  in connector " + " - CorrelationId: " + correlationId);
        return domainDetails;
    }
}
