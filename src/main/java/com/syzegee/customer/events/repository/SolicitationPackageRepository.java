package com.syzegee.customer.events.repository;

import com.syzegee.customer.events.entity.SolicitationPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Riya Patel
 */
@Repository
public interface SolicitationPackageRepository extends JpaRepository<SolicitationPackage,Long> {
    @Query("from SolicitationPackage where solicitationId=:solicitationId")
    SolicitationPackage getRecordBySoliciationId(@Param("solicitationId") int solicitationId);
    @Query("select distinct solicitationId as solicitationId, solicitationName as solicitationName from SolicitationPackage where customer.customerId=:customerId and isActive=true and :currentDate BETWEEN startFrom AND endDate")
    List<Map<String, Object>> getRecordByCustomerIdAndDate(@Param("customerId") long customerId, @Param("currentDate") Date currentDate);


}
