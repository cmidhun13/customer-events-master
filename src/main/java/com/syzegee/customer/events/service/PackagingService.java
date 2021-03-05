package com.syzegee.customer.events.service;

import com.syzegee.customer.events.adapter.PackagingAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PackagingService {

    @Autowired
    private PackagingAdapter packagingAdapter;

    public List<Map<String, Object>> getSolicitationPackageList(Long customerId) {
        log.info("Initiate getSolicitationPackageList in service" + " - customerId: " + customerId);
        List<Map<String, Object>> solicitationPackages = packagingAdapter.getSolicitationPackageList(customerId);
        //CustomerCreateDetail createDetailJson = util.getCreateDetailfromJson(customerDeatils);
        log.info("End getSolicitationPackageList in service" + " - customerId: " + customerId);
        return solicitationPackages;
    }
}
