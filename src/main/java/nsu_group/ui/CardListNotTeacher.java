package nsu_group.ui;

public class CardListNotTeacher {
    public String coursename;
    public String courseteacher;
    public String coursequantity;

    public CardListNotTeacher(){
        this.coursename = "";
        this.courseteacher = "";
        this.coursequantity = "";
    }

    public CardListNotTeacher(String coursename, String courseteacher, String coursequantity){
        this.coursename = coursename;
        this.courseteacher = courseteacher;
        this.coursequantity = coursequantity;
    }
}
