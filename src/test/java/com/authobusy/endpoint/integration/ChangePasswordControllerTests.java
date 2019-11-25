package com.authobusy.endpoint.integration;

import com.authobusy.endpoint.controller.access.request.LoginRequest;
import com.authobusy.endpoint.controller.password.request.PasswordChangeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChangePasswordControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private String jwt;

    private static final String WRONG_PASS  = "111222";
    private static final String EMPTY_PASS  = "";
    private static final String OLD_PASS  = "123123";
    private static final String INVALID_NEW_PASS  = "000";
    private static final String NEW_PASS  = "is456better";

    @Test
    public void rejectAnonymousPassword() throws Exception {
        this.mockMvc.perform(get("/passchange"))
            .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void rejectEmptyOldPasswordParam() throws Exception {

        PasswordChangeRequest req = new PasswordChangeRequest(EMPTY_PASS, NEW_PASS);

        this.performRequest(req)
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void rejectEmptyNewPasswordParam() throws Exception {

        PasswordChangeRequest req = new PasswordChangeRequest(OLD_PASS, EMPTY_PASS);

        this.performRequest(req)
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void rejectWrongOldPasswordParam() throws Exception {

        PasswordChangeRequest req = new PasswordChangeRequest(WRONG_PASS, NEW_PASS);

        this.performRequest(req)
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void rejectInvalidNewPasswordParam() throws Exception {

        PasswordChangeRequest req = new PasswordChangeRequest(WRONG_PASS, INVALID_NEW_PASS);

        this.performRequest(req)
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void passwordChangeSuccess() throws Exception {

        PasswordChangeRequest req = new PasswordChangeRequest(OLD_PASS, NEW_PASS);

        this.performRequest(req)
            .andExpect(status().isOk());
    }



    private ResultActions performRequest(PasswordChangeRequest req) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        this.loginAndGetJwt();

        return this.mockMvc.perform(
                post("/passchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.jwt)
                        .content(mapper.writeValueAsString(req))
            )
            .andDo(print());
    }

    private String loginAndGetJwt() throws Exception {

        if (this.jwt != null) {
            return this.jwt;
        }

        LoginRequest mockReq = new LoginRequest("pepito@test.com", OLD_PASS);
        ObjectMapper mapper = new ObjectMapper();

        MvcResult result = this.mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockReq))
        )
                .andReturn();

        this.jwt = result.getResponse().getHeader("Token");

        return this.jwt;
    }

}
