/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.controller;

import com.syzegee.customer.events.constants.Constants;
import com.syzegee.customer.events.model.*;
import com.syzegee.customer.events.entity.Customer;
import com.syzegee.customer.events.exception.CustomerServiceException;
import com.syzegee.customer.events.response.GenericResponse;
import com.syzegee.customer.events.service.ActivationService;
import com.syzegee.customer.events.service.CustomerService;
import com.syzegee.customer.events.util.CorrelationIdUtil;
import com.syzegee.customer.events.validator.CustomerValidator;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

/**
 * @author Sagar
 */
@Slf4j
@RestController
@RequestMapping("/v1/customer")
public class CustomerController extends CustomerBaseResponse {

	@Autowired
	CustomerService customerService;

	@Autowired
	ActivationService activationService;

	@Autowired
	CustomerValidator validator;

	@Autowired
	CustomerBaseResponse customerBaseResponse;

	@ApiOperation(value = "Api for Web Enabled")
	@PostMapping(path = "/webenabled")
	@ApiImplicitParams({
			@ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = "Authorization", required = true, paramType = "header", dataTypeClass = String.class),
			@ApiImplicitParam(name = Constants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
	})
	public ResponseEntity<GenericResponse> activationPostRequest(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,@RequestHeader(value = "correlationId",
			required = false, defaultValue = "") String correlationId, @RequestBody CustomerActivationDetails customerActivationDetails)
			throws CustomerServiceException, IOException {

		String generatedCorrelationId = CorrelationIdUtil.generateCorrelationId();
		log.info("Initiate activationPostRequest in controller : " + " - CorrelationId: " + generatedCorrelationId);
		CustomerActivationResponse activationResponse = null;
		GenericResponse response =null;
		validator.validateActivationFields(customerActivationDetails);
		activationResponse = activationService.createActivation(customerActivationDetails, generatedCorrelationId);
		response = customerBaseResponse.buildResponse("Customer created ","Customer creation completed",
				"Customer creation failed ","Customer creation failed",activationResponse);
		ResponseEntity responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		log.info("End of activationPostRequest in controller: " + " - CorrelationId: " + generatedCorrelationId);
		return responseEntity;
	}

	@ApiOperation(value = "Api for Activation")
	@PostMapping(path = "/activate")
	@ApiImplicitParams({
			@ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = "Authorization", required = true, paramType = "header", dataTypeClass = String.class),
			@ApiImplicitParam(name = Constants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
	})
	public ResponseEntity<GenericResponse> validateActCodePostRequest(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,@RequestHeader(value = "correlationId",
			required = false, defaultValue = "") String correlationId, @RequestBody ActivationValidateDetails activationValidateDetails)
			throws CustomerServiceException {
		String generatedCorrelationId = CorrelationIdUtil.generateCorrelationId();
		log.info("Initiate validateActCodePostRequest in controller : " + " - CorrelationId: " + generatedCorrelationId);
		validator.validateActivationCodeFields(activationValidateDetails);
		ActivationValidateResponse validateResponse;
		validateResponse = activationService.createValidateActivation(activationValidateDetails, generatedCorrelationId);
		GenericResponse response =null;
		ActivateResponse activateResponse = null;
		log.info("Initiate validateActCodePostRequest in controller Praveentest :" +validateResponse);
		log.info("Initiate validateActCodePostRequest in controller Praveen :" +validateResponse.equals(true));

		if(validateResponse.getStatus().equals(true)) {
			activateResponse = ActivateResponse.builder().customerId(validateResponse.getCustomerId()).build();
		}
		response = customerBaseResponse.buildResponse("Code activation completed ","Customer code activation completed",
				"Code activation failed ","Customer code activation failed",activateResponse);

		ResponseEntity responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		log.info("End of validateActCodePostRequest in controller: " + " - CorrelationId: " + generatedCorrelationId);
		return responseEntity;
	}

	@ApiOperation(value = "Api for fetching details based on CustomerId")
	@PutMapping("/{Id}")
	@ApiImplicitParams({
			@ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = "Authorization", required = true, paramType = "header", dataTypeClass = String.class),
			@ApiImplicitParam(name = Constants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
	})
	public ResponseEntity<GenericResponse> customerCreateRequest(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
																 @RequestHeader(value = "correlationId",required = false, defaultValue = "") String correlationId, @PathVariable long Id,
																 @RequestBody CustomerCreateDetail customerCreateDetail) throws CustomerServiceException, IOException {

		log.info("Request Input="+customerCreateDetail.toString());
		String generatedCorrelationId = CorrelationIdUtil.generateCorrelationId();
		log.info("Initiate customerCreateRequest in controller : " + " - CorrelationId: " + generatedCorrelationId);
		com.syzegee.customer.events.model.CustomerResponse customerCreateResponse = null;
		validator.validateRequiredFields(customerCreateDetail);
		customerCreateResponse = customerService.customerCreateEvent(customerCreateDetail, generatedCorrelationId,Id);
		GenericResponse response = customerBaseResponse.buildResponse("Customer create request has been sent successfully ","Customer create request has been received and you will be receiving an Email",
				"Code activation failed ","Customer code activation failed",customerCreateResponse);

		ResponseEntity responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		log.info("End of customerCreateRequest in controller: " + " - CorrelationId: " + generatedCorrelationId);
		return responseEntity;
	}

