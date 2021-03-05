package com.syzegee.customer.events.adapter;

import com.syzegee.customer.events.model.*;
import com.syzegee.customer.events.entity.*;
import com.syzegee.customer.events.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CustomerConnectorsTest {
    @Mock
    CustomerRepository customerRepositoryMock;

    @Mock
    OrganizationRepository organizationRepositoryMock;
    @Mock
    CustomerAddressRepository addressRepository;
    @Mock
    TierRepository tierRepository;
    @Mock
    PackageRepository packageRepository;
    @Mock
    BenefitRepository benefitRepository;
    @Mock
    PackageBenefitsRepository packageBenefitsRepository;

    @InjectMocks
    CustomerConnectors customerConnectorsMock;

    @InjectMocks
    CustomerAdapter customerAdapterMock;


    @Test
    public void createOrganization() {
        String correlationId="1213234214-123-15434235";
        Organization organization = getOrganization();
        Mockito.when(organizationRepositoryMock.save(new Organization())).thenReturn(organization);
        OrganizationDetail organizationDetail = OrganizationDetail.builder().organizationId(1l)
                .organizationName("Amazon")
                .organizationDesc("desc").build();
        Organization response = customerConnectorsMock.createOrganization(organizationDetail,correlationId);
        System.out.println(organization.getOrganizationName());
        System.out.println(response.getOrganizationName());
        assertEquals(organization, response);
        assertEquals(organization.getOrganizationName(), response.getOrganizationName());
    }

    @Test
    public void createCustomer() {
        Organization organization = getOrganization();
        Customer customer = getCustomer();
        System.out.println("customer  >>" + customer);
        CustomerDetail customerDetail = CustomerDetail.builder().customerId(99l)
                .phoneNo("12345").organizationId(1l)
                .userId(1l).businessName("Sangam").businessEmail("info@amazon.com").
                        businessType("software").currency("USD").region("USA").
                        isActive(Boolean.TRUE)
                .createdBy("admin").createdDate(new Date()).build();
        Mockito.when(customerRepositoryMock.save(new Customer())).thenReturn(customer);
        String correlationId="123";
        Customer response = customerConnectorsMock.createCustomer(customerDetail, organization, correlationId);
        assertEquals(customer, response);
        assertEquals(customer.getBusinessEmail(), response.getBusinessEmail());
    }

    @Test
    public void createCustomerAddress() {
        CustomerAddressDetail addressDetail = CustomerAddressDetail.builder()
                .addressLine1("HSR").addressLine2("BTM").addressLine3("marathalli")
                .city("Bangalore")
                .sate("Karnataka").zipCode("1232").countryCode("1233").build();
        List<CustomerAddress> customerAddresses=new ArrayList<>();
        CustomerAddress customerAddress = getCustomerAddress();
        Customer customer = getCustomer();

        Mockito.when(addressRepository.save(new CustomerAddress()))
                .thenReturn(customerAddress);

        String correlationId="123";
        CustomerAddress response = customerConnectorsMock
                .createCustomerAddress(addressDetail, customer,correlationId);
        assertEquals(customerAddress, response);
        //assertEquals(addressDetail, response);
    }

    @Test
    public void createTier() {
        TierDetail tierDetail = TierDetail.builder().id(1l).tierName("Basic").build();
        Tier tier = getTier();
        Customer customer = getCustomer();
        Mockito.when(tierRepository.save(new Tier())).thenReturn(tier);
        String correlationId="123";
        Tier response = customerConnectorsMock.createTier(tierDetail, customer,correlationId);
        assertEquals(tier, response);
        assertEquals(tier.getTierName(), response.getTierName());
    }

//    @Test
//    public void createPackages() {
//        List<PackagesDetail> packages = new ArrayList<>();
//        Tier tier = getTier();
//        Customer customer=new Customer();
//        PackagesDetail packagesDetail = PackagesDetail.builder().id(1l)
//                .packageName("Dianing").build();
//        packages.add(packagesDetail);
//        String correlationId="123";
//        Packages packag=getPackages();
//        Mockito.when(packageRepository.save(new Packages())).thenReturn(packag);
//        List<Packages> response = customerConnectorsMock.createPackages(packages,customer, tier,correlationId);
//        assertEquals(packages, response);
//        assertEquals(packages.size(), response.size());
//    }


    @Test
    public void createBenefit() {
        List<BenefitDetail> benefitDetails = new ArrayList<>();
        List<Long> vList = new ArrayList<>();
        vList.add((long) 1);
        BenefitDetail benefitDetail = BenefitDetail.builder().id(1l).vendorId(vList).benefitName("custom").build();
        benefitDetails.add(benefitDetail);
        Customer customer = getCustomer();
        CustomerBenefit customerBenefit=getCustomerBenefits();
        Mockito.when( benefitRepository.save(new CustomerBenefit())).thenReturn(customerBenefit);
        String correlationId="123";
        List<CustomerBenefit> response = customerConnectorsMock
                .createBenefit(benefitDetails, customer,correlationId);
        assertEquals(benefitDetails.size(), response.size());

    }


    @Test
    public void createPackageBenefit() {
        List<Packages> details=new ArrayList<>();
        List<PackageBenefitsDetail> packageBenefitsDetails=new ArrayList<>();
        PackageBenefitsDetail packageBenefitsDetail= PackageBenefitsDetail.builder()
                .benefitId(1l).packageId(1l).build();
        packageBenefitsDetails.add(packageBenefitsDetail);
        List<CustomerBenefit> customerBenefits=new ArrayList<>();
        CustomerBenefit customerBenefit=getCustomerBenefits();
        customerBenefit.setCustomerBenefitId(1L);
        customerBenefit.setBenefitName("benefit1");
        customerBenefits.add(customerBenefit);
        Packages packages=getPackages();
        details.add(packages);
        PackagesBenefit packagesBenefit =getPackagesBenefit();
        Mockito.when( packageBenefitsRepository.save(new PackagesBenefit()))
                .thenReturn(packagesBenefit);
        String correlationId="123";
        PackagesDetail packagesDetail = PackagesDetail.builder().customerId(1L).packageName("Dianing").build();
        packagesDetail.setBenefits(customerBenefits);
        List<PackagesDetail> packagesDetailList = new ArrayList<>();
        packagesDetailList.add(packagesDetail);

        List<PackagesBenefit> response = customerConnectorsMock
                .createPackageBenefit(packagesDetailList,
                        customerBenefits, details,correlationId);
        assertEquals(packageBenefitsDetails.size(), response.size());

    }


    @Test
    public void getCustomerTest() {
        Customer customer=getCustomer();
        Mockito.when(customerRepositoryMock.getRecordById(1L)).thenReturn(customer);
        Customer response = customerConnectorsMock.getCustomer(1l,"123");
        assertEquals(customer, response);
        assertEquals(customer.getBusinessEmail(), response.getBusinessEmail());
    }

    @Test
    public void updateCustomer() {
        Customer customer = getCustomer();
        Organization organization=getOrganization();
        System.out.println("customer  >>" + customer);
        CustomerDetail customerDetail = CustomerDetail.builder().customerId(99l).phoneNo("12345").organizationId(1l)
                .userId(1l).businessName("Sangam").businessEmail("info@amazon.com").
                        businessType("software").currency("USD").region("USA").
                        isActive(Boolean.TRUE)
                .createdBy("admin").createdDate(new Date()).build();
        Mockito.when(organizationRepositoryMock.getRecordById(1l)).thenReturn(organization);
        Mockito.when(customerRepositoryMock.save(customer)).thenReturn(customer);
        String correlationId="123";
        Customer response = customerConnectorsMock.updateCustomer(customerDetail, customer,correlationId);
        assertEquals(customer, response);
        assertEquals(customer.getBusinessEmail(), response.getBusinessEmail());
    }

    @Test
    public void updateCustomerAddress() {
        CustomerAddressDetail addressDetail = CustomerAddressDetail.builder()
              .customerId(1l)
                .addressLine1("HSR").addressLine2("BTM").addressLine3("marathalli")
                .city("Bangalore")
                .sate("Karnataka").zipCode("1232").countryCode("1233").build();
        CustomerAddress customerAddress = getCustomerAddress();
        Customer customer = getCustomer();

        Mockito.when(addressRepository.getRecordById(1l))
                .thenReturn(customerAddress);

        Mockito.when(addressRepository.save(customerAddress))
                .thenReturn(customerAddress);
        String correlationId="123";
        CustomerAddress response = customerAdapterMock
                .updateCustomerAddress(addressDetail, customer,correlationId);
        assertEquals(customerAddress, response);
        assertEquals(customerAddress.getAddressLine1(), response.getAddressLine1());
    }



    @Test
    public void updateTier() {
        TierDetail tierDetail = TierDetail.builder().id(1l).tierName("Basic").build();
        Tier tier = getTier();
        Customer customer = getCustomer();
        Mockito.when( tierRepository.getRecordById(1l)).thenReturn(tier);
        Mockito.when(tierRepository.save(tier)).thenReturn(tier);
        String correlationId="123";
        Tier response = customerConnectorsMock.updateTier(tierDetail, customer,correlationId);
        System.out.println(";;;;;   >>>"+response.getTierName());
        assertEquals(tier, response);
        assertEquals(tier.getTierName(), response.getTierName());
    }

    @Test
    public void updatePackages() {
        Customer customer = getCustomer();
        Packages packages = getPackages();
        Tier tier = getTier();
        PackagesDetail packagesDetail = PackagesDetail.builder().id(1l)
                .packageName("Dianing").build();
        Mockito.when(packageRepository.getRecordById(1l)).thenReturn(packages);
        Mockito.when(packageRepository.save(packages)).thenReturn(packages);
        String correlationId="123";
        Packages response = customerConnectorsMock.updatePackages(packagesDetail, customer,correlationId);
        assertEquals(packages, response);
        assertEquals(packages.getPackageName(), response.getPackageName());
    }

    @Test
    public void updatePackageBenifit() {
        Customer customer = getCustomer();
        PackageBenefitsDetail packageBenefitsDetail= PackageBenefitsDetail.builder()
                .id(1l).benefitId(1l).packageId(1l).build();
        CustomerBenefit customerBenefit=getCustomerBenefits();
        Packages packages=getPackages();
        PackagesBenefit packagesBenefit =getPackagesBenefit();
        Mockito.when( packageBenefitsRepository.getRecordById(1l))
                .thenReturn(packagesBenefit);
        Mockito.when(packageRepository.getRecordById(1l))
                .thenReturn(packages);
        Mockito.when( benefitRepository.getRecordById(1l))
                .thenReturn(customerBenefit);
        Mockito.when( packageBenefitsRepository.save(packagesBenefit))
                .thenReturn(packagesBenefit);
        String correlationId="123";
        PackagesBenefit response = customerConnectorsMock
                .updatePackageBenifit(packageBenefitsDetail, customer,correlationId);
        assertEquals(packagesBenefit, response);
        assertEquals(packagesBenefit.getPackageBenefitId(), response.getPackageBenefitId());
    }

    @Test
    public void updateCustomerBenifit() {
        List<Long> vList = new ArrayList<>();
        vList.add((long) 1);
        BenefitDetail benefitDetail = BenefitDetail.builder().id(1l).vendorId(vList).benefitName("custom").build();
        Customer customer = getCustomer();
        CustomerBenefit customerBenefit=getCustomerBenefits();
        Mockito.when( benefitRepository.getRecordById(1l)).thenReturn(customerBenefit);
        Mockito.when( benefitRepository.save(customerBenefit)).thenReturn(customerBenefit);
        String correlationId="123";
      CustomerBenefit response = customerConnectorsMock
                .updateCustomerBenifit(benefitDetail, customer,correlationId);
        assertEquals(customerBenefit, response);
        assertEquals(customerBenefit.getCustomerBenefitId(), response.getCustomerBenefitId());
    }

    public Organization getOrganization() {
        Organization organization = Organization.builder().organizationId(1l)
                .organizationName("Java").
                        organizationDesc("desc").isActive(Boolean.TRUE)
                .createdBy("admin").createdDate(new Date()).build();
        System.out.println("org >>>" + organization.getOrganizationName());
        return organization;
    }

    public Customer getCustomer() {
        Organization organization = getOrganization();
        Customer customer = Customer.builder().organizationId(organization).customerId(5l).phone("12345")
                .userId(1l).businessName("Sangam").businessEmail("info@amazon.com").
                        businessType("software").currency("USD").region("USA")
                .isActive(Boolean.TRUE)
                .createdBy("admin").createdDate(new Date()).build();
        return customer;
    }

    public CustomerAddress getCustomerAddress() {
        CustomerAddress customerAddress = CustomerAddress.builder().customerAddressId(1l)
                .addressLine1("HSR").addressLine2("BTM").addressLine3("marathalli")
                .city("Bangalore").customerId(getCustomer())
                .sate("Karnataka").zipCode("1232").countryCode("1233").build();
        return customerAddress;
    }

    public Tier getTier() {
        Customer customer = getCustomer();

        Tier tier = Tier.builder().customerId(customer).tierId(1l).tierName("Basic").isActive(Boolean.TRUE)
                .createdBy("admin").createdDate(new Date()).build();
        return tier;
    }

    public Packages getPackages() {
        Tier tier = getTier();
        Packages packages = Packages.builder().tierId(tier).packageId(1l).packageName("Dianing").
                createdBy("Admin").createdDate(new Date()).build();
        return packages;
    }

    public CustomerBenefit getCustomerBenefits() {
        Customer customer = getCustomer();
        CustomerBenefit customerBenefit = CustomerBenefit.builder().customerId(customer).
                vendorId(1l).build();
        return customerBenefit;
    }


    public PackagesBenefit getPackagesBenefit() {
        Packages packages = getPackages();
        CustomerBenefit benefit = getCustomerBenefits();
        PackagesBenefit pckgBenfit = PackagesBenefit.builder().packageId(packages)
                .customerBenefitId(benefit).isActive(Boolean.TRUE)
                .createdBy("admin").createdDate(new Date()).build();

        return pckgBenfit;
    }



}
