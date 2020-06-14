package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public List<Meal> getAll(Integer userId) {
        return repository.getAll(userId);
    }

    public Meal get(int id, int userId) {
        return ValidationUtil.checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Meal create(Meal meal, Integer userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public void update(Meal meal, Integer userId) {
        ValidationUtil.checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public List<Meal> getAllByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getAllByDate(userId, startDate, endDate);
    }
}