package com.syzegee.customer.events.repository;

import com.syzegee.customer.events.entity.CustomerInbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface  CustomerInboxRepository extends JpaRepository<CustomerInbox, Long> {

    /**
     * CustomerInbox query for getCustomerRequest only getting object where isActive=true
     *
     * @param customerRequestId
     * @return CustomerInbox
     */
    @Query("from CustomerInbox where customerRequestId=:customerRequestId")
    CustomerInbox getCustomerRequest(@Param("customerRequestId") String customerRequestId);

}
