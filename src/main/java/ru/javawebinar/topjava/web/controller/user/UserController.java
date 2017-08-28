package ru.javawebinar.topjava.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private AdminRestController adminController;

    @RequestMapping(value="/", method = RequestMethod.GET)
    private ModelAndView getAll() {
        ModelAndView model=new ModelAndView("/users");
        model.addObject("users", adminController.getAll());
        return model;
    }
}
