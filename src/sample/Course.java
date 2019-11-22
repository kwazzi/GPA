package sample;

public class Course {
    String courseName; double courseGrade; double courseWeight;

    public Course(String name, double grade, double weight ){
        courseName = name;
        courseGrade = grade;
        courseWeight = weight;
        getName();
        getCourseGrade();
        getWeight();
    }

    private String getName(){
        return courseName;
    }

    private void setName(){

    }

    private double getCourseGrade(){
        return courseGrade;
    }

    private void setCourseGrade(){

    }

    private double getWeight(){
        return courseWeight;
    }

    private void setWeight(){

    }

}
