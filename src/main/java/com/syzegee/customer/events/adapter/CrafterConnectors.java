package com.syzegee.customer.events.adapter;


import com.syzegee.customer.events.domain.crafter.*;
import com.syzegee.customer.events.util.CrafterTokenUtil;
import com.syzegee.customer.events.util.CrafterXmlConverter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.syzegee.customer.events.constants.Constants.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;


/**
 * @author Sagar
 */
@Slf4j
@Component
public class CrafterConnectors {

    @Value("${Crafter.url}")
    private  String crafterSiteURL;
    @Value("${Crafter.userName}")
    private String crafterUserName;
    @Value("${Crafter.firstName}")
    private String crafterFirstName;
    @Value("${Crafter.lastName}")
    private String crafterLastName;
    @Value("${Crafter.groups}")
    private String crafterGroups;
    @Value("${Crafter.email}")
    private String email;
    @Value("${Crafter.secureKey}")
    private String crafterSecureKey;
    @Value("${Crafter.password}")
    private String crafterPassword;
    @Value("${Crafter.authenticationUrl}")
    private String authenticationUrl;
    @Value("${Crafter.createUserUrl}")
    private String createUserUrl;
    @Value("${Crafter.addMemberToGroupUrl}")
    private String addMemberToGroupUrl;
    @Value("${Crafter.startSiteUrl}")
    private String startSiteUrl;
    @Value("${Crafter.createSiteUrl}")
    private String createSiteUrl;
    @Value("${Crafter.writeContent.writeContentUrl}")
    private String writeContentUrl;
    @Value("${Crafter.writeContent.phase}")
    private String phase;
    @Value("${Crafter.writeContent.path}")
    private String path;
    @Value("${Crafter.writeContent.fileName}")
    private String fileName;
    @Value("${Crafter.writeContent.user}")
    private String user;
    @Value("${Crafter.writeContent.contentType}")
    private String contentType;
    @Value("${Crafter.writeContent.unlock}")
    private Boolean unlock;
    @Value("${Crafter.deployment.url}")
    private String deploymentUrl;

    @Value("${Crafter.jessionIdValue}")
    private String JSESSIONID_VALUE;
    @Value("${Crafter.xXsrfTokenValue}")
    private String X_XSRF_TOKEN_VALUE;

    private Client restClient = ClientBuilder.newClient();

    private CrafterXmlConverter xmlConverter;

    public CrafterConnectors() {
        xmlConverter = new CrafterXmlConverter();
    }

    public CrafterAuhtnticatedToken authenticateCrafter(CrafterLogin login) {
        Response response = restClient.target(crafterSiteURL)
                .request(MediaType.APPLICATION_JSON)
                .header(FIRSTNAME,login.getFirstname())
                .header(LASTNAME,login.getLastname())
                .header(EMAIL,login.getEmail())
                .header(USERNAME,login.getUsername())
                .header(SECURE_KEY,login.getSecureKey())
                .header(GROUPS,login.getGroups())
                .header(X_XSRF_TOKEN, X_XSRF_TOKEN_VALUE)
                .get();
        log.info("response status  authenticateCrafter: "+ response.getStatus());
        return getResponseToken(response);

    }

