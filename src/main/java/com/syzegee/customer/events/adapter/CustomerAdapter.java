/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.adapter;

import com.syzegee.customer.events.model.*;
import com.syzegee.customer.events.entity.*;
import com.syzegee.customer.events.exception.CustomerRuntimeException;
import com.syzegee.customer.events.repository.*;
import com.syzegee.customer.events.util.CorrelationIdUtil;
import com.syzegee.customer.events.util.JsonUtil;
import com.syzegee.customer.events.validator.CustomerValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Sagar
 */
@Slf4j
@Component
public class CustomerAdapter {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private CustomerAddressRepository addressRepository;
    @Autowired
    private CustomerUserRepository userRepository;
    @Autowired
    private TierRepository tierRepository;
    @Autowired
    private BenefitRepository benefitRepository;
    @Autowired
    private PackageBenefitsRepository packageBenefitsRepository;
    @Autowired
    private CustomerStatusRepository customerStatusRepository;
    @Autowired
    private DomainRepository domainRepository;
    @Autowired
    private JsonUtil util;
    @Autowired
    private CustomerValidator customerValidator;

    @Autowired
    private CustomerStatusAdapter customerStatusAdapter;
    @Autowired
    private DomainDetailsAdapter domainDetailsAdapter;
    @Autowired
    private PackagingAdapter packagingAdapter;

    @Autowired
    private CustomerRoleRepository customerRoleRepository;

    /**
     * This method is used to create orgation for customer journey
     *
     * @param organizationDetail takes orgDetails as param
     * @return orgation entity
     */
    @Transactional
    public Organization createOrganization(OrganizationDetail organizationDetail, String correlationId) {
        Organization orgEntity = null;
        log.info("Initiate createOrganization in Connector ");
        orgEntity = organizationRepository.getRecordByName(organizationDetail.getOrganizationName());
        if (orgEntity == null) {
            Organization organization = Organization.builder().organizationName(organizationDetail.getOrganizationName())
                    .organizationSize(organizationDetail.getOrganizationSize())
                    .organizationDesc(organizationDetail.getOrganizationDesc()).isActive(Boolean.TRUE)
                    .correlationId(correlationId)
                    .createdBy(organizationDetail.getCreatedBy()).createdDate(new Date()).build();
            orgEntity = organizationRepository.save(organization);
        } else {
            Organization organization = Organization.builder().organizationId(orgEntity.getOrganizationId())
                    .organizationName(orgEntity.getOrganizationName())
                    .organizationSize(organizationDetail.getOrganizationSize())
                    .organizationDesc(organizationDetail.getOrganizationDesc()).isActive(Boolean.TRUE)
                    .correlationId(correlationId)
                    .createdBy(organizationDetail.getUpdatedBy()).updatedDate(new Date()).build();
            orgEntity = organizationRepository.save(organization);
        }
        log.info("End of createOrganization in Connector " + orgEntity.getOrganizationName());
        return orgEntity;
    }

    /**
     * This method is used to create customer with organization
     *
     * @param customerDetails takes the bean of cust detail
     * @return customer entity
     */
    @Transactional
    public Customer createCustomer(CustomerDetail customerDetails, Organization organization, String correlationId) {
        Customer customerResult = null;
        log.info("Initiate createCustomer in Connector " + "correlationId:" + correlationId);
        Customer customer = Customer.builder().organizationId(organization).businessName(customerDetails.getBusinessName())
                .businessType(customerDetails.getBusinessType()).businessCategory(customerDetails.getBusinessCategory())
                .businessEmail(customerDetails.getBusinessEmail())
                .userId(customerDetails.getUserId())
                .currency(customerDetails.getCurrency()).isActive(Boolean.TRUE)
                .correlationId(correlationId)
                .createdBy(customerDetails.getCreatedBy()).createdDate(new Date()).build();
        customerResult = customerRepository.save(customer);
        log.info("End of createCustomer in Connector " + "correlationId:" + correlationId);
        return customerResult;
    }

    /**
     * This method is used to create the customerAddress for customer
     *
     * @param customerAddress
     * @param customers       id is required to create the cust  Address
     * @return customer address entity
     */
    @Transactional
    public CustomerAddress createCustomerAddress(CustomerAddressDetail customerAddress, Customer customers, String correlationId) {
        log.info("Initiate createCustomerAddress in Connector " + "correlationId:" + correlationId);
        CustomerAddress address = CustomerAddress.builder().addressLine1(customerAddress.getAddressLine1()).addressLine2(customerAddress.getAddressLine2())
                .addressLine3(customerAddress.getAddressLine3()).city(customerAddress.getCity()).countryCode(customerAddress.getCountryCode())
                .correlationId(correlationId)
                .customerId(customers).isActive(Boolean.TRUE).createdBy(customerAddress.getCreatedBy()).createdDate(new Date()).build();
        CustomerAddress custAddress = addressRepository.save(address);
        log.info("End of createCustomerAddress in Connector " + "correlationId:" + correlationId);
        return custAddress;
    }