	@ApiOperation(value = "Api for Updating details for Customer using CustomerId")
	@PutMapping("update/{id}") // Analysis
	@ApiImplicitParams({
			@ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = "Authorization", required = true, paramType = "header", dataTypeClass = String.class),
			@ApiImplicitParam(name = Constants.ID, value = "Id", required = true, paramType = "pathVariable", dataTypeClass = String.class),
			@ApiImplicitParam(name = Constants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
	})
	public ResponseEntity<com.syzegee.customer.events.model.CustomerResponse> updateCustomerRequest(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,@RequestHeader(value = "correlationId",
			required = false, defaultValue = "") String correlationId, @RequestBody CustomerUpdateDetail customerUpdateDetail
			, @PathVariable long id) throws CustomerServiceException {
		String validCorrelationId = CorrelationIdUtil.generateCorrelationId(correlationId);
		log.info("Initiate customerPutRequestin controller : " + " - CorrelationId: " + validCorrelationId);
		com.syzegee.customer.events.model.CustomerResponse customerupdateResponse = null;
		customerupdateResponse = customerService.updateCustomerEvent(customerUpdateDetail, id, validCorrelationId);
		ResponseEntity responseEntity = new ResponseEntity<>(customerupdateResponse, HttpStatus.OK);
		log.info("End of customerPutRequest in controller: " + " - CorrelationId: " + validCorrelationId);
		return responseEntity;
	}

	@ApiOperation(value = "Update UserDetails")
	@PutMapping("update/userId")
	public ResponseEntity<GenericResponse> customerUpdate(@RequestBody CreateUpdateCustomerRequest customerUpdateRequest) throws CustomerServiceException {
		System.out.println("update customer -by praveen customerId" +customerUpdateRequest.getCustomerId());
		System.out.println("update customer -by praveen UserId" +customerUpdateRequest.getUserId());
		Customer customerResponse= customerService.updateCustomer(customerUpdateRequest);
		GenericResponse response = customerBaseResponse.buildResponse("Customer userId updated","Customer userId updated succesfully",
				"Customer userId update failed ","Customer userId update failed",null);

		ResponseEntity responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		log.info("End of customerUpdate in controller: " );
		return responseEntity;
	}

	@ApiOperation(value = "Updating status for customer based on CustomerId")
	@PatchMapping("/{Id}/status")
	@ApiImplicitParams({
			@ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = "Authorization", required = true, paramType = "header", dataTypeClass = String.class),
			@ApiImplicitParam(name = Constants.ID, value = "Id", required = true, paramType = "pathVariable", dataTypeClass = String.class),
			@ApiImplicitParam(name = Constants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
	})
	public ResponseEntity<GenericResponse> updateMarketingAutomationStatus(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization,
			@RequestHeader(value = "correlationId",required = false, defaultValue = "") String correlationId, @PathVariable long Id,
			@RequestBody CustomerSiteUpdateRequest customerSiteUpdateRequest) throws CustomerServiceException,Exception {
		System.out.println("update customer -by praveen UserId" +customerSiteUpdateRequest.getCustomerDetail().getUserId());
		String validCorrelationId = CorrelationIdUtil.generateCorrelationId(correlationId);
		CustomerDomainStatusUpdateEvent marketingAutomation = CustomerDomainStatusUpdateEvent.builder().correlationId("12333")
				.customerEmail("selvaganpr@gmail.com")
				.customerId(Id)
				.customerFirstName("Selva")
				.customerLastName("Sakthivel")
				.customerUserName("Selva")
				.customerOrganizationName("abc")
				.siteName("www.testSite")
				.createdBy("mautic")
				.createdDate(new Date())
				.domainName("www.rwsaw")
				.siteType("Marketing Automation")
				.templateCode("01001")
				.build();
		customerService.updateMarketingAutomationStatus(marketingAutomation);
		GenericResponse response =null;
		response =new GenericResponse(true,HttpStatus.OK.value(),"status ","Customer status is updated","");
		ResponseEntity responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		log.info("End of customerUpdate in controller: " );
		return responseEntity;
	}

	@ApiOperation(value = "Updating customer details based on userId")
	@PatchMapping("/{Id}")
	@ApiImplicitParams({
			@ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = "Authorization", required = true, paramType = "header", dataTypeClass = String.class),
			@ApiImplicitParam(name = Constants.ID, value = "Id", required = true, paramType = "pathVariable", dataTypeClass = String.class),
			@ApiImplicitParam(name = Constants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
	})
	public ResponseEntity<GenericResponse> updateCustomerSites(
			@RequestHeader(value = "correlationId",required = false, defaultValue = "") String correlationId, @PathVariable long Id,
			@RequestBody CustomerSiteUpdateRequest customerSiteUpdateRequest) throws CustomerServiceException,Exception {
		System.out.println("update customer -by praveen UserId" +customerSiteUpdateRequest.getCustomerDetail().getUserId());
		String validCorrelationId = CorrelationIdUtil.generateCorrelationId(correlationId);
		com.syzegee.customer.events.model.CustomerResponse
				customerResponse= customerService.updateCustomerSitesEvent(customerSiteUpdateRequest,correlationId,Id);

		GenericResponse response = customerBaseResponse.buildResponse("Customer userId updated","Customer userId updated succesfully",
				"Customer userId update failed ","Customer userId update failed",customerResponse);

		ResponseEntity responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		log.info("End of customerUpdate in controller: " );
		return responseEntity;
	}


}
