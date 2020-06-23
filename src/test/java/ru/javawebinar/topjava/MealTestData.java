package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int NOT_FOUND = 10;
    public static final int USER_ID = AbstractBaseEntity.START_SEQ;
    public static final int ANOTHER_USER_ID = AbstractBaseEntity.START_SEQ + 1;
    public static final int MEAL_ID = AbstractBaseEntity.START_SEQ + 2;
    public static final Meal MEAL_1_1 = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_2_1 = new Meal(MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_3_1 = new Meal(MEAL_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_4_1 = new Meal(MEAL_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL_5_1 = new Meal(MEAL_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_6_1 = new Meal(MEAL_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_7_1 = new Meal(MEAL_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal MEAL_1_2 = new Meal(MEAL_ID + 7, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 600);
    public static final Meal MEAL_2_2 = new Meal(MEAL_ID + 8, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 900);
    public static final Meal MEAL_3_2 = new Meal(MEAL_ID + 9, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 300);
    public static final Meal MEAL_4_2 = new Meal(MEAL_ID + 10, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_5_2 = new Meal(MEAL_ID + 11, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_6_2 = new Meal(MEAL_ID + 12, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.FEBRUARY, 1, 11, 0), "test", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_1_1.getId(), MEAL_1_1.getDateTime(), MEAL_1_1.getDescription(), MEAL_1_1.getCalories());
        updated.setDateTime(LocalDateTime.of(2020, Month.FEBRUARY, 2, 11, 0));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }
}