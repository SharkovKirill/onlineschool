/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package nsu_group.ui.mvc;

import javax.validation.Valid;

import nsu_group.ui.Lesson;
import nsu_group.ui.LessonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import nsu_group.ui.User;
import nsu_group.ui.UserRepository;
import java.util.ArrayList;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;


    @Autowired
    public UserController(UserRepository userRepository, LessonRepository lessonRepository) {
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
    }

//    @RequestMapping(params = "login")
//    public ModelAndView list() {
//        System.out.println("kjwenwnfwf");
//        ArrayList<User> users = this.userRepository.findAll();
////		return new ModelAndView("users/list", "users", users);
//        return new ModelAndView("users/login", "users", users);
//    }

    @RequestMapping(params = "reg", method = RequestMethod.GET)
    public String reg(@ModelAttribute User user) {
        return "users/reg";
    }

    @RequestMapping(value="/reg", method = RequestMethod.POST)
    public ModelAndView createUser(@Valid User user, BindingResult result,
                               RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("users/reg", "formErrors", result.getAllErrors());
        }
        user = this.userRepository.save(user);
        Iterable<Lesson> lessons = this.lessonRepository.findAll();
        return new ModelAndView("lessons/list", "lessons", lessons);
//		System.out.println(lesson.getVideo()+"                  jwkef");
//		redirect.addFlashAttribute("globalLesson", "Successfully created a new lesson");
//		return new ModelAndView("redirect:/{lesson.id}", "lesson.id", lesson.getId());
    }

    @RequestMapping(params = "login", method = RequestMethod.GET)
    public String login(@ModelAttribute User user) {
        return "users/login";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ModelAndView successfulLogin(@Valid User user, BindingResult result,
                                   RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("users/login", "formErrors", result.getAllErrors());
        }
        user = this.userRepository.save(user);
        Iterable<Lesson> lessons = this.lessonRepository.findAll();
        return new ModelAndView("lessons/list", "lessons", lessons);
    }
}
