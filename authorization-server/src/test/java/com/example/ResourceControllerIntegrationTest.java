package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ResourceControllerIntegrationTest {
    private static final String AUTH_HEADER = "Authorization";

    @Autowired
    private MockMvc mockMvc;

    // Testing the '/home' endpoint
    @Test
    void getHome_returns200_OkResponse_ifUnauthenticated() throws Exception {
        this.mockMvc
                .perform(get("/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("You have been got 'Home' page"));
    }

    @Test
    void getHome_returns200_OkResponse_ifAuthorizedAsUser() throws Exception {
        this.mockMvc
                .perform(get("/home").header(AUTH_HEADER, getUserAuthHeader()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("You have been got 'Home' page"));
    }

    @Test
    void getHome_returns200_OkResponse_ifAuthorizedAsManager() throws Exception {
        this.mockMvc
                .perform(get("/home").header(AUTH_HEADER, getManagerAuthHeader()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("You have been got 'Home' page"));
    }

    // Testing the '/user' endpoint
    @Test
    void getUser_returns401_unauthorizedResponse_ifUnauthenticated() throws Exception {
        this.mockMvc
                .perform(get("/user"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(header().string("WWW-Authenticate", "Basic realm=\"Realm\""));
    }

    @Test
    void getUser_returns200_OkResponse_ifAuthorizedAsUser() throws Exception {
        this.mockMvc
                .perform(get("/user").header(AUTH_HEADER, getUserAuthHeader()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Dear 'user', you have been authorized and got 'User' resource"));
    }

    @Test
    void getUser_returns401_ForbiddenResponse_ifAuthorizedAsManager() throws Exception {
        this.mockMvc
                .perform(get("/user").header(AUTH_HEADER, getManagerAuthHeader()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    // Testing the '/manager' endpoint
    @Test
    void getManager_returns401_unauthorizedResponse_ifUnauthenticated() throws Exception {
        this.mockMvc
                .perform(get("/manager"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(header().string("WWW-Authenticate", "Basic realm=\"Realm\""));
    }

    @Test
    void getManager_returns401_ForbiddenResponse_ifAuthorizedAsUser() throws Exception {
        this.mockMvc
                .perform(get("/manager").header(AUTH_HEADER, getUserAuthHeader()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getManager_returns200_OkResponse_ifAuthorizedAsManager() throws Exception {
        this.mockMvc
                .perform(get("/manager").header(AUTH_HEADER, getManagerAuthHeader()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Dear 'manager', you have been authorized and got 'Manager' resource"));
    }

    private String getUserAuthHeader() {
        String credentials = "user:userpass";
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        return "Basic " + encodedCredentials;
    }

    private String getManagerAuthHeader() {
        String credentials = "manager:managerpass";
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        return "Basic " + encodedCredentials;
    }
}
