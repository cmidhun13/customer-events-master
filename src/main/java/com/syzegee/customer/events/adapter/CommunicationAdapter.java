package com.syzegee.customer.events.adapter;

import com.syzegee.customer.events.model.CustomerActivationResponse;
import com.syzegee.customer.events.model.CustomerCreateDetail;
import com.syzegee.customer.events.util.ActivationTemplate;
import com.syzegee.customer.events.util.MailSender;
import com.syzegee.customer.events.util.SiteInviteTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Sagar
 */
@Slf4j
@Component
public class CommunicationAdapter {

    @Autowired
    private ActivationTemplate activationTemplate;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private SiteInviteTemplate inviteTemplate;

    public void sendCustomerActivationEmail(CustomerActivationResponse activationResponse) throws IOException {
        log.info("Initiate sendCustomerActivationEmail in CommunicationConnector");
        String template = activationTemplate.createActivationTemplate(activationResponse.getEmailId(), activationResponse.getActivation_code());
        try {
            mailSender.emailTemplateInfo(template, activationResponse.getEmailId());
        }
        catch (Exception exception)
        {
            log.error("Unable to generate email :" + exception.getMessage());
        }
        log.info("End of sendCustomerActivationEmail in CommunicationConnector");
    }

    public void sendCustomerSiteInviteEmail(CustomerCreateDetail createDetail) throws IOException {
        log.info("Initiate sendCustomerActivationEmail in CommunicationConnector");
        String template = inviteTemplate.createSiteInviteTemplate(createDetail.getCustomerDetail().getBusinessEmail());
        try {
            mailSender.emailTemplateInfo(template, createDetail.getCustomerDetail().getBusinessEmail());
        }
        catch (Exception exception)
        {
            log.error("Unable to generate email :" + exception.getMessage());
        }
        log.info("End of sendCustomerActivationEmail in CommunicationConnector");
    }
}
