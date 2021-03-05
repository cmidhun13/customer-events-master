package com.syzegee.customer.events.service;

import static org.junit.Assert.assertEquals;

//@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

//    @Mock
//    CustomerConnectors connectors;
//    @InjectMocks
//    CustomerService customerService;

//    @Test
//    public void createCustomerEventTest() {
//        CustomerResponse expectedResult = new CustomerResponse();
//        expectedResult.setCustomerId(5L);
//        expectedResult.setCorrelationId("1");
//        expectedResult.setMessage("Customer Record Successfully Created ");
//        CustomerBenefit customerBenefit = null;
//        OrganizationDetail organizationDetail = OrganizationDetail.builder().organizationId(1L).organizationName("Amazon")
//                .organizationDesc("desc").build();
//        Organization organization = Organization.builder().organizationName("Amazon").
//                organizationDesc("desc").isActive(Boolean.TRUE)
//                .createdBy("Admin").createdDate(new Date()).build();
//        when(connectors.createOrganization(organizationDetail,"124568562")).thenReturn(organization);
//        CustomerDetail customerDetail = CustomerDetail.builder().customerId(1L).userId("User1")
//                .businessName("Amazon").businessEmail("info@amazon.com").phoneNo("12335453")
//                .businessType("software").currency("USD").region("USA")
//                .createdBy("admin").createdDate(new Date()).updatedBy("admin").updatedDate(new Date()).build();
//
//
//        Customer customer = Customer.builder().organizationId(organization).customerId(5L)
//                .userId("User1").businessName("Amazon").businessEmail("info@amazon.com").
//                        businessType("software").currency("USD").region("USA").isActive(Boolean.TRUE)
//                .createdBy("admin").createdDate(new Date()).build();
//        String correlationId = "123";
//        when(connectors.createCustomer(customerDetail, organization, correlationId)).thenReturn(customer);
//
//        TierDetail tierDetail = TierDetail.builder().id(1L).tierName("Basic").build();
//        Tier tier = Tier.builder().customerId(customer).tierName(tierDetail.getTierName()).isActive(Boolean.TRUE)
//                .createdBy(tierDetail.getCreatedBy()).createdDate(new Date()).build();
//        when(connectors.createTier(tierDetail, customer, correlationId)).thenReturn(tier);
//
//        List<CustomerAddressDetail> custAddressDetail = new ArrayList<>();
//
//        CustomerAddressDetail addressDetail = CustomerAddressDetail.builder().customerAddressId(1L)
//                .addressLine1("HSR").addressLine2("BTM").addressLine3("marathalli").city("Bangalore")
//                .sate("Karnataka").zipCode("1232").countryCode("1233").build();
//        custAddressDetail.add(addressDetail);
//        List<CustomerAddress> customerAddresses = new ArrayList<>();
//        CustomerAddress customerAddress = CustomerAddress.builder().addressLine1(addressDetail.getAddressLine1()).addressLine2(addressDetail.getAddressLine2())
//                .addressLine3(addressDetail.getAddressLine3()).city(addressDetail.getCity()).countryCode(addressDetail.getCountryCode())
//                .customerId(customer).isActive(Boolean.TRUE).createdBy(addressDetail.getCreatedBy()).createdDate(new Date()).build();
//        customerAddresses.add(customerAddress);
//        when(connectors.createCustomerAddress(custAddressDetail, customer, correlationId)).thenReturn(customerAddresses);
//List<PackagesDetail> detailList=new ArrayList<>();
//        PackagesDetail packagesDetail = PackagesDetail.builder().id(1L).packageName("Dianing").build();
//        detailList.add(packagesDetail);
//        List<Packages> packagesList=new ArrayList<>();
//        Packages packages = Packages.builder().tierId(tier).packageName(packagesDetail.getPackageName()).
//                createdBy(packagesDetail.getCreatedBy())
//                .createdDate(new Date()).build();
//        packagesList.add(packages);
//        when(connectors.createPackages(detailList, customer, tier, correlationId)).thenReturn(packagesList);
//
//        List<BenefitDetail> benefitDetails = new ArrayList<>();
//
//        BenefitDetail benefitDetail = BenefitDetail.builder().id(1L).vendorId(1L).benefitName("custom").build();
//        benefitDetails.add(benefitDetail);
//        List<CustomerBenefit> customerBenefits = new ArrayList<>();
//        for (BenefitDetail detail : benefitDetails) {
//            customerBenefit = CustomerBenefit.builder().customerId(customer).vendorId(detail.getVendorId())
//                    .build();
//
//            customerBenefits.add(customerBenefit);
//        }
//        when(connectors.createBenefit(benefitDetails, customer, correlationId)).thenReturn(customerBenefits);
//
//        List<PackageBenefitsDetail> packageBenefitsDetails = new ArrayList<>();
//        PackageBenefitsDetail packageBenefitsDetail = PackageBenefitsDetail.builder().benefitId(1L).packageId(1L).build();
//        packageBenefitsDetails.add(packageBenefitsDetail);
//        List<PackagesBenefit> packagesBenefits = new ArrayList<>();
//        for (CustomerBenefit benefit : customerBenefits) {
//            for (PackageBenefitsDetail pckBeneDetails : packageBenefitsDetails) {
//                PackagesBenefit pckgBenfit = PackagesBenefit.builder().packageId(packages)
//                        .customerBenefitId(benefit).isActive(Boolean.TRUE)
//                        .createdBy(pckBeneDetails.getCreatedBy()).createdDate(new Date()).build();
//                packagesBenefits.add(pckgBenfit);
//            }
//        }
//        when(connectors.createPackageBenefit(packageBenefitsDetails
//                , customerBenefits, packagesList, correlationId)).thenReturn(packagesBenefits);
//
//        List<CustomerRoleDetail> customerRoleDetails = new ArrayList<>();
//        CustomerRoleDetail customerRoleDetail = CustomerRoleDetail.builder().customerId(customer).customerRoleType("admin").build();
//        customerRoleDetails.add(customerRoleDetail);
//        List<CustomerUserDetail> customerUserDetails = new ArrayList<>();
//        CustomerUserDetail customerUserDetail = CustomerUserDetail.builder()
//                .customerId(1l).firstName("john").lastName("dev").build();
//        customerUserDetails.add(customerUserDetail);
//        LocationDetail locationDetail = LocationDetail.builder().locationId(1l).locationName("India").build();
//        CustomerCreateDetail customerCreateDetail = CustomerCreateDetail.builder().orgDetail(organizationDetail)
//                .customerDetail(customerDetail).custAddressDetail(custAddressDetail).tierDetail(tierDetail)
//                .packagesDetail(detailList).benefitDetail(benefitDetails).packageBenefitsDetail(packageBenefitsDetails)
////                .locationDetail(locationDetail).customerUserDetails(customerUserDetails).customerRoleDetails(customerRoleDetails)
//                .build();
//        CustomerInbox customerInbox = CustomerInbox.builder().
//                customerId(customer.getCustomerId())
//                .customerRequestId("12345")
//                .status(CUSTOMER_CREATED).details(customerCreateDetail.toString())
//                .correlationId(correlationId).build();
//        when(connectors.saveToinbox(customerCreateDetail, customerInbox.getCustomerId(), correlationId)).thenReturn(customerInbox);
//        CustomerResponse customerRegistration = customerService.customerCreateEvent(customerCreateDetail, correlationId,customerInbox.getCustomerId());
//        assertEquals(customerRegistration.getMessage(), expectedResult.getMessage());
//        assertEquals(customerRegistration.getCorrelationId(), expectedResult.getCorrelationId());
//        assertEquals(customerRegistration.getCustomerId(), expectedResult.getCustomerId());
//    }

