package com.odde.massivemailer.model;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;


import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Table("courses")
public class Course extends Model {


    public Course() {

    }

    public Course(String title, String content, String location) {
        setCourseName(title);
        setCourseDetails(content);
        setLocation(location);
    }

    public Course(Map<String, String> map) {

        for (Map.Entry param :map.entrySet()) {
            set(param.getKey(), param.getValue());
        }
    }

    public void setCourseName(String courseName) {
        set("coursename", courseName);
    }

    public void setInstructor(String instructor) {
        set("instructor", instructor);
    }

    public void setLocation(String location) {
        set("location", location);
    }

    public void setCourseDetails(String details) {
        set("coursedetails", details);
    }

    public String getCoursename() {
        return getAttribute("coursename");
    }

    public String getDuration() {
        return getAttribute("duration");
    }

    public String getLocation() {
        return getAttribute("location");
    }

    public String getStartdate() {
        return getAttribute("startdate");
    }

    public String getAddress() {
        return getAttribute("address");
    }

    public String getCoursedetails() {
        return getAttribute("coursedetails");
    }

    public String getInstructor() {
        return getAttribute("instructor");
    }

    public String getAttribute(String name) {
        return (String) get(name);
    }

     public static Course getCourseByName(String name) {
        LazyList<Course> list = where("coursename = ?", name);
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

    public static Course getCourseById(Integer id) {
        LazyList<Course> list = where("id = ?", id.intValue());
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

    public List<Participant> participants() {
        return Participant.where("course_id = ?", getId().toString());
    }
}
