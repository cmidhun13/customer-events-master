/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.controller;

import com.syzegee.customer.events.service.CustomerService;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 *
 * @author Akshay
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Ignore
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    CustomerService customerService;
//    @MockBean
//    CustomerValidator validator;
    /**
     * Test of creatCustomer method, of class CustomerController.
     */
//    @Test
//    public void testCreatCustomer() throws Exception {
//        System.out.println("creatCustomer");
//        String url = "/customer/v1";
//        CustomerDetail customerDetail= CustomerDetail.builder().customerId(1l).userId("User1")
//                .businessName("Amazon").businessEmail("info@amazon.com").phoneNo("12335453")
//                .businessType("software").currency("USD").region("USA")
//                .createdBy("admin").createdDate(new Date()).updatedBy("admin").updatedDate(new Date()).build();
//        OrganizationDetail organizationDetail= OrganizationDetail.builder().organizationId(1l).organizationName("Amazon")
//                .organizationDesc("desc").build();
//        List<CustomerAddressDetail> custAddressDetail= new ArrayList<>();
//        CustomerAddressDetail customerAddressDetail= CustomerAddressDetail.builder().customerAddressId(1l)
//                .addressLine1("HSR").addressLine2("BTM").addressLine3("marathalli").city("Bangalore")
//                .sate("Karnataka").zipCode("1232").countryCode("1233").build();
//        custAddressDetail.add(customerAddressDetail);
//        TierDetail tierDetail= TierDetail.builder().id(1l).tierName("Basic").build();
//        List<PackagesDetail> detailList=new ArrayList<>();
//        PackagesDetail packagesDetail= PackagesDetail.builder().id(1l).packageName("Dianing").build();
//        detailList.add(packagesDetail);
//        List<BenefitDetail> benefitDetails=new ArrayList<>();
//        BenefitDetail benefitDetail = BenefitDetail.builder().id(1l).vendorId(1l).benefitName("custom").build();
//        benefitDetails.add(benefitDetail);
//        List<PackageBenefitsDetail> packageBenefitsDetails=new ArrayList<>();
//        PackageBenefitsDetail packageBenefitsDetail= PackageBenefitsDetail.builder().benefitId(1l).packageId(1l).build();
//        packageBenefitsDetails.add(packageBenefitsDetail);
//        Customer customer = Customer.builder().customerId(1l).build();
//        CustomerCreateDetail customerCreateDetail = CustomerCreateDetail.builder().orgDetail(organizationDetail)
//                .customerDetail(customerDetail).custAddressDetail(custAddressDetail).tierDetail(tierDetail)
//                .packagesDetail(detailList).benefitDetail(benefitDetails).packageBenefitsDetail(packageBenefitsDetails)
//                       .build();
//        String payload=mapToJson(customerCreateDetail);
//        System.out.println("payload>>>  "+payload);
//        RequestBuilder request = MockMvcRequestBuilders
//                .post(url)
//                .accept(MediaType.APPLICATION_JSON).
//                header("correlationId","")
//                .content(payload)
//                //.characterEncoding("utf-8")
//                .contentType(MediaType.APPLICATION_JSON);
//        MvcResult result1 = mockMvc.perform(request)
//                .andExpect(status().isOk()).andReturn();
//    }


//    @Test
//    public void testCustomerPutRequest()throws Exception{
//        System.out.println("creatCustomer");
//        String url = "/customer/v1/41";
//        CustomerDetail customerDetail= CustomerDetail.builder().customerId(1l).userId("User1")
//                .businessName("Amazon").businessEmail("info@amazon.com").phoneNo("12335453")
//                .businessType("software").currency("USD").region("USA")
//                .createdBy("admin").createdDate(new Date()).updatedBy("admin").updatedDate(new Date()).build();
//        CustomerAddressDetail customerAddressDetail= CustomerAddressDetail.builder().customerAddressId(1l)
//                .addressLine1("HSR").addressLine2("BTM").addressLine3("marathalli").city("Bangalore")
//                .sate("Karnataka").zipCode("1232").countryCode("1233").build();
//        TierDetail tierDetail= TierDetail.builder().id(1l).tierName("Basic").build();
//        PackagesDetail packagesDetail= PackagesDetail.builder().id(1l).packageName("Dianing").build();
//        BenefitDetail benefitDetail = BenefitDetail.builder().id(1l).vendorId(1l).
//                benefitName("custom").build();
//        PackageBenefitsDetail packageBenefitsDetail= PackageBenefitsDetail.builder().benefitId(1l).packageId(1l).build();
//        Customer customer = Customer.builder().customerId(1l).build();
//        CustomerUpdateDatail customerUpdateDatail = CustomerUpdateDatail.builder()
//                .customerDetail(customerDetail).custAddressDetail(customerAddressDetail).tierDetail(tierDetail)
//                .packagesDetail(packagesDetail).benefitDetail(benefitDetail).packageBenefitsDetail(packageBenefitsDetail).build();
//        String payload=mapToJson(customerUpdateDatail);
//        System.out.println("payload>>>  "+payload);
//        RequestBuilder request = MockMvcRequestBuilders
//                .put(url)
//                .header("correlationId","")
//                .accept(MediaType.APPLICATION_JSON)
//                .content(payload)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        MvcResult result1 = mockMvc.perform(request)
//                .andExpect(status().isOk()).andReturn();
//    }
//    private String mapToJson(Object object) throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.writeValueAsString(object);
//    }

}
