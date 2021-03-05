package com.syzegee.customer.events.controller;

import com.syzegee.customer.events.constants.Constants;
import com.syzegee.customer.events.entity.CustomerRole;
import com.syzegee.customer.events.exception.CustomerServiceException;
import com.syzegee.customer.events.model.CustomerUserRequest;
import com.syzegee.customer.events.model.CustomerUserResponse;
import com.syzegee.customer.events.response.GenericResponse;
import com.syzegee.customer.events.service.CustomerService;
import com.syzegee.customer.events.util.CorrelationIdUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/v1/customer")
public class CustomerUserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerBaseResponse customerBaseResponse;

    @PostMapping("/{Id}/users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = "Authorization", required = true, paramType = "header", dataTypeClass = String.class),
            @ApiImplicitParam(name = Constants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
    })
    public ResponseEntity<GenericResponse> customerUserRoleCreate(@RequestHeader(value = "correlationId", required = false, defaultValue = "") String correlationId,
                                                                  @PathVariable long Id,
                                                                   @RequestBody CustomerUserRequest customerUserRequest) throws CustomerServiceException {

        log.info("Request Input=" + customerUserRequest.toString());
        System.out.println("Request Input=" + customerUserRequest.toString());
        String generatedCorrelationId = CorrelationIdUtil.generateCorrelationId();
        log.info("Initiate customerUserRequest in controller : " + " - CorrelationId: " + generatedCorrelationId);
        CustomerUserResponse customerUserResponse = customerService.addCustomerUser(customerUserRequest,generatedCorrelationId, Id);
        GenericResponse response = customerBaseResponse.buildResponse("CustomerUser create request has been sent successfully ",
                "CustomerUser has been created successfully with given roles",
                "CustomerUser creation failed","CustomerUser creation failed",customerUserResponse);

        ResponseEntity responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        return responseEntity;

    }
}
