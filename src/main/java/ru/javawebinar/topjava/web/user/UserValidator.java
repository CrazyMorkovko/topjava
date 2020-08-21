package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@Component
public class UserValidator implements Validator {
    final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserTo user = (UserTo) target;

        if (user.getEmail() != null) {
            try {
                userService.getByEmail(user.getEmail());
                errors.rejectValue("email", "user.wrongEmail", "User with this email already exists");
            } catch (NotFoundException ignored) {

            }
        }
    }
}