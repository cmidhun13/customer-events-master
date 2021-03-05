/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.adapter;

import com.syzegee.customer.events.model.*;
import com.syzegee.customer.events.domain.crafter.CustomerTemplateEvent;
import com.syzegee.customer.events.domain.ruleengine.CustomerRuleEvent;
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

import static com.syzegee.customer.events.constants.Constants.CUSTOMER_CREATED;
import static com.syzegee.customer.events.constants.Constants.CUSTOMER_UPDATED;

/**
 * @author Sagar
 */
@Slf4j
@Component
public class CustomerConnectors {

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
    private PackageRepository packageRepository;
    @Autowired
    private BenefitRepository benefitRepository;
    @Autowired
    private PackageBenefitsRepository packageBenefitsRepository;
    @Autowired
    private SolicitationPackageRepository solicitationPackageRepository;
    @Autowired
    private CustomerInboxRepository customerInboxRepository;
    @Autowired
    private DomainRepository domainRepository;
    @Autowired
    private JsonUtil util;
    @Autowired
    private CustomerValidator customerValidator;
    @Autowired
    private CustomerAdapter customerAdapter;


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
                .createdBy(tierDetail.getCreatedBy()).createdDate(new Date()).build();
        Tier tiers = tierRepository.save(tier);
        log.info("End of createTier in Connector " + "correlationId:" + correlationId);
        return tiers;
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
    private int getUniqueRandomNumberSolicitation(){
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        SolicitationPackage recordBySoliciationId = solicitationPackageRepository.getRecordBySoliciationId(randomNumber);
        if(recordBySoliciationId!=null){
            getUniqueRandomNumberSolicitation();
        }
        return randomNumber;
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
    public List<SolicitationPackage> createSolicitationPackages(List<SolicitationDetail> solicitationDetails,List<Packages> packages,Customer customer, String correlationId) {
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
     * @param detail     ,
     * @param customerId id is required to create the tier
     * @return tier object
     */

    public CustomerInbox saveToinbox(CustomerCreateDetail detail, Long customerId, String correlationId) {
        log.info("Initiate saveToinbox in connector " + "correlationId:" + correlationId);
        String customerRequestId = CorrelationIdUtil.generateCorrelationId();
        String details = util.objectToString(detail);
        CustomerInbox customerInbox = CustomerInbox.builder().
                customerId(customerId)
                .customerRequestId(customerRequestId)
                .status(CUSTOMER_CREATED).details(details)
                .correlationId(correlationId).build();
        CustomerInbox inbox = customerInboxRepository.save(customerInbox);
        log.info("End of  saveToinbox in connector " + "correlationId:" + correlationId);
        return inbox;
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
        List<Packages> packages = this.createPackages(createDetail.getPackagesDetail(), customer, tier, correlationId);
        List<PackagesBenefit> packageBenefit = this.createPackageBenefit(createDetail.getPackagesDetail(),benefit, packages, correlationId);
        DomainDetails domainDetails = this.createDomainDetails(createDetail.getDomainDetails(), customer, correlationId);
        List<SolicitationPackage> solicitationPackages = this.createSolicitationPackages(createDetail.getSolicitationDetail(),packages,customer,correlationId);
        log.info("End of createAllCustomerDetails  in connector " + " - CorrelationId: " + correlationId);
        return customer;
    }

    /**
     * This method is used to create Tier for the customer
     *
     * @param customerDomainDetails  ,
     * @param correlationId is required to create the tier
     * @return domainDetails
     */
    private DomainDetails createDomainDetails(CustomerDomainDetails customerDomainDetails, Customer customer, String correlationId) {
        log.info("Initiate createDomainDetails in connector " + " - CorrelationId: " + correlationId);
        DomainDetails details = DomainDetails.builder()
                .siteCode(customerDomainDetails.getSiteCode())
                .templateCode(customerDomainDetails.getTemplateCode())
                .siteName(customerDomainDetails.getSiteName())
                .siteType(customerDomainDetails.getSiteType())
                .customerId(customer)
                .domainName(customerDomainDetails.getDomainName())
                .logo(customerDomainDetails.getLogo())
                .tagline(customerDomainDetails.getTagline())
                .isActive(Boolean.TRUE)
                .createdBy(customerDomainDetails.getCreatedBy())
                .createdDate(new Date())
                .correlationId(correlationId).build();
        DomainDetails domainDetails = domainRepository.save(details);
        log.info("End of createDomainDetails  in connector " + " - CorrelationId: " + correlationId);
        return domainDetails;
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
     * This method is used to create customer with organization
     *
     * @param event
     * @return CustomerInbox
     */
    @Transactional
    public CustomerInbox getCustomerRequest(CustomerEvent event) {
        log.info("Initiate getCustomerRequest in Connector " + " - CorrelationId: " + event.getCorrelationId());
        CustomerInbox customerDeatils = customerInboxRepository.getCustomerRequest(event.getCustomerRequestId());
        log.info("End of getCustomerRequest in Connector " + " - CorrelationId: " + event.getCorrelationId());
        return customerDeatils;
    }

    /**
     * This method is used to create customer with organization
     *
     * @param event
     * @return CustomerInbox
     */
    @Transactional
    public CustomerInbox getCustomerForRuleRequest(CustomerRuleEvent event) {
        log.info("Initiate getCustomerForRuleRequest in Connector " + " - CorrelationId: " + event.getCorrelationId());
        CustomerInbox customerDeatils = customerInboxRepository.getCustomerRequest(event.getCustomerRequestId());
        log.info("End of getCustomerForRuleRequest in Connector " + " - CorrelationId: " + event.getCorrelationId());
        return customerDeatils;
    }

    /**
     * This method is used to create customer with organization
     *
     * @param event
     * @return CustomerInbox
     */
    @Transactional
    public CustomerInbox getCustomerForTemplateRequest(CustomerTemplateEvent event) {
        log.info("Initiate getCustomerForTemplateRequest in Connector " + " - CorrelationId: " + event.getCorrelationId());
        CustomerInbox customerDeatils = customerInboxRepository.getCustomerRequest(event.getCustomerRequestId());
        log.info("End of getCustomerForTemplateRequest in Connector " + " - CorrelationId: " + event.getCorrelationId());
        return customerDeatils;
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
        log.info("End of createActivationDetails in Connector " + " - CorrelationId: " + correlationId);
        return customer;
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
        if (organizationDetail.getUpdatedDate() != null) {
            orgEntity.setUpdatedDate(new Date());
        }
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


    //====================================================update======================================

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
        log.info("customer object " + customer);
        validator.validateEntity("customer", customer);
        log.info("customer validateEntity is success " );
        return customer;

    }

    @Transactional
    public CustomerInbox updateToinbox(CustomerUpdateDetail detail, Customer customer, String correlationId) {
        log.info("Initiate updateToinbox in connector " + "correlationId:" + correlationId);
        String customerRequestId = CorrelationIdUtil.generateCorrelationId();
        CustomerInbox customerInbox = CustomerInbox.builder().
                customerId(customer.getCustomerId())
                .customerRequestId(customerRequestId)
                .status(CUSTOMER_UPDATED).details(detail.toString())
                .correlationId(correlationId).build();
        CustomerInbox inbox = customerInboxRepository.save(customerInbox);
        log.info("End of  updateToinbox in connector " + "correlationId:" + correlationId);
        return inbox;
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
            CustomerAddress customerAddress = customerAdapter.updateCustomerAddress(customerUpdateDetail.getCustAddressDetail(), customer, correlationId);
        }
        if (customerUpdateDetail.getPackagesDetail() != null) {
            Packages packages = this.updatePackages(customerUpdateDetail.getPackagesDetail(), customer, correlationId);
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
   /* public CustomerAddress updateCustomerAddress(CustomerAddressDetail customerAddressDetail, Customer customer, String correlationId) {
        log.info("Inside updateCustomerAddress method in connector " + "correlationId:" + correlationId);
        CustomerAddress customerAddress = null;
        CustomerValidator validator = new CustomerValidator();
        Long customerId = customerAddressDetail.getCustomerId();
        validator.validateField("customerId", customerId.toString());
        customerAddress = addressRepository.getRecordByCustomerId(customerId);
        validator.validateEntity("customerAddress", customerAddress);
        customerAddress.setCorrelationId(correlationId);

        if (customerAddress.getCustomerId().getCustomerId().equals(customer.getCustomerId())) {
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
            customerAddress.setUpdatedBy(customerAddressDetail.getUpdatedBy());
            customerAddress.setUpdatedDate(new Date());
            CustomerAddress address = addressRepository.save(customerAddress);
            log.info("end of updateCustomerAddress method in connector " + "correlationId:" + correlationId);
            return address;
        } else {
            log.info("error in updateCustomerAddress method in connector " + "correlationId:" + correlationId);
            throw new CustomerRuntimeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    "customerId with given customerId is not present in record");
        }

    }
*/

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
                Packages packages = packageRepository.getRecordById(packageBenefitsDetail.getPackageId());
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
    public List<Map<String, Object>> getSolicitationPackageList(Long customerId) {
        log.info("Initiate getSolicitationPackageList in connector " + "customer id:" + customerId);
        List<Map<String, Object>> solicitationPackages = solicitationPackageRepository.getRecordByCustomerIdAndDate(customerId,new Date());
        log.info("solicitationPackages object " + solicitationPackages);
        log.info("End getSolicitationPackageList in connector " + "customer id:" + customerId);
        return solicitationPackages;

    }
}
