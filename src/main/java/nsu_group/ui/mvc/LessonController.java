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

import nsu_group.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static nsu_group.ui.mvc.UserController.user;

@Controller
@RequestMapping("/")
public class LessonController {
	private final LessonRepository lessonRepository;
	private Lesson lesson;
//	private final UserRepository userRepository;

	@Autowired
	public LessonController(LessonRepository lessonRepository) {
//	public LessonController(LessonRepository lessonRepository, UserRepository userRepository) {
		this.lessonRepository = lessonRepository;
//		this.userRepository = userRepository;
	}


	@RequestMapping
	public ModelAndView list() {
		System.out.println(user.getTeacher());
		if(user.getTeacher().equals("0")){
		ArrayList<CardListNotTeacher> arrayCards = this.lessonRepository.groupingByTeacher();
		return new ModelAndView("lessons/coursesnew", "cards", arrayCards);
		}
		else{
			Iterable<Lesson> lessons = this.lessonRepository.findAll();
			return new ModelAndView("lessons/list", "lessons", lessons); //для препода
		}
	}
	@RequestMapping(params = "courses", method = RequestMethod.GET)
	public String courses(@ModelAttribute Lesson lesson) {
		return "lessons/courses";
	}

	@RequestMapping("{id}")
	public ModelAndView view(@PathVariable("id") Lesson lesson) {
		return new ModelAndView("lessons/view", "lesson", lesson);
	}

//	@RequestMapping(params = "login", method = RequestMethod.GET)
//	public String login() {
//		return "users/login";
//	}

//	@RequestMapping(params = "form", method = RequestMethod.GET)
//	public String createForm(@ModelAttribute Lesson lesson, RedirectAttributes redir) {
//		User user = new User(1,"testemail@gmail.com", "ТипоИмя", "PASSWORDDD", "1", "1,2,3,4");
//		redir.addFlashAttribute("user", user);
//		return "lessons/form";
//	}
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model model, RedirectAttributes redirectAttributes) {
		Lesson lesson = new Lesson();
		model.addAttribute("user", user);
		model.addAttribute("lesson", lesson);
		redirectAttributes.addFlashAttribute("model", model);
		return "lessons/form";
	}
	@RequestMapping(value="/form", method = RequestMethod.POST)
	public ModelAndView create(@Valid Lesson lesson, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return new ModelAndView("lessons/form", "formErrors", result.getAllErrors());
		}
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put("lesson", lesson);
		model.put("user", user);
		lesson = this.lessonRepository.testSave(model);
		Iterable<Lesson> lessons = this.lessonRepository.findAll();
		return new ModelAndView("lessons/list", "lessons", lessons);
	}




	@RequestMapping("foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}
}
