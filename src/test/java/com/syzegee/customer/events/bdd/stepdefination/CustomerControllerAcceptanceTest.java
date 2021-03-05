package com.syzegee.customer.events.bdd.stepdefination;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.syzegee.customer.events.model.*;
import com.syzegee.customer.events.entity.Customer;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerControllerAcceptanceTest {
    private final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    CustomerCreateDetail customerCreateDetail;
    OrganizationDetail organizationDetail;
    CustomerUpdateDetail customerUpdateDetail;
    Long customerId;
    HttpResponse response;

    @Given("^I am user and entering customer details$")
    public void i_am_user_and_entering_customer_details() throws Throwable {
        CustomerDetail customerDetail = CustomerDetail.builder().customerId(1l).userId(1l)
                .businessName("Amazon").businessEmail("info@amazon.com").phoneNo("12335453")
                .businessType("software").currency("USD").region("USA")
                .createdBy("admin").createdDate(new Date()).updatedBy("admin").updatedDate(new Date()).build();


        List<CustomerAddressDetail> custAddressDetail = new ArrayList<>();
        List<PackagesDetail> detailList = new ArrayList<>();
        CustomerAddressDetail customerAddressDetail = CustomerAddressDetail.builder()
                .addressLine1("HSR").addressLine2("BTM").addressLine3("marathalli").city("Bangalore")
                .sate("Karnataka").zipCode("1232").countryCode("1233").build();
        custAddressDetail.add(customerAddressDetail);

        TierDetail tierDetail = TierDetail.builder().id(1l).tierName("Basic").build();

        PackagesDetail packagesDetail = PackagesDetail.builder().id(1l).packageName("Dianing").build();
        detailList.add(packagesDetail);
        List<BenefitDetail> benefitDetails = new ArrayList<>();
        List<Long> vList = new ArrayList<>();
        vList.add((long) 1);
        BenefitDetail benefitDetail = BenefitDetail.builder().id(1l).vendorId(vList).benefitName("custom").build();
        benefitDetails.add(benefitDetail);

        List<PackageBenefitsDetail> packageBenefitsDetails = new ArrayList<>();
        PackageBenefitsDetail packageBenefitsDetail = PackageBenefitsDetail.builder().benefitId(1l).packageId(1l).build();
        packageBenefitsDetails.add(packageBenefitsDetail);


        Customer customer = Customer.builder().customerId(1l).build();

        customerCreateDetail = CustomerCreateDetail.builder().orgDetail(organizationDetail)
                .customerDetail(customerDetail).custAddressDetail(customerAddressDetail).tierDetail(tierDetail)
                .packagesDetail(detailList).benefitDetail(benefitDetails)./*packageBenefitsDetail(packageBenefitsDetails)
                .*/build();


    }

    @When("^organization is not provided$")
    public void organization_is_not_provided() throws Throwable {
        organizationDetail = null;

    }


    @Then("^Call create api with givens details$")
    public void call_create_api_with_givens_details() throws Throwable {
        String customerPayload = mapToJson(customerCreateDetail);
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        stubFor(post("/customer/v1/create")
                .withRequestBody(containing(customerPayload))
                .willReturn(aResponse().withStatus(HttpStatus.BAD_REQUEST.value()).withStatusMessage(
                        "Organization is empty")
                ));
        HttpPost request = new HttpPost(
                "http://localhost:" + wireMockServer.port() + "/customer/v1/create");
        StringEntity body = new StringEntity(customerPayload);
        request.setEntity(body);
        response = httpClient.execute(request);
        System.out.println("response>>>   " + response.getStatusLine().getStatusCode());
    }

    @Then("^Return client error as  organization is not provided in payload post request$")
    public void return_client_error_organization_is_not_provided_in_post_request() throws Throwable {
        assertEquals("Organization is empty",
                response.getStatusLine().getReasonPhrase());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusLine().getStatusCode());
        wireMockServer.stop();
    }

    @Given("^Enter customer details with organization in payload$")
    public void enter_customer_details_with_organization_in_payload() throws Exception {
        CustomerDetail customerDetail = CustomerDetail.builder().customerId(1l).userId(1l)
                .businessName("Amazon").businessEmail("info@amazon.com").phoneNo("12335453")
                .businessType("software").currency("USD").region("USA")
                .createdBy("admin").createdDate(new Date()).updatedBy("admin").updatedDate(new Date()).build();


        List<CustomerAddressDetail> custAddressDetail = new ArrayList<>();
        List<PackagesDetail> detailList = new ArrayList<>();
        CustomerAddressDetail customerAddressDetail = CustomerAddressDetail.builder()
                .addressLine1("HSR").addressLine2("BTM").addressLine3("marathalli").city("Bangalore")
                .sate("Karnataka").zipCode("1232").countryCode("1233").build();
        custAddressDetail.add(customerAddressDetail);

        TierDetail tierDetail = TierDetail.builder().id(1l).tierName("Basic").build();

        PackagesDetail packagesDetail = PackagesDetail.builder().id(1l).packageName("Dianing").build();
        detailList.add(packagesDetail);
        List<BenefitDetail> benefitDetails = new ArrayList<>();

        List<Long> vList = new ArrayList<>();
        vList.add((long) 1);
        BenefitDetail benefitDetail = BenefitDetail.builder().id(1l).vendorId(vList).benefitName("custom").build();
        benefitDetails.add(benefitDetail);

        List<PackageBenefitsDetail> packageBenefitsDetails = new ArrayList<>();
        PackageBenefitsDetail packageBenefitsDetail = PackageBenefitsDetail.builder().benefitId(1l).packageId(1l).build();
        packageBenefitsDetails.add(packageBenefitsDetail);

        List<CustomerUserDetail> customerUserDetails = new ArrayList<>();
        Customer customer = Customer.builder().customerId(1l).build();
        customerCreateDetail = CustomerCreateDetail.builder().orgDetail(organizationDetail)
                .customerDetail(customerDetail).custAddressDetail(customerAddressDetail).tierDetail(tierDetail)
                .packagesDetail(detailList).benefitDetail(benefitDetails)./*packageBenefitsDetail(packageBenefitsDetails)
                .*/build();
    }

    @When("^organization is provided with valid id$")
    public void organization_is_provided_with_valid_id() throws Exception {
        organizationDetail = OrganizationDetail.builder().organizationId(1l).organizationName("Amazon")
                .organizationDesc("desc").build();
    }

    @Then("^create the customer with given details$")
    public void create_the_customer_with_given_details() throws Exception {
        String customerPayload = mapToJson(customerCreateDetail);
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        stubFor(post("/customer/v1/create")
                .withRequestBody(containing(customerPayload))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                ));
        HttpPost request = new HttpPost(
                "http://localhost:" + wireMockServer.port() + "/customer/v1/create");
        StringEntity body = new StringEntity(customerPayload);
        request.setEntity(body);
        response = httpClient.execute(request);
        System.out.println("response>>>   " + response.getStatusLine().getStatusCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusLine().getStatusCode());
        wireMockServer.stop();
    }

    public static ResponseDefinitionBuilder aResponse() {
        return new ResponseDefinitionBuilder();
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Given("^I am user and entering update details$")
    public void i_am_user_and_entering_update_details() throws Exception {
        CustomerDetail customerDetail = CustomerDetail.builder().customerId(1l).userId(1l)
                .businessName("Amazon").businessEmail("info@amazon.com").phoneNo("12335453")
                .businessType("software").currency("USD").region("USA")
                .createdBy("admin").createdDate(new Date()).updatedBy("admin").updatedDate(new Date()).build();
        CustomerAddressDetail customerAddressDetail = CustomerAddressDetail.builder()
                .addressLine1("HSR").addressLine2("BTM").addressLine3("marathalli").city("Bangalore")
                .sate("Karnataka").zipCode("1232").countryCode("1233").build();

        TierDetail tierDetail = TierDetail.builder().id(1l).tierName("Basic").build();

        PackagesDetail packagesDetail = PackagesDetail.builder().id(1l).packageName("Dianing").build();
        List<Long> vList = new ArrayList<>();
        vList.add((long) 1);
        BenefitDetail benefitDetail = BenefitDetail.builder().id(1l).vendorId(vList).
                benefitName("custom").build();
        PackageBenefitsDetail packageBenefitsDetail = PackageBenefitsDetail.builder().benefitId(1l).packageId(1l).build();
        Customer customer = Customer.builder().customerId(1l).build();
        customerUpdateDetail = CustomerUpdateDetail.builder()
                .customerDetail(customerDetail).custAddressDetail(customerAddressDetail).tierDetail(tierDetail)
                .packagesDetail(packagesDetail).benefitDetail(benefitDetail).packageBenefitsDetail(packageBenefitsDetail).build();
        String payload = mapToJson(customerUpdateDetail);
    }

    @When("^customerId is not provided$")
    public void customerid_is_not_provided() throws Exception {
        customerId = null;
    }

    @Then("^Call update api with givens details$")
    public void call_update_api_with_givens_details() throws Exception {
        String customerPayload = mapToJson(customerCreateDetail);
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        stubFor(put("/customer/v1/update" + customerId)
                .withRequestBody(containing(customerPayload))
                .willReturn(aResponse().withStatus(HttpStatus.BAD_REQUEST.value()).withStatusMessage(
                        "customer with given id is not present in record")
                ));
        HttpPut request = new HttpPut(
                "http://localhost:" + wireMockServer.port() + "/customer/v1/update" + customerId);
        StringEntity body = new StringEntity(customerPayload);
        request.setEntity(body);
        response = httpClient.execute(request);
    }

    @Then("^Return client error as  update is not provided in payload put request$")
    public void return_client_error_as_update_is_not_provided_in_payload_put_request() throws Exception {
        assertEquals("customer with given id is not present in record",
                response.getStatusLine().getReasonPhrase());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusLine().getStatusCode());
        wireMockServer.stop();
    }

    @Given("^Enter customer details with valid customerId in payload$")
    public void enter_customer_details_with_valid_customerId_in_payload() throws Exception {
        CustomerDetail customerDetail = CustomerDetail.builder().customerId(1l).userId(1l)
                .businessName("Amazon").businessEmail("info@amazon.com").phoneNo("12335453")
                .businessType("software").currency("USD").region("USA")
                .createdBy("admin").createdDate(new Date()).updatedBy("admin").updatedDate(new Date()).build();
        CustomerAddressDetail customerAddressDetail = CustomerAddressDetail.builder()
                .addressLine1("HSR").addressLine2("BTM").addressLine3("marathalli").city("Bangalore")
                .sate("Karnataka").zipCode("1232").countryCode("1233").build();
        TierDetail tierDetail = TierDetail.builder().id(1l).tierName("Basic").build();
        PackagesDetail packagesDetail = PackagesDetail.builder().id(1l).packageName("Dianing").build();
        List<Long> vList = new ArrayList<>();
        vList.add((long) 1);
        BenefitDetail benefitDetail = BenefitDetail.builder().id(1l).vendorId(vList).
                benefitName("custom").build();
        PackageBenefitsDetail packageBenefitsDetail = PackageBenefitsDetail.builder().benefitId(1l).packageId(1l).build();
        Customer customer = Customer.builder().customerId(1l).build();
        customerUpdateDetail = CustomerUpdateDetail.builder()
                .customerDetail(customerDetail).custAddressDetail(customerAddressDetail).tierDetail(tierDetail)
                .packagesDetail(packagesDetail).benefitDetail(benefitDetail).packageBenefitsDetail(packageBenefitsDetail).build();
        String payload = mapToJson(customerUpdateDetail);
        System.out.println("payload>>>  " + payload);
    }

    @When("^update details is provided with valid id$")
    public void update_details_is_provided_with_valid_id() throws Exception {
        customerId = 1l;
    }

    @Then("^update the customer with given details$")
    public void update_the_customer_with_given_details() throws Exception {
        String customerPayload = mapToJson(customerCreateDetail);
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        stubFor(put("/customer/v1/update/" + customerId)
                .withRequestBody(containing(customerPayload))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                ));
        HttpPut request = new HttpPut(
                "http://localhost:" + wireMockServer.port() + "/customer/v1/update/" + customerId);
        StringEntity body = new StringEntity(customerPayload);
        request.setEntity(body);
        response = httpClient.execute(request);
        System.out.println("response>>>   " + response.getStatusLine().getStatusCode());
        assertEquals(HttpStatus.OK.value(), response.getStatusLine().getStatusCode());
        wireMockServer.stop();
    }
}
