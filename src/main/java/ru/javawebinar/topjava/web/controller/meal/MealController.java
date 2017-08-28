package ru.javawebinar.topjava.web.controller.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class MealController {

    private final MealRestController mealRestController;

    @Autowired
    private MealController(MealRestController mealRestController) {
        this.mealRestController = mealRestController;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    private ModelAndView getAll() {
        ModelAndView modelAndView=new ModelAndView("/meals");
        modelAndView.addObject("meals", mealRestController.getAll());
        return modelAndView;
    }

    @RequestMapping(value="/delete", params = {"id"}, method = RequestMethod.GET)
    private String delete(HttpServletRequest request, @RequestParam(value = "id") int id) {
        mealRestController.delete(id);
        return "redirect:/meals";
    }

    @RequestMapping(value="/update", method = RequestMethod.GET)
    private ModelAndView beforeUpdate(@RequestParam(value = "id") int id) {
        final Meal meal =  mealRestController.get(id);
        ModelAndView modelAndView=new ModelAndView("/mealForm");
        modelAndView.addObject("meal", meal);
        modelAndView.addObject("updated", 1);
        return modelAndView;
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    private String commitUpdate(@RequestParam(value = "id") int id,
                          @RequestParam(value = "dateTime") String datetime,
                          @RequestParam(value = "calories") int calories,
                          @RequestParam(value = "description") String description) { //@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
        Meal meal = new Meal( LocalDateTime.parse(datetime), description, calories);
        mealRestController.update(meal, id);
        return "redirect:/meals";
    }

    @RequestMapping(value="/create", method = RequestMethod.GET)
    private ModelAndView beforeCreate() {
        final Meal meal =  new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        ModelAndView modelAndView=new ModelAndView("/mealForm");
        modelAndView.addObject("meal", meal);
        modelAndView.addObject("updated", 0);
        return modelAndView;
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    private String commitCreate(@RequestParam(value = "dateTime") String datetime,
                                @RequestParam(value = "calories") int calories,
                                @RequestParam(value = "description") String description) { //@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
        Meal meal = new Meal( LocalDateTime.parse(datetime), description, calories);
        mealRestController.create(meal);
        return "redirect:/meals";
    }

    @RequestMapping(value="/filter", method = RequestMethod.POST)
    private ModelAndView  filter(@RequestParam(value = "startDate") String startDate,
                                 @RequestParam(value = "endDate") String endDate,
                                 @RequestParam(value = "startTime") String startTime,
                                 @RequestParam(value = "endTime") String endTime) {
        ModelAndView modelAndView=new ModelAndView("/meals");
        modelAndView.addObject("meals", mealRestController.getBetween(parseLocalDate(startDate), parseLocalTime(startTime),
                        parseLocalDate(endDate), parseLocalTime(endTime)));
        return modelAndView;
    }
}
