package ru.javawebinar.topjava.repository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class JdbcUtil {
    public static <T> boolean isValid(T entity) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(entity);

        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }

        return true;
    }
}