    /**
     * This method is used to create Tier for the customer
     *
     * @param tierDetail
     * @param customer   id is required to create the tier
     * @return tier object
     */
    @Transactional
    public Tier createTier(TierDetail tierDetail, Customer customer, String correlationId) {
        log.info("Initiate createTier in Connector " + "correlationId:" + correlationId);
        Tier tier = Tier.builder().customerId(customer).tierName(tierDetail.getTierName()).isActive(Boolean.TRUE)
                .correlationId(correlationId)
                .updatedBy(tierDetail.getUpdatedBy())
                .updatedDate(new Date())
                .createdBy(tierDetail.getCreatedBy()).createdDate(new Date()).build();
        Tier tiers = tierRepository.save(tier);

        log.info("End of createTier in Connector " + "correlationId:" + correlationId);
        return tiers;
    }


    /**
     * This method is used to create the list of benefits of customer for package
     *
     * @param benefitsDetail
     * @param customer       benefits are created
     * @return list of benefits
     */
    @Transactional
    public List<CustomerBenefit> createBenefit(List<BenefitDetail> benefitsDetail, Customer customer, String correlationId) {
        List<CustomerBenefit> customerBenefits = new ArrayList<>();
        log.info("Initiate createBenefit in Connector " + "correlationId:" + correlationId);
        for (BenefitDetail detail : benefitsDetail) {
            for(Long vendorId : detail.getVendorId()) {
                CustomerBenefit customerBenefit = CustomerBenefit.builder()
                        .benefitName(detail.getBenefitName())
                        .customerId(customer).correlationId(correlationId)
                        .isActive(Boolean.TRUE)
                        .createdDate(new Date())
                        .redirectUrl(detail.getRedirectUrl())
                        .imageUrl(detail.getImageUrl())
                        .description(detail.getDescription())
                        .vendorId(vendorId).build();
                CustomerBenefit custBenefit = benefitRepository.save(customerBenefit);
                customerBenefits.add(custBenefit);
            }
        }
        log.info("End of createBenefit in Connector " + "correlationId:" + correlationId);
        return customerBenefits;
    }

    /**
     * This method is used to create the list of custom benefits for the package
     *
     *
     * @param customerBenefit  is required to create the pacakge benefits
     * @param packages         id is required to create pckg benefit
     * @return package benefits for the customer
     */
    @Transactional
    //TODO Put null checks here
    public List<PackagesBenefit> createPackageBenefit(List<PackagesDetail> packagesDetails,
                                                      List<CustomerBenefit> customerBenefit, List<Packages> packages, String correlationId) {
        List<PackagesBenefit> packagesBenefits = new ArrayList<>();
        log.info("Initiate createPackageBenefit in Connector " + "correlationId:" + correlationId);
        for(PackagesDetail packagesDetail : packagesDetails){
            for(CustomerBenefit packbenefit:packagesDetail.getBenefits()){
                for(CustomerBenefit custBenefit : customerBenefit){
                    if(packbenefit.getBenefitName().equals(custBenefit.getBenefitName())){
                        for(Packages packages1:packages) {
                            if(packagesDetail.getPackageName().equals(packages1.getPackageName())) {
                                PackagesBenefit pckgBenfit = PackagesBenefit.builder().packageId(packages1)
                                        .customerBenefitId(custBenefit).isActive(Boolean.TRUE)
                                        .correlationId(correlationId)
                                        .createdBy(packbenefit.getCreatedBy()).createdDate(new Date()).build();
                                PackagesBenefit packagesBenefit = packageBenefitsRepository.save(pckgBenfit);
                                packagesBenefits.add(packagesBenefit);
                            }
                        }
                    }
                }

            }
        }

        log.info("End of createPackageBenefit in Connector " + "correlationId:" + correlationId);
        return packagesBenefits;
    }


