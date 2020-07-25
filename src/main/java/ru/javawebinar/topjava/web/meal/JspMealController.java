package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {
    @GetMapping
    public String getAll(HttpServletRequest request, Model model) {
        String action = request.getParameter("action");

        if (action != null && action.equals("filter")) {
            model.addAttribute("meals", getBetween(
                    parseLocalDate(request.getParameter("startDate")),
                    parseLocalTime(request.getParameter("startTime")),
                    parseLocalDate(request.getParameter("endDate")),
                    parseLocalTime(request.getParameter("endTime"))
            ));
        } else {
            model.addAttribute("meals", getAll());
        }

        return "meals";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        delete(getId(request));
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("meal", get(getId(request)));
        return "mealForm";
    }

    @PostMapping
    public String updateOrCreate(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );

        if (StringUtils.isEmpty(request.getParameter("id"))) {
            create(meal);
        } else {
            update(meal, getId(request));
        }

        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        return Integer.parseInt(Objects.requireNonNull(request.getParameter("id")));
    }
}
