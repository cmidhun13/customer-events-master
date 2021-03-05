$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/test/resources/Features/create_customer.feature");
formatter.feature({
  "name": "Testing customer REST API",
  "description": "",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "create customer fails when organization is not provided",
  "description": "",
  "keyword": "Scenario"
});
formatter.step({
  "name": "I am user and entering customer details",
  "keyword": "Given "
});
formatter.match({
  "location": "CustomerControllerAcceptanceTest.i_am_user_and_entering_customer_details()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "organization is not provided",
  "keyword": "When "
});
formatter.match({
  "location": "CustomerControllerAcceptanceTest.organization_is_not_provided()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Call create api with givens details",
  "keyword": "Then "
});
formatter.match({
  "location": "CustomerControllerAcceptanceTest.call_create_api_with_givens_details()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Return client error as  organization is not provided in payload post request",
  "keyword": "And "
});
formatter.match({
  "location": "CustomerControllerAcceptanceTest.return_client_error_organization_is_not_provided_in_post_request()"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "create customer Success",
  "description": "",
  "keyword": "Scenario"
});
formatter.step({
  "name": "Enter customer details with organization in payload",
  "keyword": "Given "
});
formatter.match({
  "location": "CustomerControllerAcceptanceTest.enter_customer_details_with_organization_in_payload()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "organization is provided with valid id",
  "keyword": "When "
});
formatter.match({
  "location": "CustomerControllerAcceptanceTest.organization_is_provided_with_valid_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "create the customer with given details",
  "keyword": "Then "
});
formatter.match({
  "location": "CustomerControllerAcceptanceTest.create_the_customer_with_given_details()"
});
formatter.result({
  "status": "passed"
});
formatter.uri("src/test/resources/Features/update_customer.feature");
formatter.feature({
  "name": "Testing customer update REST API",
  "description": "",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "update customer fails when customerId not provided",
  "description": "",
  "keyword": "Scenario"
});
formatter.step({
  "name": "I am user and entering update details",
  "keyword": "Given "
});
formatter.match({
  "location": "CustomerControllerAcceptanceTest.i_am_user_and_entering_update_details()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "customerId is not provided",
  "keyword": "When "
});
formatter.match({
  "location": "CustomerControllerAcceptanceTest.customerid_is_not_provided()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Call update api with givens details",
  "keyword": "Then "
});
formatter.match({
  "location": "CustomerControllerAcceptanceTest.call_update_api_with_givens_details()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Return client error as  update is not provided in payload put request",
  "keyword": "And "
});
formatter.match({
  "location": "CustomerControllerAcceptanceTest.return_client_error_as_update_is_not_provided_in_payload_put_request()"
});
formatter.result({
  "status": "passed"
});
formatter.scenario({
  "name": "update customer Success",
  "description": "",
  "keyword": "Scenario"
});
formatter.step({
  "name": "Enter customer details with valid customerId in payload",
  "keyword": "Given "
});
formatter.match({
  "location": "CustomerControllerAcceptanceTest.enter_customer_details_with_valid_customerId_in_payload()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "update details is provided with valid id",
  "keyword": "When "
});
formatter.match({
  "location": "CustomerControllerAcceptanceTest.update_details_is_provided_with_valid_id()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "update the customer with given details",
  "keyword": "Then "
});
formatter.match({
  "location": "CustomerControllerAcceptanceTest.update_the_customer_with_given_details()"
});
formatter.result({
  "status": "passed"
});
});