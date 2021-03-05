package com.syzegee.customer.events.repository;


import com.syzegee.customer.events.entity.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Sagar
 */
@Repository
public interface CustomerUserRepository  extends JpaRepository<CustomerUser,Long> {

    @Query("from CustomerUser where isActive=true and emailId=:emailId")
    CustomerUser getCustomerUserByEmailId(@Param("emailId") String emailId);

    @Query("from CustomerUser where customer_user_id=:customerUserId")
    CustomerUser getCustomerUserById(@Param("customerUserId") Long customerUserId);
}