    /**
     * This method is used to create the list of benefits of customer for package
     *
     * @param customerUserDetail
     * @return list of benefits
     */
    @Transactional
    public CustomerUser createCustomerUser(CustomerUserDetail customerUserDetail, Customer customer,
                                           String correlationId) {
        log.info("Initiate createCustomerUser in Connector " + "correlationId:" + correlationId);
        CustomerUser customerUser = CustomerUser.builder()
                .correlationId(correlationId)
                .customerId(customer)
                .isActive(Boolean.TRUE)
                .firstName(customerUserDetail.getFirstName())
                .lastName(customerUserDetail.getLastName())
                .emailId(customerUserDetail.getEmailId())
                .createdDate(new Date())
                .createdBy(customerUserDetail.getCreatedBy()).build();
        customerUser = userRepository.save(customerUser);
        log.info("End of createBenefit in Connector " + "correlationId:" + correlationId);
        return customerUser;
    }


    /**
     * This method is used to create Tier for the customer
     *
     * @param createDetail  ,
     * @param correlationId is required to create the tier
     * @return tier object
     */
    public Customer createAllCustomerDetails(CustomerCreateDetail createDetail, String correlationId, Long customerId) {
        Organization organization = null;
        log.info("Initiate createAllCustomerDetails in connector " + " - CorrelationId: " + correlationId);
        Organization org = this.appendOrganization(createDetail.getOrgDetail(), customerId, correlationId);
        Customer customer = this.appendCustomer(createDetail.getCustomerDetail(), org, customerId, correlationId);
        Tier tier = this.createTier(createDetail.getTierDetail(), customer, correlationId);
        CustomerAddress customerAddress = this.createCustomerAddress(createDetail.getCustAddressDetail(), customer, correlationId);
        List<CustomerBenefit> benefit = this.createBenefit(createDetail.getBenefitDetail(), customer, correlationId);
        List<Packages> packages = packagingAdapter.createPackages(createDetail.getPackagesDetail(), customer, tier, correlationId);
        List<PackagesBenefit> packageBenefit = this.createPackageBenefit(createDetail.getPackagesDetail(),benefit, packages, correlationId);
        DomainDetails domainDetails = domainDetailsAdapter.createDomainDetails(createDetail.getDomainDetails(), customer, correlationId);
        List<SolicitationPackage> solicitationPackages = packagingAdapter.createSolicitationPackages(createDetail.getSolicitationDetail(),packages,customer,correlationId);
        log.info("End of createAllCustomerDetails  in connector " + " - CorrelationId: " + correlationId);
        return customer;
    }



    /**
     * This method is used to create customer with organization
     *
     * @param customerCreateDetail takes the bean of cust detail
     * @return customer entity
     */
    @Transactional
    public Customer createCustomerEvent(CustomerCreateDetail customerCreateDetail, String correlationId, Long customerId) {
        Customer customer = null;
        Organization organization = null;
        log.info("Initiate createCustomerEvent in Connector " + " - CorrelationId: " + correlationId);
        Customer orgId = customerRepository.getActiveCustomerById(customerId);
        organization = this.createOrganization(customerCreateDetail.getOrgDetail(), correlationId);
        customer = this.createCustomer(customerCreateDetail.getCustomerDetail(), organization, correlationId);
        log.info("End of createCustomerEvent in Connector " + " - CorrelationId: " + correlationId);
        return customer;
    }

    /**
     * @param customerActivationDetails
     * @return Customer
     */
    @Transactional
    public Customer createActivationDetails(CustomerActivationDetails customerActivationDetails, String correlationId) {
        Customer customer = null;
        Organization organization = null;
        CustomerUser user = null;
        log.info("Initiate createActivationDetails in Connector " + " - CorrelationId: " + correlationId);
        organization = this.createOrganization(customerActivationDetails.getOrgDetail(), correlationId);
        customer = this.createCustomerforActivation(customerActivationDetails.getCustomerDetail(), organization, correlationId);
        user = this.createCustomerUser(customerActivationDetails.getUserDetail(), customer, correlationId);
        this.updateUserIdForCustomer(user,customer.getCustomerId());
        customer.setUserId(user.getCustomerUserId());
        log.info("End of createActivationDetails in Connector " + " - CorrelationId: " + correlationId);
        return customer;
    }

    /**
     * This method is used to create customer with organization
     *
     *
     * @return customer entity
     */
    @Transactional
    public void updateUserIdForCustomer(CustomerUser user,Long customerId) {
        Customer customerResult = null;
        log.info("Initiate createCustomerforActivation in Connector ");
        customerRepository.updateUserIdForCustomer(user.getCustomerUserId(),customerId);
        log.info("End of updateUserIdForCustomer in Connector " );
    }

