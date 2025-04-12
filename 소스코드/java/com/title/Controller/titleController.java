package com.title.Controller;

import com.title.Service.titleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.title.DTO.userDTO;

@Controller
public class titleController {

    @Autowired
    titleService titleService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView title(@RequestParam(required = false) String userId){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("title");

        modelAndView.addObject("user",titleService.selectUser(userId));

        modelAndView.addObject("movies",titleService.selectMovies(userId));

        userDTO user = titleService.selectUser(userId);
        if (user != null) {
            String occupation = user.getOccupation();
            modelAndView.addObject("occupationMovies", titleService.selectOccupationMovies(occupation));

            int ageGroup = user.getAge();
            modelAndView.addObject("ageGroupMovies", titleService.selectAgeGroupMovies(ageGroup));

            String gender = user.getGender();
            modelAndView.addObject("genderMovies", titleService.selectGenderMovies(gender));

            modelAndView.addObject("recommendedMovies", titleService.selectRecommendedMovies(userId));

            modelAndView.addObject("recommendedMovies_Cosine", titleService.selectRecommendedMovies_Cosine(userId));
        }



        return modelAndView;
    }
}
