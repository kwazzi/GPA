package sample;

import sample.Course;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

public class GradeTracker {
    ArrayList<Course> listOfCourses;
    ArrayList<Course> storedCourses;
    double gpa;
    File coursesData;
    Scanner input;
    Scanner coursesInput;
    String commas = (",");

    public GradeTracker() throws IOException {
        coursesData = new File("courses.txt");
        coursesInput = new Scanner(coursesData);
        input = new Scanner(System.in);
        choices();

    }

    public void choices() throws IOException {
        boolean yes;
        System.out.println("do you already have stored data?");
        String yesOrNo = input.nextLine();
        /////////// if the user already has data /////////////
        if (yesOrNo.equals("yes")) {
            readCourses();
            calculateGPA(storedCourses);
            System.out.println("Your stored gpa is: " + getGPA());
            listAllCourses();
            findCourse();
            storeCourses(storedCourses);
        }
        //////// if they don't have data or want to enter new ones //////////////
        else if (yesOrNo.equalsIgnoreCase("no")) {
            getInput();
            calculateGPA(listOfCourses);
            System.out.println("Your gpa is: " + getGPA());
            System.out.println("do you want to save this data?");
            String saveOrNo = input.nextLine();
            ///////////// if they want to save //////////////
            if (saveOrNo.equals("yes")) {
                storeCourses(listOfCourses);
                readCourses();
                listAllCourses();
                System.out.println("do you want to change something?");
                String change = input.nextLine();
                if (change.equals("yes")) {
                    listAllCourses();
                    findCourse();
                }
                else {
                    System.out.println("all done");
                }
            }
        }
    }

    public void getInput() {
        listOfCourses = new ArrayList<Course>();
        Scanner input = new Scanner(System.in);

        System.out.println("How many courses have you taken?");
        int numOfCourses = input.nextInt();
        input.nextLine();
        System.out.println("Enter each of your courses in the following format: ");
        System.out.println("CourseName, Course Grade, Course Weight");
        System.out.println("Type 'stop' to end the program and get your gpa");
        smallInput(numOfCourses, listOfCourses);

    }

    public void smallInput(int x, ArrayList<Course> whichData) {
        for (int i = 0; i < x; i++) {
            String inputCourse = input.nextLine();
            String inputArray[] = new String[x];

            //////separate the inputs///////////
            inputArray = inputCourse.split(commas);
            if (inputArray.length != 3) {
                System.out.println("bro type it in right i'm tired of you i swearrrrr");
            }
            String course = inputArray[0];
            double grade = Double.parseDouble(inputArray[1]);
            double weight = Double.parseDouble(inputArray[2]);
            /////////////add new course object//////////////
            Course temp = new Course(course, grade, weight);
            whichData.add(temp);
        }
    }

    public void calculateGPA(ArrayList<Course> whichData) {
        double grade = 0;
        double x = 0;
        Course thisCourse = new Course(null, 0, 0);
        for (int i = 0; i < whichData.size(); i++) {
            thisCourse = whichData.get(i);
            grade = (thisCourse.courseGrade * thisCourse.courseWeight) / 100;
            x = grade + x;
        }
        gpa = x / whichData.size();
    }

    public double getGPA() {
        return gpa;
    }

    public void setGpa() {
        this.gpa = gpa;
    }

    /////////////stuff for file
    public void storeCourses(ArrayList<Course> whichData) throws IOException {
        FileWriter fileWriter = new FileWriter(coursesData);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        Course temp = new Course(null, 0, 0);
        for (int i = 0; i < whichData.size(); i++) {
            temp = whichData.get(i);
            printWriter.println(temp.courseName + "," + temp.courseGrade + "," + temp.courseWeight);
        }
        printWriter.close();
    }

    public void readCourses() {
        storedCourses = new ArrayList<Course>();
        String commas = (",");
        String courseName = null;
        double courseGrade = 0;
        double courseWeight = 0;

        while (coursesInput.hasNext()) {
            String course = coursesInput.nextLine();
            String[] courses = course.split(commas);
            //////courseNames///////
            for (int i = 0; i < courses.length; i = i + 3) {
                courseName = courses[i];
            }
            /////courseGrades//////////
            for (int j = 1; j < courses.length; j = j + 3) {
                courseGrade = Double.parseDouble(courses[j]);
            }
            //////courseWeights//////////
            for (int k = 2; k < courses.length; k = k + 3) {
                courseWeight = Double.parseDouble(courses[k]);
            }
            Course temp = new Course(courseName, courseGrade, courseWeight);
            storedCourses.add(temp);
        }
    }

    public void findCourse() throws IOException {
        Course temp = new Course(null, 0, 0);
        Scanner input = new Scanner(System.in);
        System.out.println("What do you wanna change? (delete, add, change value)");
        String command = input.nextLine();
        if (command.equals("delete") || command.equals("change value")) {
            System.out.println("Which course would you like to change?");
            int x = input.nextInt();
            String trash = input.nextLine();
            temp = storedCourses.get(x);
            //////////////////deleting a course//////////////
            if (command.equalsIgnoreCase("delete")) {
                storedCourses.remove(temp);
                storeCourses(storedCourses);
            }

            ///////////////////changing values////////////////////
            else if (command.equals("change value")) {
                System.out.println("Which value would you like to change? (name, grade, weight)");
                String change = input.nextLine();
                if (change.equalsIgnoreCase("name")) {
                    System.out.println("type in your new value");
                    String newName = input.nextLine();
                    temp.courseName = newName;
                } else if (change.equals("grade")) {
                    System.out.println("type in your new value");
                    double newGrade = input.nextDouble();
                    temp.courseGrade = newGrade;
                } else if (change.equals("weight")) {
                    System.out.println("type in your new value");
                    double newWeight = input.nextDouble();
                    temp.courseWeight = newWeight;
                } else {
                    System.out.println("bro idk what u trynna do");
                }
            }
        } else if (command.equalsIgnoreCase("add")) {
            System.out.println("Ex: Course Name, Course Grade, Course Weight");
            smallInput(1, storedCourses);
        }
        listAllCourses();
        calculateGPA(storedCourses);
        System.out.println("Your new gpa is: " + getGPA());
    }

    public void listAllCourses() {
        Course temp = new Course(null, 0, 0);
        for (int i = 0; i < storedCourses.size(); i++) {
            temp = storedCourses.get(i);
            System.out.println(i + " | " + temp.courseName + " | " + temp.courseGrade + " | " + temp.courseWeight);
        }
    }
}

