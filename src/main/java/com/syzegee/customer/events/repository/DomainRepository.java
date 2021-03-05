package com.syzegee.customer.events.repository;


import com.syzegee.customer.events.entity.DomainDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sagar
 */
@Repository
public interface DomainRepository extends JpaRepository<DomainDetails,Long> {

//    @Query("select a from DomainDetails d" +" JOIN FETCH d.customerId c where d.siteCode=:siteCode and c.customerId=:customerId and d.siteName=:siteName")
    @Query(value = "from DomainDetails where siteCode =:siteCode and customerId.customerId =:customerId")
    List<DomainDetails> findBySiteCodeAndCustomerId(String siteCode, Long customerId);


}
