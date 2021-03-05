package com.syzegee.customer.events.service;

import com.syzegee.customer.events.adapter.CommunicationAdapter;
import com.syzegee.customer.events.adapter.CustomerAdapter;
import com.syzegee.customer.events.adapter.CustomerInboxAdapter;
import com.syzegee.customer.events.entity.Customer;
import com.syzegee.customer.events.entity.CustomerInbox;
import com.syzegee.customer.events.exception.CustomerRuntimeException;
import com.syzegee.customer.events.model.*;
import com.syzegee.customer.events.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class ActivationService {

    @Autowired
    private CustomerAdapter customerAdapter;

    @Autowired
    private CustomerInboxAdapter customerInboxAdapter;

    @Autowired
    private CommunicationAdapter communicationAdapter;

    @Autowired
    private JsonUtil util;

    private String correlationId = " - CorrelationId: ";

    public CustomerActivationResponse createActivation(CustomerActivationDetails customerActivationDetails, String correlationId) throws IOException {
        log.info("Initiate createActivation in service " + " - CorrelationId: " + correlationId);
        Customer customer = customerAdapter.createActivationDetails(customerActivationDetails, correlationId);
        CustomerActivationResponse activationResponse = CustomerActivationResponse.builder().customerId(customer.getCustomerId())
                .correlationId(customer.getCorrelationId())
                .emailId(customerActivationDetails.getUserDetail().getEmailId())
                .activation_code(customer.getActivationCode()).build();

            communicationAdapter.sendCustomerActivationEmail(activationResponse);

        log.info("End of createActivation in service " + correlationId + correlationId);
        return activationResponse;
    }

    public ActivationValidateResponse createValidateActivation(ActivationValidateDetails activationValidateDetails,
                                                               String correlationId) {
        log.info("Initiate createValidateActivation in service " + correlationId + correlationId);
        ActivationValidateResponse activationValidateResponse = customerAdapter.createValidateActivationDetails
                (activationValidateDetails, correlationId);
        log.info("End of createValidateActivation in service " + " - CorrelationId: " + correlationId);
        return activationValidateResponse;
    }

    public Customer createCustomerRegistration(CustomerEvent event) throws CustomerRuntimeException {
        log.info("Initiate createCustomerRegistration in service" + correlationId + event.getCorrelationId());
        CustomerInbox customerDeatils = customerInboxAdapter.getCustomerRequest(event);
        CustomerCreateDetail createDetailJson = util.getCreateDetailfromJson(customerDeatils);
        Customer customerDetails = customerAdapter.createAllCustomerDetails(createDetailJson, event.getCorrelationId(), event.getCustomerId());
        log.info("End of the createCustomerRegistration in service " + correlationId + event.getCorrelationId());
        return customerDetails;
    }
}
