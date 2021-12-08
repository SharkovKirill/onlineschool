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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import nsu_group.ui.Lesson;
import nsu_group.ui.LessonRepository;

@Controller
@RequestMapping("/")
public class LessonController {
	private final LessonRepository lessonRepository;

	@Autowired
	public LessonController(LessonRepository lessonRepository) {
		this.lessonRepository = lessonRepository;
	}

	@RequestMapping
	public ModelAndView list() {
		Iterable<Lesson> lessons = this.lessonRepository.findAll();
		return new ModelAndView("lessons/list", "lessons", lessons);
	}

	@RequestMapping("{id}")
	public ModelAndView view(@PathVariable("id") Lesson lesson) {
		return new ModelAndView("lessons/view", "lesson", lesson);
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(@ModelAttribute Lesson lesson) {
		return "lessons/form";
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView create(@Valid Lesson lesson, BindingResult result,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return new ModelAndView("lessons/form", "formErrors", result.getAllErrors());
		}
		lesson = this.lessonRepository.save(lesson);
		Iterable<Lesson> lessons = this.lessonRepository.findAll();
		return new ModelAndView("lessons/list", "lessons", lessons);
//		System.out.println(lesson.getVideo()+"                  jwkef");
//		redirect.addFlashAttribute("globalLesson", "Successfully created a new lesson");
//		return new ModelAndView("redirect:/{lesson.id}", "lesson.id", lesson.getId());
	}

	@RequestMapping("foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}
}
