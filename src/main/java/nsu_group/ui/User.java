package nsu_group.ui;

import javax.validation.constraints.NotNull;

public class User {
    private int id;
    private String email;
    private String name;
    private String password;
    private String teacher;
    private String userlessons;

    public User(int id, String email, String name, String password, String teacher, String userlessons){
        this.id = 2323;

        this.email = email;
        this.name = name;
        this.password = password;
        if(teacher==null){
            this.teacher = "0";
        }else {
            this.teacher = teacher;
        }
        this.userlessons = userlessons;

    }
    public User() {
        this.id = 0;
        this.email = "";
        this.name = "";
        this.password = "";
        this.teacher = "";
        this.userlessons = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getUserlessons() {
        return userlessons;
    }

    public void setUserlessons(String userlessons) {
        this.userlessons = userlessons;
    }
}