    /**
     * This method is used to create customer with organization
     *
     * @param customerDetails takes the bean of cust detail
     * @return customer entity
     */
    @Transactional
    public Customer createCustomerforActivation(CustomerDetail customerDetails, Organization organization, String correlationId) {
        Customer customerResult = null;
        // String activation_code = CorrelationIdUtil.generateCorrelationId();
        String activation_code = CorrelationIdUtil.generateActivationCode(correlationId);
        log.info("Initiate createCustomerforActivation in Connector " + "correlationId:" + correlationId);
        Customer customer = Customer.builder().organizationId(organization)
                .businessName(organization.getOrganizationName())
                .isActive(Boolean.TRUE)
                .activationStatus(Boolean.FALSE)
                .activationCode(activation_code)
                .correlationId(correlationId)
                .createdBy(customerDetails.getCreatedBy()).createdDate(new Date()).build();
        customerResult = customerRepository.save(customer);
        log.info("End of createCustomerforActivation in Connector " + "correlationId:" + correlationId);
        return customerResult;
    }

    public ActivationValidateResponse createValidateActivationDetails(ActivationValidateDetails activationValidateDetails,
                                                                      String correlationId) {
        log.info("Initiate createValidateActivationDetails in Connector " + "correlationId:" + correlationId);
        ActivationValidateResponse validateResponse = customerValidator.validateActivationCode(activationValidateDetails);
        ActivationValidateResponse statusValidateResponse = ActivationValidateResponse.builder().status(Boolean.TRUE).build();
        log.info("End of createValidateActivationDetails in Connector " + "correlationId:" + correlationId);
        return validateResponse;
    }

    /**
     * This method is used to create orgation for customer journey
     *
     * @param organizationDetail takes orgDetails as param
     * @return Organization
     */
    @Transactional
    public Organization appendOrganization(OrganizationDetail organizationDetail, Long customerId, String correlationId) {
        log.info("Initiate appendOrganization in Connector " + "correlationId:" + correlationId);
        Customer customer = customerRepository.getActiveCustomerById(customerId);
        Organization orgEntity = organizationRepository.getRecordById(customer.getOrganizationId().getOrganizationId());
        if (organizationDetail.getOrganizationName() != null) {
            orgEntity.setOrganizationName(organizationDetail.getOrganizationName());
        }
        if (organizationDetail.getOrganizationDesc() != null) {
            orgEntity.setOrganizationDesc(organizationDetail.getOrganizationDesc());
        }
        if (organizationDetail.getOrganizationSize() != null) {
            orgEntity.setOrganizationSize(organizationDetail.getOrganizationSize());
        }
        if (organizationDetail.getIsActive() != null) {
            orgEntity.setIsActive(Boolean.TRUE);
        }
        if (organizationDetail.getUpdatedBy() != null) {
            orgEntity.setUpdatedBy(organizationDetail.getUpdatedBy());
        }

        orgEntity.setUpdatedDate(new Date());
        Organization organization = organizationRepository.save(orgEntity);
        log.info("End of appendOrganization in Connector " + "correlationId:" + correlationId);
        return organization;
    }

    /**
     * @param customerDetail
     * @param organization
     * @return Customer
     */
    public Customer appendCustomer(CustomerDetail customerDetail, Organization organization, Long customerId, String correlationId) throws CustomerRuntimeException {
        log.info("Initiate appendCustomer in connector " + "correlationId:" + correlationId);
        Customer customer = customerRepository.getActiveCustomerById(customerId);
        if (customerDetail.getOrganizationId() != null) {
            customer.setOrganizationId(organization);
        }
        if (customerDetail.getUserId() != null) {
            customer.setUserId(customerDetail.getUserId());
        }
        if (customerDetail.getBusinessName() != null) {
            customer.setBusinessName(customerDetail.getBusinessName());
        }
        if (customerDetail.getBusinessEmail() != null) {
            customer.setBusinessEmail(customerDetail.getBusinessEmail());
        }
        if (customerDetail.getBusinessType() != null) {
            customer.setBusinessType(customerDetail.getBusinessType());
        }
        customer.setBusinessCategory(customerDetail.getBusinessCategory());

        if (customerDetail.getPhoneNo() != null) {
            customer.setPhone(customerDetail.getPhoneNo());
        }
        if (customerDetail.getCurrency() != null) {
            customer.setCurrency(customerDetail.getCurrency());
        }
        if (customerDetail.getRegion() != null) {
            customer.setRegion(customerDetail.getRegion());
        }
        customer.setCorrelationId(correlationId);
        customer.setIsActive(Boolean.TRUE);
        customer.setUpdatedBy(customerDetail.getUpdatedBy());
        customer.setUpdatedDate(new Date());
        customer.setCommunicationPreferences(customerDetail.getCommunicationPreferences());
        Customer custObj = customerRepository.save(customer);
        log.info("End of appendCustomer in connector " + "correlationId:" + correlationId);
        return custObj;

    }


