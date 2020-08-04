package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class AdminUIControllerTest extends AbstractControllerTest {
    private static final String REST_URL = "/admin/users/";

    @Autowired
    private UserService userService;

    @Test
    void changeStatus() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .param("enabled", "false"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertFalse(userService.get(USER_ID).isEnabled());
    }
}
