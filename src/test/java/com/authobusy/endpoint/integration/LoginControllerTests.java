package com.authobusy.endpoint.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.authobusy.endpoint.controller.access.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginGetUnavailable() throws Exception {
        this.mockMvc.perform(get("/login")).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void rejectsEmptyParam() throws Exception {
    }

    @Test
    public void rejectsUserNotFound() throws Exception {
        LoginRequest mockReq = new LoginRequest("me@notfound.es", "123123");
        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockReq))
        )
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void rejectsInvalidPassword() throws Exception {
        LoginRequest mockReq = new LoginRequest("pepito@test.com", "wrong");
        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockReq))
        )
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void userLoginSuccessful() throws Exception {
        LoginRequest mockReq = new LoginRequest("pepito@test.com", "123123");
        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockReq))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists("Token"))

        ;
    }


}
