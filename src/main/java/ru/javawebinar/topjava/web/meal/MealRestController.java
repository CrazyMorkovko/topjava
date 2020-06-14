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
    protected final Logger log = LoggerFactory.getLogger(getClass());
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

    public List<MealTo> getAll(LocalDate startDate, LocalDate endDate) {
        log.info("getAllWithFilterByDate");
        return MealsUtil.getFilteredTos(
                service.getAll(SecurityUtil.authUserId(), startDate, endDate),
                SecurityUtil.authUserCaloriesPerDay(),
                LocalTime.MIN,
                LocalTime.MAX
        );
    }

    public List<MealTo> getAll(LocalTime startTime, LocalTime endTime) {
        log.info("getAllWithFilterByDate");
        return MealsUtil.getFilteredTos(
                service.getAll(SecurityUtil.authUserId(), startTime, endTime),
                SecurityUtil.authUserCaloriesPerDay(),
                LocalTime.MIN,
                LocalTime.MAX
        );
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        ValidationUtil.checkNew(meal);
        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        ValidationUtil.assureIdConsistent(meal, id);
        service.update(meal);
    }
}