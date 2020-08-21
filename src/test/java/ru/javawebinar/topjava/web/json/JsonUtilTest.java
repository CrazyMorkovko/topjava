package ru.javawebinar.topjava.web.json;

import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.javawebinar.topjava.MealTestData.*;

class JsonUtilTest {
    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(ADMIN_MEAL1);

        System.out.println(json);
        MEAL_MATCHER.assertMatch(JsonUtil.readValue(json, Meal.class), ADMIN_MEAL1);
    }

    @Test
    void readWriteValues() {
        String json = JsonUtil.writeValue(MEALS);

        System.out.println(json);
        MEAL_MATCHER.assertMatch(JsonUtil.readValues(json, Meal.class), MEALS);
    }

    @Test
    void writeOnlyAccess() {
        String json = JsonUtil.writeValue(UserTestData.USER);

        System.out.println(json);
        assertThat(json, not(containsString("password")));

        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.USER, "newPass");

        System.out.println(jsonWithPass);
        assertEquals(JsonUtil.readValue(jsonWithPass, User.class).getPassword(), "newPass");
    }
}