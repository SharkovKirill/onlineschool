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

package nsu_group.ui;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;

public class Lesson {

    public ArrayList<String> lis;
    private int id;

    @NotEmpty(message = "Message is required.")
    private String name;

    @NotEmpty(message = "Description is required.")
    private String description;

    @NotEmpty(message = "Video is required.")
    private String video;

    private String course;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return this.video.replace("watch?v=", "embed/");
    }

    public void setVideo(String video) {
        this.video = video;
    }


    public Lesson(){
        this.id = 0;
        this.name = "";
        this.description = "";
        this.video = "";
        this.course = "";
        this.lis = new ArrayList<String>();
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

//    public void updateLis(String course) {
//        lis = new ArrayList<String>(course);
//    }
}

