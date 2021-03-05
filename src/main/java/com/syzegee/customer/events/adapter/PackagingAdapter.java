package com.syzegee.customer.events.adapter;

import com.syzegee.customer.events.entity.Customer;
import com.syzegee.customer.events.entity.Packages;
import com.syzegee.customer.events.entity.SolicitationPackage;
import com.syzegee.customer.events.entity.Tier;
import com.syzegee.customer.events.exception.CustomerRuntimeException;
import com.syzegee.customer.events.model.PackageBenefitsDetail;
import com.syzegee.customer.events.model.PackagesDetail;
import com.syzegee.customer.events.model.SolicitationDetail;
import com.syzegee.customer.events.repository.PackageRepository;
import com.syzegee.customer.events.repository.SolicitationPackageRepository;
import com.syzegee.customer.events.validator.CustomerValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Slf4j
public class PackagingAdapter {

    @Autowired
    private SolicitationPackageRepository solicitationPackageRepository;

    @Autowired
    private PackageRepository packageRepository;

    private int getUniqueRandomNumberSolicitation(){
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        SolicitationPackage recordBySoliciationId = solicitationPackageRepository.getRecordBySoliciationId(randomNumber);
        if(recordBySoliciationId!=null){
            getUniqueRandomNumberSolicitation();
        }
        return randomNumber;
    }

    public List<Map<String, Object>> getSolicitationPackageList(Long customerId) {
        log.info("Initiate getSolicitationPackageList in connector " + "customer id:" + customerId);
        List<Map<String, Object>> solicitationPackages = solicitationPackageRepository.getRecordByCustomerIdAndDate(customerId,new Date());
        log.info("solicitationPackages object " + solicitationPackages);
        log.info("End getSolicitationPackageList in connector " + "customer id:" + customerId);
        return solicitationPackages;

    }


    /**
     *
     * @param solicitationDetails
     * @param packages
     * @param customer
     * @param correlationId
     * @return
     */
    @Transactional
    public List<SolicitationPackage> createSolicitationPackages(List<SolicitationDetail> solicitationDetails, List<Packages> packages, Customer customer, String correlationId) {
        List<SolicitationPackage> solicitationPackages = new ArrayList<>();
        log.info("Initiate createSolicitationPackages in Connector " + "correlationId:" + correlationId);
        for (SolicitationDetail solicitationDetail : solicitationDetails) {
            int randomNumber = getUniqueRandomNumberSolicitation();
            for (String packageName : solicitationDetail.getPackages()) {
                for (Packages pack : packages) {
                    if (packageName.equals(pack.getPackageName())) {
                        SolicitationPackage solPackage = SolicitationPackage.builder()
                                .solicitationId(randomNumber)
                                .packages(pack)
                                .solicitationName(solicitationDetail.getSolicitationName())
                                .solicitationDescription(solicitationDetail.getSolicitationDesc())
                                .isActive(Boolean.TRUE)
                                .customer(customer)
                                .startFrom(solicitationDetail.getStartDate())
                                .endDate(solicitationDetail.getEndDate())
                                .createdDate(new Date())
                                .build();
                        SolicitationPackage solicitationPackage = solicitationPackageRepository.save(solPackage);
                        solicitationPackages.add(solicitationPackage);
                    }
                }
            }
        }
        log.info("End of createSolicitationPackages in Connector " + "correlationId:" + correlationId);
        return solicitationPackages;
    }

    /**
     * @param packagesDetail
     * @param customer
     * @return
     */
    public Packages updatePackages(PackagesDetail packagesDetail, Customer customer, String correlationId) {
        log.info("inside updatePackages method in connector " + "correlationId:" + correlationId);
        Packages packages = null;
        CustomerValidator validator = new CustomerValidator();

        Long packagesId = packagesDetail.getId();
        validator.validateField("packagesId", packagesId.toString());

        packages = packageRepository.getRecordById(packagesId);
        validator.validateEntity("packages", packages);
        packages.setCorrelationId(correlationId);
        if (packages.getTierId().getCustomerId().getCustomerId().equals(customer.getCustomerId())) {
            if (packagesDetail.getPackageName() != null) {
                packages.setPackageName(packagesDetail.getPackageName());
            }
            packages.setIsActive(true);
            packages.setUpdatedBy(packagesDetail.getUpdatedBy());
            packages.setUpdatedDate(new Date());
            Packages packagesResult = packageRepository.save(packages);
            log.info("end of updatePackages method in connector " + "correlationId:" + correlationId);
            return packagesResult;
        } else {
            log.info("error in updatePackages method in connector " + "correlationId:" + correlationId);
            throw new CustomerRuntimeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    "customerId with given packagesId is not present in record");
        }
    }

    /**
     * This method is used to create the packages for the customer
     *
     * @param packagesDetails
     * @param tier            based on tier it creates the packages
     * @return the packages for customer
     */
    @Transactional
    public List<Packages> createPackages(List<PackagesDetail> packagesDetails, Customer customer, Tier tier, String correlationId) {
        List<Packages> packages = new ArrayList<>();
        log.info("Initiate createPackages in Connector " + "correlationId:" + correlationId);
        for (PackagesDetail packDetail : packagesDetails) {
            Packages pack = Packages.builder().tierId(tier).packageName(packDetail.getPackageName()).createdBy(packDetail.getCreatedBy())
                    .correlationId(correlationId)
                    .customerId(customer)
                    .isActive(Boolean.TRUE)
                    .createdDate(new Date()).build();
            Packages pkg = packageRepository.save(pack);
            packages.add(pkg);
        }
        log.info("End of createPackages in Connector " + "correlationId:" + correlationId);
        return packages;
    }

    public Packages getPackages(PackageBenefitsDetail packageBenefitsDetail) {
        Packages packages = packageRepository.getRecordById(packageBenefitsDetail.getPackageId());
        return packages;
    }

}
