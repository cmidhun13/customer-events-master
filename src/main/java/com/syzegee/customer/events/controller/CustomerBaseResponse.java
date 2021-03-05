package com.syzegee.customer.events.controller;

import com.syzegee.customer.events.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomerBaseResponse {
    //TODO Construct as a functional interface, Use one of the predicate method to pass the arguments
    public GenericResponse buildResponse(String successMessage, String successDescription,String failureMessage,
                                         String failureDescription,Object response) {
        GenericResponse httpResponse;
        if (Objects.nonNull(response)) {
             httpResponse = new GenericResponse(true, HttpStatus.OK.value(), successMessage, successDescription, response);

        }else{
            httpResponse = new GenericResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), failureMessage, failureDescription, null);
        }
        return httpResponse;
    }

}