//    @Test
//    public void updateCustomerEventTest() {
//        String correlationId = "123";
//        CustomerResponse expectedResult = new CustomerResponse();
//        expectedResult.setCustomerId(5l);
//        expectedResult.setCorrelationId(correlationId);
//        expectedResult.setMessage("Customer update request recevied you will be receving an eamil  ");
//        CustomerDetail customerDetail = CustomerDetail.builder().customerId(1l).userId("User1")
//                .businessName("Amazon").businessEmail("info@amazon.com").phoneNo("12335453")
//                .businessType("software").currency("USD").region("USA")
//                .createdBy("admin").createdDate(new Date()).updatedBy("admin").updatedDate(new Date()).build();
//
//
//        Customer customer = Customer.builder().customerId(5l).correlationId(correlationId)
//                .userId("User1").businessName("Amazon").businessEmail("info@amazon.com").
//                        businessType("software").currency("USD").region("USA").isActive(Boolean.TRUE)
//                .createdBy("admin").createdDate(new Date()).build();
//        when(connectors.getCustomer(5L, correlationId)).thenReturn(customer);
//
//        when(connectors.updateCustomer(customerDetail, customer, correlationId)).thenReturn(customer);
//
//        CustomerAddressDetail addressDetail = CustomerAddressDetail.builder().customerAddressId(1L)
//                .addressLine1("HSR").addressLine2("BTM").addressLine3("marathalli").city("Bangalore")
//                .sate("Karnataka").zipCode("1232").countryCode("1233").build();
//        CustomerAddress customerAddress = CustomerAddress.builder().addressLine1(addressDetail.getAddressLine1()).addressLine2(addressDetail.getAddressLine2())
//                .addressLine3(addressDetail.getAddressLine3()).city(addressDetail.getCity()).countryCode(addressDetail.getCountryCode())
//                .customerId(customer).isActive(Boolean.TRUE).createdBy(addressDetail.getCreatedBy()).createdDate(new Date()).build();
//
//        when(connectors.updateCustomerAddress(addressDetail, customer, correlationId)).thenReturn(customerAddress);
//
//        BenefitDetail benefitDetail = BenefitDetail.builder().id(1L).vendorId(1L).benefitName("custom")
//                .build();
//        CustomerBenefit customerBenefit = CustomerBenefit.builder().customerId(customer).
//                vendorId(benefitDetail.getVendorId())
//                .build();
//        when(connectors.updateCustomerBenifit(benefitDetail, customer, correlationId)).thenReturn(customerBenefit);
//
//        TierDetail tierDetail = TierDetail.builder().id(1L).tierName("Basic").build();
//        Tier tier = Tier.builder().customerId(customer).tierName(tierDetail.getTierName()).isActive(Boolean.TRUE)
//                .createdBy(tierDetail.getCreatedBy()).createdDate(new Date()).build();
//        when(connectors.updateTier(tierDetail, customer, correlationId)).thenReturn(tier);
//
//        PackagesDetail packagesDetail = PackagesDetail.builder().id(1L).packageName("Dianing").build();
//        Packages packages = Packages.builder().tierId(tier).packageName(packagesDetail.getPackageName()).
//                createdBy(packagesDetail.getCreatedBy())
//                .createdDate(new Date()).build();
//        when(connectors.updatePackages(packagesDetail, customer, correlationId)).thenReturn(packages);
//
//        PackageBenefitsDetail packageBenefitsDetail = PackageBenefitsDetail.builder().benefitId(1L).packageId(1L).build();
//        PackagesBenefit packagesBenefit = PackagesBenefit.builder().packageId(packages)
//                .customerBenefitId(customerBenefit).isActive(Boolean.TRUE)
//                .createdBy(packageBenefitsDetail.getCreatedBy()).createdDate(new Date()).build();
//        when(connectors.updatePackageBenifit(packageBenefitsDetail, customer, correlationId)).thenReturn(packagesBenefit);
//
//        CustomerUpdateDatail customerUpdateDatail = CustomerUpdateDatail.builder()
//                .customerDetail(customerDetail).custAddressDetail(addressDetail).tierDetail(tierDetail)
//                .packagesDetail(packagesDetail).benefitDetail(benefitDetail).packageBenefitsDetail(packageBenefitsDetail)
//                .build();
//        CustomerInbox customerInbox = CustomerInbox.builder().
//                customerId(customer.getCustomerId())
//                .customerRequestId("12345")
//                .status(CUSTOMER_CREATED).details(customerUpdateDatail.toString())
//                .correlationId(correlationId).build();
//        when(connectors.updateToinbox(customerUpdateDatail, customer, correlationId)).thenReturn(customerInbox);
//        when(connectors.updateAllCustomerDetails(customerUpdateDatail, 5L, correlationId)).thenReturn(customer);
//        CustomerResponse customerRegistration = customerService.updateCustomerEvent(customerUpdateDatail,5L,correlationId);
//        assertEquals(customerRegistration.getMessage(), expectedResult.getMessage());
//        assertEquals(customerRegistration.getCorrelationId(), expectedResult.getCorrelationId());
//        assertEquals(customerRegistration.getCustomerId(), expectedResult.getCustomerId());
//
//    }
}