    private CrafterAuhtnticatedToken getResponseToken(Response response){
        String xsrfToken  = null;
        String jessionIdToken = null;
        MultivaluedMap<String, Object> headers = response.getHeaders();

        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
            if(HEADER_NAME_SET_COOKIE.equals(entry.getKey())) {
                log.info("entry Key :"+ entry.getKey() + ": entry.Key vAlue: "+ entry.getValue());
                List<Object> keyObjects = entry.getValue();
                for (Object temp : keyObjects) {
                    String tokenValue = (String) temp;
                    if (isNotBlank(tokenValue)) {
                        if (tokenValue.contains(HEADER_NAME_XSRF_TOKEN)){
                            xsrfToken = CrafterTokenUtil.token(tokenValue);
                        }
                        if(tokenValue.contains(HEADER_NAME_JSESSIONID)){
                            jessionIdToken = CrafterTokenUtil.token(tokenValue);
                        }
                    }
                }
                break;
            }
        }
        CrafterAuhtnticatedToken crafterAuhtnticatedToken = CrafterAuhtnticatedToken.builder()
                .xsrfTOken(xsrfToken)
                .jessionId(jessionIdToken).build();
        return crafterAuhtnticatedToken;
    }

    public JSONObject createCMSUser(CrafterUser crafterUser) {
        JSONObject userJson = null;
        generateCrafterToken();
        Response response = restClient.target(crafterSiteURL + createUserUrl)
                .request(MediaType.APPLICATION_JSON)
                .header(Cookie, JSESSIONID + JSESSIONID_VALUE + ";"
                        + XSRF_TOKEN + X_XSRF_TOKEN_VALUE)
                .header(X_XSRF_TOKEN, X_XSRF_TOKEN_VALUE)
                .post(Entity.entity(crafterUser, MediaType.APPLICATION_JSON));
        if (response.getStatus() == 201) {
            String output = response.readEntity(String.class);
            userJson = new JSONObject(output);
            addMemberstoGroup(String.valueOf(userJson.getJSONObject(USER).getLong(ID)),
                    userJson.getJSONObject(USER).getString(USERNAME));
        }
        return userJson;
    }

    public JSONObject addMemberstoGroup(String id, String name) {
        JSONObject memberJson = null;
        generateCrafterToken();
        CrafterGroups groups = new CrafterGroups();
        List<String> ids = new ArrayList<>();
        ids.add(id);
        List<String> users = new ArrayList<>();
        users.add(name);
        groups.setIds(ids);
        groups.setUsernames(users);
        Response response = restClient.target(crafterSiteURL + addMemberToGroupUrl)
                .request(MediaType.APPLICATION_JSON)
                .header(Cookie, JSESSIONID + JSESSIONID_VALUE + ";"
                        + XSRF_TOKEN + X_XSRF_TOKEN_VALUE)
                .header(X_XSRF_TOKEN, X_XSRF_TOKEN_VALUE)
                .post(Entity.entity(groups, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            String output = response.readEntity(String.class);
            memberJson = new JSONObject(output);
        }
        return memberJson;
    }
    public JSONObject deploymentTemplate(DeploymentSite deploymentSite,Long customerId) {
        JSONObject siteJson = null;
        CrafterLogin login = setLoginDetails(String.valueOf(customerId));
        Response response = restClient.target(deploymentUrl)
                .request(MediaType.APPLICATION_JSON)
                .header(FIRSTNAME, login.getFirstname())
                .header(LASTNAME, login.getLastname())
                .header(email, login.getEmail())
                .header(USERNAME, login.getUsername())
                .header(SECURE_KEY, login.getSecureKey())
                .header(GROUPS, login.getGroups())
                .header(X_XSRF_TOKEN, "Test2")
                .header(Cookie,
                        XSRF_TOKEN + "Test2;Path=/studio; HttpOnly")
                .post(Entity.entity(deploymentSite, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 201) {
            String output = response.readEntity(String.class);
        }
        return siteJson;
    }
    public JSONObject startCMSSite(CrafterCreateSite createSite,Long customerId) {
        JSONObject siteJson = null;
        CrafterLogin login = setLoginDetails(String.valueOf(customerId));
        Response response = restClient.target(crafterSiteURL + startSiteUrl)
                .request(MediaType.APPLICATION_JSON)
                .header(FIRSTNAME, login.getFirstname())
                .header(LASTNAME, login.getLastname())
                .header(email, login.getEmail())
                .header(USERNAME, login.getUsername())
                .header(SECURE_KEY, login.getSecureKey())
                .header(GROUPS, login.getGroups())
                .header(X_XSRF_TOKEN, "Test2")
                .header(Cookie,
                        XSRF_TOKEN + "Test2;Path=/studio; HttpOnly")
                .post(Entity.entity(createSite.getSite_id(), MediaType.APPLICATION_JSON));
        System.out.println("........... " + response.getStatus());
        if (response.getStatus() == 201) {
            String output = response.readEntity(String.class);
            siteJson = new JSONObject(output);
        }
        return siteJson;
    }

    public JSONObject createCMSSite(CrafterCreateSite createSite,Long customerId) {
        JSONObject siteJson = null;
        CrafterLogin login = setLoginDetails(String.valueOf(customerId));
        Response response = restClient.target(crafterSiteURL + createSiteUrl)
                .request(MediaType.APPLICATION_JSON)
                .header(FIRSTNAME, login.getFirstname())
                .header(LASTNAME, login.getLastname())
                .header(EMAIL, login.getEmail())
                .header(USERNAME, login.getUsername())
                .header(SECURE_KEY, login.getSecureKey())
                .header(GROUPS, login.getGroups())
                .header(X_XSRF_TOKEN, "Test2")
                .header(Cookie,
                        XSRF_TOKEN + "Test2;Path=/studio; HttpOnly")
                .post(Entity.entity(createSite, MediaType.APPLICATION_JSON));
        if (response.getStatus() == 201) {
            String output = response.readEntity(String.class);
            siteJson = new JSONObject(output);
        }
        return siteJson;
    }

    public JSONObject writeContent(List<String> binifits, String siteName,String email,String logo) throws JAXBException {
        JSONObject writeContentJson = null;
        generateCrafterToken();
        String xmlPayload = xmlConverter.setFeatures(binifits,email,logo);
        Response response = restClient.target(crafterSiteURL + writeContentUrl)
                .queryParam(SITE_ID, siteName)
                .queryParam(PHASE, phase)
                .queryParam(PATH, path)
                .queryParam(FILENAME, fileName)
                .queryParam(USER, user)
                .queryParam(CONTENTTYPE, contentType)
                .queryParam(UNLOCK, unlock)
                .request(MediaType.APPLICATION_XML)
                .header(Cookie, JSESSIONID + JSESSIONID_VALUE + ";"
                        + XSRF_TOKEN + X_XSRF_TOKEN_VALUE)
                .header(X_XSRF_TOKEN, X_XSRF_TOKEN_VALUE)
                .post(Entity.entity(xmlPayload, MediaType.APPLICATION_XML));
        log.info("writeContent..... " + response.getStatus());
        if (response.getStatus() == 200) {
            String output = response.readEntity(String.class);
            writeContentJson = new JSONObject(output);
        }
        return writeContentJson;
    }

    private CrafterAuhtnticatedToken generateCrafterToken() {
        CrafterLogin crafterLogin= setLoginDetails();
        CrafterAuhtnticatedToken token = authenticateCrafter(crafterLogin);
        log.info("Generated Token ::: "+token.getJessionId() + " : Generated XSRF Token : "+token.getXsrfTOken());
        return token;
    }

    private CrafterLogin setLoginDetails(String customerId){
        CrafterLogin login = CrafterLogin.builder().firstname(crafterFirstName)
                .groups(crafterGroups)
                .lastname(crafterLastName)
                .username(crafterUserName)
                .email(email)
                .secureKey(crafterSecureKey)
                .customerId(customerId)
                .build();
        return login;
    }

    private CrafterLogin setLoginDetails(){
        CrafterLogin login = CrafterLogin.builder().firstname(crafterFirstName)
                .groups(crafterGroups)
                .lastname(crafterLastName)
                .username(crafterUserName)
                .secureKey(crafterSecureKey)
                .email(email)
                .build();
        return login;
    }
    public static void main(String[] args) throws JAXBException {
        CrafterConnectors connectors=new CrafterConnectors();
        CrafterUser user=new CrafterUser();
        user.setUsername("sagar");
        user.setPassword("sagar123");
        user.setFirstName("venky");
        user.setLastName("donkey");
        user.setEmail("sagar.nadagoud@wildjasmine.com");
        user.setEnabled(Boolean.FALSE);
        user.setExternallyManaged(Boolean.TRUE);
        List<String> h = new ArrayList<>();
        h.add("events");
    }
}
