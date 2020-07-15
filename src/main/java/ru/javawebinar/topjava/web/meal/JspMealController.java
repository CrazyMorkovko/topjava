package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController extends AbstractMealController {
    @GetMapping("/meals")
    public String getMeals(HttpServletRequest request, Model model) {
        String action = request.getParameter("action");
        if (action != null && action.equals("filter")) {
            model.addAttribute("meals", this.getBetween(
                    parseLocalDate(request.getParameter("startDate")),
                    parseLocalTime(request.getParameter("startTime")),
                    parseLocalDate(request.getParameter("endDate")),
                    parseLocalTime(request.getParameter("endTime"))
            ));
        } else {
            model.addAttribute("meals", this.getAll());
        }
        return "meals";
    }

    @GetMapping("/meals/delete")
    public String deleteMeal(HttpServletRequest request) {
        this.delete(getId(request));
        return "redirect:/meals";
    }

    @GetMapping("/meals/create")
    public String createMeal(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        model.addAttribute("isCreate", true);
        return "mealForm";
    }

    @GetMapping("/meals/update")
    public String updateMeal(HttpServletRequest request, Model model) {
        model.addAttribute("meal", this.get(getId(request)));
        model.addAttribute("isCreate", false);
        return "mealForm";
    }

    @PostMapping("/meals")
    public String updateOrCreateMeal(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );

        if (StringUtils.isEmpty(request.getParameter("id"))) {
            this.create(meal);
        } else {
            this.update(meal, getId(request));
        }

        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        return Integer.parseInt(Objects.requireNonNull(request.getParameter("id")));
    }
}
