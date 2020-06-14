package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getFilteredTos(
                service.getAll(SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay(),
                LocalTime.MIN,
                LocalTime.MAX
        );
    }

    public List<MealTo> getAllByDateTime(String startDate, String endDate, String startTime, String endTime) {
        log.info("getAllWithFilterByDateTime");

        LocalDate startD = LocalDate.MIN;
        try {
            startD = LocalDate.parse(startDate);
        } catch (Exception ignored) {

        }

        LocalDate endD = LocalDate.MAX;
        try {
            endD = LocalDate.parse(endDate);
        } catch (Exception ignored) {

        }

        LocalTime startT = LocalTime.MIN;
        try {
            startT = LocalTime.parse(startTime);
        } catch (Exception ignored) {

        }

        LocalTime endT = LocalTime.MAX;
        try {
            endT = LocalTime.parse(endTime);
        } catch (Exception ignored) {

        }

        return MealsUtil.getFilteredTos(
                service.getAllByDate(SecurityUtil.authUserId(), startD, endD),
                SecurityUtil.authUserCaloriesPerDay(),
                startT,
                endT
        );
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        ValidationUtil.checkNew(meal);
        log.info("create {}", meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        ValidationUtil.assureIdConsistent(meal, id);
        log.info("update {} with id={}", meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }
}