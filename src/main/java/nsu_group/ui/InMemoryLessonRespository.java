/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nsu_group.ui;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Dave Syer
 */
public class InMemoryLessonRespository implements LessonRepository {

	private static AtomicLong counter = new AtomicLong();

	private final ConcurrentMap<Long, Lesson> lessons = new ConcurrentHashMap<Long, Lesson>();

	@Override
	public Iterable<Lesson> findAll() {
		return this.lessons.values();
	}

	@Override
	public Lesson save(Lesson lesson) {
		Long id = lesson.getId();
		if (id == null) {
			id = counter.incrementAndGet();
			lesson.setId(id);
		}
		this.lessons.put(id, lesson);
		return lesson;
	}

	@Override
	public Lesson findLesson(Long id) {
		return this.lessons.get(id);
	}
}
