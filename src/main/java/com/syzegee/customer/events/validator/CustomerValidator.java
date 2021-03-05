package com.syzegee.customer.events.validator;

import com.syzegee.customer.events.exception.CustomerServiceException;
import com.syzegee.customer.events.model.*;
import com.syzegee.customer.events.entity.Customer;
import com.syzegee.customer.events.entity.CustomerUser;
import com.syzegee.customer.events.exception.CustomerRuntimeException;
import com.syzegee.customer.events.repository.CustomerRepository;
import com.syzegee.customer.events.repository.CustomerUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sagar
 */
@Slf4j
@Component
public class CustomerValidator {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerUserRepository userRepository;

	public boolean validateEntity(String fieldName, Object entity) {
		if (entity != null) {
			log.info("field value is:" + entity);
			return true;
		} else {
			throw new CustomerServiceException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), fieldName + " is not present " +
					"with given id");
		}
	}


	public void validateActivationFields(CustomerActivationDetails customerActivationDetails) throws CustomerRuntimeException {
		if (customerActivationDetails != null) {
			this.validateField("organization", customerActivationDetails.getOrgDetail().getOrganizationName());
			this.validateField("customer_user", customerActivationDetails.getUserDetail().getFirstName());
			this.validateField("customer_user", customerActivationDetails.getUserDetail().getLastName());
			this.validateField("customer_user", customerActivationDetails.getUserDetail().getEmailId());
			CustomerUser customerUser = userRepository.getCustomerUserByEmailId(customerActivationDetails.getUserDetail().getEmailId());
			if (customerUser != null) {
				throw new CustomerServiceException(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),"Customer User ID :" + customerUser.getEmailId() + " Already Exist!! Please provide new email address");
			}
		} else {
			throw new  CustomerServiceException(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),"Invalid Payload. Please verify the input request");
		}
	}


	public void validateActivationCodeFields(ActivationValidateDetails activationValidateDetails) throws CustomerRuntimeException {
		if (activationValidateDetails != null) {
			this.validateField("customer", activationValidateDetails.getActivationCode());
			this.validateField("customer", activationValidateDetails.getCustomerUserId());
		} else
			throw new  CustomerServiceException(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),"Invalid Payload. Please verify the input request");
	}

	public void validateRequiredFields(CustomerCreateDetail createDetail) throws CustomerRuntimeException {
		if (createDetail != null) {
			this.validateField("organization", createDetail.getOrgDetail().getOrganizationSize());
			this.validateField("customer", createDetail.getCustomerDetail().getRegion());
			this.validateField("customer", createDetail.getCustomerDetail().getBusinessType());
			this.validateField("customer", createDetail.getCustomerDetail().getBusinessCategory());
			this.validateField("Address", createDetail.getCustAddressDetail().getAddressLine1());
			this.validateField("Tier", createDetail.getTierDetail().getTierName());
			if(createDetail.getPackagesDetail()!=null&&!createDetail.getPackagesDetail().isEmpty())
			{
				for (PackagesDetail detail : createDetail.getPackagesDetail()) {
					this.validateField("Package", detail.getPackageName());
				}
			}
			if(createDetail.getBenefitDetail()!=null&&!createDetail.getBenefitDetail().isEmpty()) {
				for (BenefitDetail benefitDetail : createDetail.getBenefitDetail()) {
					this.validateField("Benefit", benefitDetail.getBenefitName());
				}
			}
			try {
				System.out.println("createDetail.getPackagesDetail()"+createDetail.getPackagesDetail());
				if(createDetail.getPackagesDetail()!=null&&!createDetail.getPackagesDetail().isEmpty()) {
					for (PackagesDetail packageBenefitsDetail : createDetail.getPackagesDetail()) {
						this.validateField("PackageBenefits", packageBenefitsDetail.getPackageName());
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}

		} else
			throw new CustomerServiceException(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),"Invalid Payload");
	}

	/**
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public boolean validateField(String fieldName, String fieldValue) {
		if (fieldValue != null && !fieldValue.equals("null") && !fieldValue.trim().isEmpty()) {
			log.info("field value is test:" + fieldValue + "fieldname is:" + fieldName);
			return true;
		} else
			throw new CustomerServiceException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
					"" + fieldName + ": " + fieldValue + " Invalid Input, field cannot be null");
	}

	@Transactional
	//TODO move the Respository logic into the class
	public ActivationValidateResponse validateActivationCode(ActivationValidateDetails activationValidateDetails) {
		ActivationValidateResponse validateResponse;
		CustomerUser customerUser = userRepository.getCustomerUserByEmailId(activationValidateDetails.getCustomerUserId());

		if (customerUser != null) {
			Customer customer = customerRepository.getActiveCustomerById(customerUser.getCustomerId().getCustomerId());
			if(customer.getActivationStatus() == true) {
				throw new  CustomerServiceException(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),"Customer Activation code is already completed");
			}
			if (customer != null) {
				if (customer.getActivationCode().equals(activationValidateDetails.getActivationCode())) {
					customer.setActivationStatus(Boolean.TRUE);
					Customer save = customerRepository.save(customer);
					validateResponse = ActivationValidateResponse.builder().status(save.getActivationStatus()).customerId(save.getCustomerId()).build();
					return validateResponse;
				} else {
					throw new  CustomerServiceException(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),"Customer Activation code did not match Please Re-enter Your Activation Code");
				}
			} else {
				throw new  CustomerServiceException(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),"Customer Activation Code Not Found!!");
			}
		} else {
			throw new  CustomerServiceException(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),"Customer userId Not Found!");
		}
	}
}