    /**
     * @param id
     * @return
     */
    public Customer getCustomer(Long id, String corelationId) throws CustomerRuntimeException {
        log.info("Initiate getCustomer in connector " + "customer id:" + id);
        CustomerValidator validator = new CustomerValidator();
        validator.validateField("Customer", id.toString());
        log.info("validation success  " + "customer id:" + id);
        Customer customer = null;
        customer = customerRepository.getRecordById(id);
        if(Objects.isNull(customer))
        {
            throw new CustomerRuntimeException();
        }
        log.info("customer object " + customer);
        validator.validateEntity("customer", customer);
        log.info("customer validateEntity is success " );
        return customer;

    }


    public Customer updateAllCustomerDetails(CustomerUpdateDetail customerUpdateDetail, Long id, String correlationId) {
        log.info("Initiate updateAllCustomerDetails in connector " + "correlationId:" + correlationId);
        Customer customer = this.getCustomer(id, correlationId);
        if (customerUpdateDetail.getCustomerDetail() != null) {
            Customer updateCustomer = this.updateCustomer(customerUpdateDetail.getCustomerDetail(), customer, correlationId);
        }
        if (customerUpdateDetail.getTierDetail() != null) {
            Tier tier = this.updateTier(customerUpdateDetail.getTierDetail(), customer, correlationId);
        }
        if (customerUpdateDetail.getCustAddressDetail() != null) {
            CustomerAddress customerAddress = this.updateCustomerAddress(customerUpdateDetail.getCustAddressDetail(), customer, correlationId);
        }
        if (customerUpdateDetail.getPackagesDetail() != null) {
            Packages packages = packagingAdapter.updatePackages(customerUpdateDetail.getPackagesDetail(), customer, correlationId);
        }
        if (customerUpdateDetail.getBenefitDetail() != null) {
            CustomerBenefit benefit = this.updateCustomerBenifit(customerUpdateDetail.getBenefitDetail(), customer, correlationId);
        }
        if (customerUpdateDetail.getPackageBenefitsDetail() != null) {
            PackagesBenefit packageBenefit = this.updatePackageBenifit(customerUpdateDetail.getPackageBenefitsDetail()
                    , customer, correlationId);
        }
        log.info("End of  updateAllCustomerDetails in connector " + "correlationId:" + correlationId);
        return customer;
    }

    /**
     * @param customerDetail
     * @param customer
     * @return
     */
    public Customer updateCustomer(CustomerDetail customerDetail, Customer customer, String correlationId) throws CustomerRuntimeException {
        log.info("Inside updateCustomer method in connector " + "correlationId:" + correlationId);
        CustomerValidator validator = new CustomerValidator();
        if (customerDetail.getOrganizationId() != null) {
            Organization organization = organizationRepository.getRecordById(customerDetail.getOrganizationId());
            validator.validateEntity("customer", customer);
            customer.setOrganizationId(organization);
        }
        customer.setCorrelationId(correlationId);

        if (customerDetail.getUserId() != null) {
            customer.setUserId(customerDetail.getUserId());
        }
        if (customerDetail.getBusinessName() != null) {
            customer.setBusinessName(customerDetail.getBusinessName());
        }
        if (customerDetail.getBusinessEmail() != null) {
            customer.setBusinessName(customerDetail.getBusinessEmail());
        }
        if (customerDetail.getPhoneNo() != null) {
            customer.setPhone(customerDetail.getPhoneNo());
        }
        if (customerDetail.getCurrency() != null) {
            customer.setCurrency(customerDetail.getCurrency());
        }
        if (customerDetail.getBusinessType() != null) {
            customer.setBusinessType(customerDetail.getBusinessType());
        }
        if (customerDetail.getRegion() != null) {
            customer.setRegion(customerDetail.getRegion());
        }
        customer.setIsActive(true);
        customer.setUpdatedBy(customerDetail.getUpdatedBy());
        customer.setUpdatedDate(new Date());
        Customer cust = customerRepository.save(customer);
        log.info("end update customer method in connector " + "correlationId:" + correlationId);
        return cust;

    }

