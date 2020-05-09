package com.eMurasu.controller;

import com.eMurasu.Objects.LoginResponse;
import com.eMurasu.Objects.ReturnMessage;
import com.eMurasu.enums.UserState;
import com.eMurasu.model.UserCredential;
import com.eMurasu.repo.UserCredentialRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@RestController
public class UserCredentialController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserCredentialRepo userCredentialRepo;
    @Value("${security.jwt.client-id}")
    private String clientId;

    @Value("${security.jwt.client-secret}")
    private String clientSecret;

    @Value("${security.jwt.grant-type}")
    private String grantType;

    @PostMapping(path = "/login")
    public LoginResponse login(@RequestParam String username, @RequestParam String password) {
        try {
            if (userCredentialRepo.existsById(username)) {
                UserCredential loginUser = userCredentialRepo.findByUsername(username);
                if (loginUser.getStatus().equals(UserState.ACTIVE)) {
                    //get token
                    HttpPost getToken = new HttpPost("http://localhost:8082/oauth/token");
                    //Header for request
                    getToken.addHeader("content-type", "application/x-www-form-urlencoded");

                    //Parameters for request
                    List<NameValuePair> urlParameters = new ArrayList<>();
                    urlParameters.add(new BasicNameValuePair("username", username));
                    urlParameters.add(new BasicNameValuePair("password", password));
                    urlParameters.add(new BasicNameValuePair("grant_type", grantType));
                    try {
                        getToken.setEntity(new UrlEncodedFormEntity(urlParameters));
                    } catch (UnsupportedEncodingException e) {
                        return new LoginResponse("Something wrong in the parameter set-up of token request body");
                    }
                    //BasicAuth set up
                    CredentialsProvider provider = new BasicCredentialsProvider();
                    provider.setCredentials(
                            AuthScope.ANY,
                            new UsernamePasswordCredentials(clientId, clientSecret)
                    );
                    try {
                        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
                        CloseableHttpResponse response = httpClient.execute(getToken);
                        //convert response entity as json
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode tokenDetails = tokenDetails = mapper.readTree(EntityUtils.toString(response.getEntity()));
                        assertNotNull(tokenDetails);

                        if (200 == response.getStatusLine().getStatusCode()) {
                            LoginResponse loginResponse = new LoginResponse(response.getStatusLine().getStatusCode(),
                                    response.getStatusLine().toString(),
                                    tokenDetails.get("access_token").asText(),
                                    tokenDetails.get("token_type").asText(),
                                    tokenDetails.get("refresh_token").asText(),
                                    tokenDetails.get("expires_in").asInt(),
                                    tokenDetails.get("scope").asText(),
                                    tokenDetails.get("jti").asText());
                            response.close();
                            httpClient.close();
                            return loginResponse;
                        } else {
                            LoginResponse loginResponse = new LoginResponse(response.getStatusLine().getStatusCode(),
                                    response.getStatusLine().toString(),
                                    tokenDetails.get("error").asText(),
                                    tokenDetails.get("error_description").asText());
                            response.close();
                            httpClient.close();
                            return loginResponse;
                        }
                    } catch (IOException e) {
                        return new LoginResponse(e.getMessage());
                    }
                } else if (loginUser.getStatus().equals(UserState.NEW)) {
                    return new LoginResponse("Please change default password to a new password");
                } else {
                    return new LoginResponse("User is not in active");
                }
            } else {
                return new LoginResponse("User not exists");
            }
        } catch (Exception e) {
            //need to write code for get error message for clientId, ClientSecret related errors.
            return new LoginResponse(e.getMessage());
        }
    }


    @PostMapping(path = "/changePassword", produces = "application/json")
    private ReturnMessage changePassword(@RequestParam String username, @RequestParam String password,
                                         @RequestParam String newPassword) {
        try {
            if (userCredentialRepo.existsById(username)) {
                UserCredential user = userCredentialRepo.findByUsername(username);
                if (new BCryptPasswordEncoder().matches(password, user.getPassword())) {
                    user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
                    user.setStatus(UserState.ACTIVE);
                    userCredentialRepo.save(user);
                    return new ReturnMessage("You have changed your password successfully");
                } else {
                    return new ReturnMessage("Password doesn't match");
                }
            } else {
                return new ReturnMessage("User not exists!");
            }
        } catch (Exception e) {
            return new ReturnMessage(e.getMessage());
        }
    }
}
