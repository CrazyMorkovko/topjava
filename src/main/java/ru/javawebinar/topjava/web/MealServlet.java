package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealInMemoryDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private MealDao mealDao;

    @Override
    public void init() {
        mealDao = new MealInMemoryDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        String template = "/meals.jsp";
        switch (action.toLowerCase()) {
            case "create": {
                template = "/meals/manage.jsp";
                request.setAttribute("meal", new Meal(LocalDateTime.now(), "", 0));
                break;
            }
            case "edit": {
                template = "/meals/manage.jsp";
                request.setAttribute("meal", mealDao.getById(Integer.parseInt(request.getParameter("id"))));
                break;
            }
            case "delete": {
                mealDao.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("meals");
                return;
            }
            default: {
                request.setAttribute(
                        "meals",
                        MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000)
                );
                break;
            }
        }

        log.debug("redirect to meals");
        request.setAttribute("dateTimeFormatter", formatter);
        request.getRequestDispatcher(template).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );
        if (id == null) {
            mealDao.add(meal);
        } else {
            mealDao.update(Integer.parseInt(id), meal);
        }

        response.sendRedirect("meals");
    }
}
