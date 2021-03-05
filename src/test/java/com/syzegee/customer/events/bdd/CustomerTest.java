package com.syzegee.customer.events.bdd;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/Features" , dryRun = false, glue = "com.syzegee.customer.events.bdd.stepdefination", plugin = {
        "pretty", "html: cucumber-html-reports", "json: cucumber-html-reports/cucumber.json"})
public class CustomerTest {

}