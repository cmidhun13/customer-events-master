package com.syzegee.customer.events.enums;

import com.syzegee.customer.events.entity.CustomerStatus;

public enum CustomerStatusEnum implements ICustomerStatus<CustomerStatus>{
    UPDATE_CUSTOMER_STATUS((x)->{
        CustomerStatus customerStatus=(CustomerStatus) x;
        customerStatus.setCmsSiteUpdateStatus(true);
        customerStatus.setOnBoardStatus(true);
     //   customerStatus.setMarketingAutomationStatus(true);
        return customerStatus;
    }),
    UPDATE_CMS_SITE_STATUS((x)->{
        CustomerStatus customerStatus=(CustomerStatus) x;
        customerStatus.setCmsSiteUpdateStatus(true);
        return customerStatus;
    }),
    UPDATE_ONBOARD_STATUS((x)->{
        CustomerStatus customerStatus=(CustomerStatus) x;
        customerStatus.setOnBoardStatus(true);
        return customerStatus;
    }),
    UPDATE_MARKETING_AUTOMATION_STATUS((x)->{
        CustomerStatus customerStatus=(CustomerStatus) x;
        customerStatus.setMarketingAutomationStatus(true);
        return customerStatus;
    });


    private ICustomerStatus<CustomerStatus> iCustomerStatus;

    CustomerStatusEnum(ICustomerStatus iCustomerStatus) {
        this.iCustomerStatus = iCustomerStatus;
    }

    @Override
    public CustomerStatus updateStatus(CustomerStatus x) {
        return iCustomerStatus.updateStatus(x);
    }
}