    /**
     * @param customerAddressDetail
     * @param customer
     * @return
     */
    public CustomerAddress updateCustomerAddress(CustomerAddressDetail customerAddressDetail, Customer customer, String correlationId) {
        log.info("Inside updateCustomerAddress method in connector " + "correlationId:" + correlationId);
        CustomerAddress customerAddress = null;
        CustomerValidator validator = new CustomerValidator();
        Long customerId = customer.getCustomerId();
        validator.validateField("customerId", customerId.toString());
        customerAddress = addressRepository.getRecordByCustomerId(
                Customer.builder().customerId(customerId).build());
        if(!Objects.isNull(customerAddress))
        {
            validator.validateEntity("customerAddress", customerAddress);
        }
        else
        {
            customerAddress = CustomerAddress.builder().build();
            customerAddress.setCustomerId(Customer.builder().customerId(customerId).build());
        }

        customerAddress.setCorrelationId(correlationId);


            if (customerAddressDetail.getAddressLine1() != null) {
                customerAddress.setAddressLine1(customerAddressDetail.getAddressLine1());
            }
            if (customerAddressDetail.getAddressLine2() != null) {
                customerAddress.setAddressLine2(customerAddressDetail.getAddressLine2());
            }
            if (customerAddressDetail.getAddressLine3() != null) {
                customerAddress.setAddressLine3(customerAddressDetail.getAddressLine3());
            }
            if (customerAddressDetail.getCity() != null) {
                customerAddress.setCity(customerAddressDetail.getCity());
            }
            if (customerAddressDetail.getSate() != null) {
                customerAddress.setSate(customerAddressDetail.getSate());
            }
            if (customerAddressDetail.getZipCode() != null) {
                customerAddress.setZipCode(customerAddressDetail.getZipCode());
            }
            if (customerAddressDetail.getCountryCode() != null) {
                customerAddress.setCountryCode(customerAddressDetail.getCountryCode());
            }
            customerAddress.setIsActive(true);
            customerAddress.setCreatedBy(customerAddressDetail.getCreatedBy());
            customerAddress.setUpdatedBy(customerAddressDetail.getUpdatedBy());
            customerAddress.setUpdatedDate(new Date());
            CustomerAddress address = addressRepository.save(customerAddress);
            log.info("end of updateCustomerAddress method in connector " + "correlationId:" + correlationId);
            return address;
       /* else {
            log.info("error in updateCustomerAddress method in connector " + "correlationId:" + correlationId);
            throw new CustomerRuntimeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    "customerId with given customerId is not present in record");
        }*/

    }


