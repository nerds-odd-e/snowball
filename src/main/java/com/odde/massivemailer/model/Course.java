package com.odde.massivemailer.model;

import com.odde.massivemailer.model.validator.UniquenessValidator;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.*;

/**
 * Created by csd on 30/5/17.
 */
@Table("courses")
public class Course extends Model {

    private String coursename;
    private String duration;
    private String location;
    private Date startdate;
    private String address;
    private String coursedetails;
    private String instructor;


    public Course() { }

    public Course(String coursename, String duration, String location, Date startdate, String address, String coursedetails, String instructor) {
        setCoursename(coursename);
        setDuration(duration);
        setLocation(location);
        setStartdate(startdate);
        setAddress(address);
        setCoursedetails(coursedetails);
        setInstructor(instructor);
    }

    public String getCoursename() {
        return coursename;
    }

    public String getDuration() {
        return duration;
    }

    public String getLocation() {
        return location;
    }

    public Date getStartdate() {
        return startdate;
    }

    public String getAddress() {
        return address;
    }

    public String getCoursedetails() {
        return coursedetails;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCoursedetails(String coursedetails) {
        this.coursedetails = coursedetails;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
