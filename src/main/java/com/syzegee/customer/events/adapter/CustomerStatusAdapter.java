package com.syzegee.customer.events.adapter;

import com.syzegee.customer.events.entity.CustomerStatus;
import com.syzegee.customer.events.enums.CustomerStatusEnum;
import com.syzegee.customer.events.repository.CustomerStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class CustomerStatusAdapter {

    @Autowired
    private CustomerStatusRepository customerStatusRepository;

    @Transactional
    public void updateAllCustomerStatuses(CustomerStatus customerStatus) {
        CustomerStatusEnum.UPDATE_CUSTOMER_STATUS.updateStatus(customerStatus);
        customerStatusRepository.save(customerStatus);
    }

    @Transactional
    public void updateOnboardCustomerStatus(CustomerStatus customerStatus) {
        CustomerStatusEnum.UPDATE_ONBOARD_STATUS.updateStatus(customerStatus);
        customerStatusRepository.save(customerStatus);
    }

    @Transactional
    public void updateCmsSiteCustomerStatus(CustomerStatus customerStatus) {
        CustomerStatusEnum.UPDATE_CMS_SITE_STATUS.updateStatus(customerStatus);
        customerStatusRepository.save(customerStatus);
    }

    @Transactional
    public void updateMarketingAutomationStatus(CustomerStatus customerStatus) {
        CustomerStatusEnum.UPDATE_MARKETING_AUTOMATION_STATUS.updateStatus(customerStatus);
        customerStatusRepository.save(customerStatus);
    }


}