    /**
     * @param tierDetail
     * @param customer
     * @return
     */
    public Tier updateTier(TierDetail tierDetail, Customer customer, String correlationId) {
        log.info("inside updateTier method in connector " + "correlationId:" + correlationId);
        Tier tier = null;
        CustomerValidator validator = new CustomerValidator();

        Long tierId = tierDetail.getId();
        validator.validateField("tierId", tierId.toString());

        tier = tierRepository.getRecordById(tierId);
        validator.validateEntity("tier", tier);
        tier.setCorrelationId(correlationId);
        if (tier.getCustomerId().getCustomerId().equals(customer.getCustomerId())) {
            if (tierDetail.getTierName() != null) {
                tier.setTierName(tierDetail.getTierName());
            }
            tier.setIsActive(true);
            tier.setUpdatedBy(tierDetail.getUpdatedBy());
            tier.setUpdatedDate(new Date());
            Tier tierResult = tierRepository.save(tier);
            log.info("end of updateTier method in connector " + "correlationId:" + correlationId);
            return tierResult;
        } else {
            log.info("error in updateTier method in connector " + "correlationId:" + correlationId);
            throw new CustomerRuntimeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    "customerId with given TierId is not present in record");
        }
    }



    /**
     * @param packageBenefitsDetail
     * @param customer
     * @return
     */

    public PackagesBenefit updatePackageBenifit(PackageBenefitsDetail packageBenefitsDetail, Customer customer, String correlationId) {
        log.info("inside updatePackageBenifit method in connector " + "correlationId:" + correlationId);
        PackagesBenefit packagesBenefit = null;
        CustomerValidator validator = new CustomerValidator();
        Long packagesBenefitId = packageBenefitsDetail.getId();
        validator.validateField("packagesBenefitId", packagesBenefitId.toString());
        packagesBenefit = packageBenefitsRepository.getRecordById(packagesBenefitId);
        validator.validateEntity("packagesBenefit", packagesBenefit);
        packagesBenefit.setCorrelationId(correlationId);
        if (packagesBenefit.getPackageId().getTierId().getCustomerId().getCustomerId().equals(customer.getCustomerId())) {

            if (packageBenefitsDetail.getPackageId() != 0) {
                Packages packages = packagingAdapter.getPackages(packageBenefitsDetail);
                validator.validateEntity("Packages", packages);
                packagesBenefit.setPackageId(packages);
            }
            if (packageBenefitsDetail.getBenefitId() != 0) {
                CustomerBenefit customerBenefit = benefitRepository.getRecordById(packageBenefitsDetail.getBenefitId());
                validator.validateEntity("CustomerBenefit", customerBenefit);
                packagesBenefit.setCustomerBenefitId(customerBenefit);
            }
            packagesBenefit.setIsActive(true);
            packagesBenefit.setUpdatedBy(packageBenefitsDetail.getUpdatedBy());
            packagesBenefit.setUpdatedDate(new Date());
            PackagesBenefit packagesBenefitResult = packageBenefitsRepository.save(packagesBenefit);
            log.info("end of updatePackageBenifit method in connector " + "correlationId:" + correlationId);
            return packagesBenefitResult;
        } else {
            log.info("error in updatePackageBenifit method in connector " + "correlationId:" + correlationId);
            throw new CustomerRuntimeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    "customerId with given packagesBenefitId is not present in record");
        }
    }



    /**
     * @param benefitDetail
     * @param customer
     * @return
     */
    @Transactional
    public CustomerBenefit updateCustomerBenifit(BenefitDetail benefitDetail, Customer customer, String correlationId) {
        log.info("inside updateCustomerBenifit method in connector " + "correlationId:" + correlationId);
        CustomerValidator validator = new CustomerValidator();
        Long customerBenefitId = benefitDetail.getId();
        validator.validateField("customerBenefitId", customerBenefitId.toString());

        CustomerBenefit customerBenefit = benefitRepository.getRecordById(customerBenefitId);
        validator.validateEntity("customerBenefit", customerBenefit);
        customerBenefit.setCorrelationId(correlationId);

        if (customerBenefit.getCustomerId().getCustomerId().equals(customer.getCustomerId())) {
            CustomerBenefit customerBenefitResult = benefitRepository.save(customerBenefit);
            log.info("end of updateCustomerBenifit method in connector " + "correlationId:" + correlationId);
            return customerBenefitResult;
        } else {
            log.info("error in updateCustomerBenifit method in connector " + "correlationId:" + correlationId);
            throw new CustomerRuntimeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    "customerId with given customerBenefitId is not present in record");
        }

    }


    public void updateCustomerDomainNMAStatus(CustomerDomainStatusUpdateEvent event) throws CustomerRuntimeException {
        CustomerStatus customerStatus = CustomerStatus.builder().customerId(event.getCustomerId())
                .correlationId(event.getCorrelationId())
                .build();
        Optional<Customer> customer = getCustomer(customerStatus);
        //TODO convert into optional
        if (customer.isPresent()) {
            domainDetailsAdapter.updateDomainDetails(event, customer);
            customerStatusAdapter.updateMarketingAutomationStatus(customerStatus);

        }
    }

    @Transactional
    public void updateCustomerOrgTierNAddressDetails(CustomerUpdateDetail customerUpdateDetail, Organization organization,List<Tier> tierList)
            throws CustomerRuntimeException {
       CustomerAddress customerAddress = updateCustomerAddress(customerUpdateDetail.getCustAddressDetail(),
                Customer.builder().customerId(customerUpdateDetail.getCustomerId()).build(),"");
       List<CustomerAddress> customerAddressCollection = new ArrayList<>();
       customerAddressCollection.add(customerAddress);

       Customer customer = customerRepository.getRecordById(customerUpdateDetail.getCustomerId());
       customer.setCustomerAddressCollection(customerAddressCollection);
       customer.setTierCollection(tierList);
       customer.setOrganizationId(organization);
        if(customerUpdateDetail.getCustomerDetail().getBusinessEmail()!=null)
        {
            customer.setBusinessEmail(customerUpdateDetail.getCustomerDetail().getBusinessEmail());
        }
        if(customerUpdateDetail.getCustomerDetail().getBusinessName()!=null)
        {
            customer.setBusinessName(customerUpdateDetail.getCustomerDetail().getBusinessName());
        }
        if(customerUpdateDetail.getCustomerDetail().getBusinessType()!=null)
        {
            customer.setBusinessType(customerUpdateDetail.getCustomerDetail().getBusinessType());
        }
        if(customerUpdateDetail.getCustomerDetail().getBusinessCategory()!=null)
        {
            customer.setBusinessCategory(customerUpdateDetail.getCustomerDetail().getBusinessCategory());
        }
        if(customerUpdateDetail.getCustomerDetail().getRegion()!=null)
        {
            customer.setRegion(customerUpdateDetail.getCustomerDetail().getRegion());
        }
        if(customerUpdateDetail.getCustomerDetail().getPhoneNo()!=null)
        {
            customer.setPhone(customerUpdateDetail.getCustomerDetail().getPhoneNo());
        }
        if(customerUpdateDetail.getCustomerDetail().getCurrency()!=null)
        {
            customer.setCurrency(customerUpdateDetail.getCustomerDetail().getCurrency());
        }
        if(customerUpdateDetail.getCustomerDetail().getCommunicationPreferences()!=null)
        {
            customer.setCommunicationPreferences(customerUpdateDetail.getCustomerDetail().getCommunicationPreferences());
        }
        if(customerUpdateDetail.getCustomerDetail().getUpdatedBy()!=null)
        {
            customer.setUpdatedBy(customerUpdateDetail.getCustomerDetail().getUpdatedBy());
        }
        customer.setUpdatedDate(new Date());
       customerRepository.save(customer);
    }

    public Optional<Customer> getCustomer(CustomerStatus customerStatus) {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.getRecordById(customerStatus.getCustomerId()));
        return customer;
    }

    @Transactional
    public CustomerUserResponse addCustomerUserAndRole(CustomerUserRequest customerUserRequest, String correlationId, Long customerId) {
        CustomerUser customerUser = null;
        Customer customer = null;
        CustomerRole userRole= null;
        log.info("Initiate addCustomerUserRole in Connector " + " - CorrelationId: " + correlationId);
        Customer cusId = customerRepository.getRecordById(customerId);
        //TODO Create customer User
        //TODO create customer Role
        customerUser = createCustomerUser(customerUserRequest, cusId, correlationId);
        List<CustomerRole> customerRoleList =createUserRoles(customerUser,customerUserRequest,userRole,correlationId);
        List<CustomerUserRoleResponse> customerUserRoleResponseList = new ArrayList<>();
        for(CustomerRole role : customerRoleList)
        {
            CustomerUserRoleResponse customerUserRoleResponse = CustomerUserRoleResponse.builder().isSuccess("true")
                    .uri("/role/"+role.getCustomerRoleId()).build();
            customerUserRoleResponseList.add(customerUserRoleResponse);
        }
        CustomerUserResponse customerUserResponse = CustomerUserResponse.builder()
                .createdBy(customerUser.getCreatedBy())
                .correlationId(customerUser.getCorrelationId())
                .createdDate(customerUser.getCreatedDate())
                .isActive(customerUser.getIsActive())
                .emailId(customerUser.getEmailId())
                .roles(customerUserRoleResponseList)
                .build();
        log.info("End of addCustomerUserRole in Connector " + " - CorrelationId: " + correlationId);
        return  customerUserResponse;
    }
    @Transactional
    public CustomerUser createCustomerUser(CustomerUserRequest customerUserRequest, Customer customer,
                                           String correlationId) {
        log.info("Initiate createCustomerUser in Connector " + "correlationId:" + correlationId);
        CustomerUser customerUser =null;
        customerUser = CustomerUser.builder()
                .correlationId(correlationId)
                .customerId(customer)
                .isActive(customerUserRequest.getIsActive())
                .firstName(customerUserRequest.getFirstName())
                .lastName(customerUserRequest.getLastName())
                .emailId(customerUserRequest.getEmailId())
                .createdDate(new Date())
                .createdBy(customerUserRequest.getCreatedBy()).build();
        customerUser = userRepository.save(customerUser);
            return customerUser;
    }

    @Transactional
    private List<CustomerRole> createUserRoles(CustomerUser customerUser,CustomerUserRequest customerUserRequest, CustomerRole userroles, String correlationId) {
        List<CustomerRole> customerRoleList = new ArrayList<>();
        log.info("Initiate UserRole in connector " + "correlationId:" + correlationId);
        String customerRequestId = CorrelationIdUtil.generateCorrelationId();
        String details = util.objectToString(customerUserRequest);
        //TODO user camel case as Java standards
        if(null!= customerUserRequest.getRoles() && !customerUserRequest.getRoles().isEmpty() && customerUserRequest.getRoles().size()>0)
        {
            for(CustomerUserRoleRequest userRoleRequest:customerUserRequest.getRoles())
            {
                CustomerRole userRole = CustomerRole.builder()
                        //  .customerRoleId(userroles.getCustomerRoleId())
                        .customerId(customerUser.getCustomerId().getCustomerId())
                        .customerUserId(customerUser.getCustomerUserId())
                        .correlationId(correlationId)
                        .isActive(customerUserRequest.getIsActive())
                        .createdBy(customerUserRequest.getCreatedBy())
                        .createdDate(new Date())
                        .updatedBy(customerUserRequest.getUpdatedBy())
                        .updatedDate(customerUserRequest.getUpdatedDate())
                        .roles(userRoleRequest.getRoleType())
                        .build();
                userRole = customerRoleRepository.save(userRole);
                customerRoleList.add(userRole);
            }
        }

        log.info("End of  UserRole in connector " + "correlationId:" + correlationId);
        return customerRoleList;
    }
}
