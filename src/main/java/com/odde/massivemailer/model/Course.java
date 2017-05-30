package com.odde.massivemailer.model;

import com.odde.massivemailer.model.validator.UniquenessValidator;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public Course(CourseBuilder courseBuilder) {

        setCoursename(courseBuilder.coursename);
        setDuration(courseBuilder.duration);
        setLocation(courseBuilder.location);
        setStartdate(courseBuilder.startdate);
        setAddress(courseBuilder.address);
        setCoursedetails(courseBuilder.coursedetails);
        setInstructor(courseBuilder.instructor);

    }

    /*public Course(String coursename, String duration, String location, Date startdate, String address, String coursedetails, String instructor) {
        setCoursename(coursename);
        setDuration(duration);
        setLocation(location);
        setStartdate(startdate);
        setAddress(address);
        setCoursedetails(coursedetails);
        setInstructor(instructor);
    }*/

    public String getCoursename() {
        return getAttribute("coursename");
    }

    public String getDuration() {
        return getAttribute("duration");
    }

    public String getLocation() {
        return getAttribute("location");
    }

    public Date getStartdate() throws ParseException {
        System.out.println("++++++++++++++++++++++++++++" + getAttribute("startdate"));
        return new SimpleDateFormat("yyyy-MM-dd").parse(getAttribute("startdate"));
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

    public void setCoursename(String coursename) {
        //this.coursename = coursename;
        set("coursename", coursename);
    }

    public void setDuration(String duration) {
        //this.duration = duration;
        set("duration", duration);
    }

    public void setLocation(String location) {
        //this.location = location;
        set("location", location);
    }

    public void setStartdate(Date startdate) {
        //this.startdate = startdate;
        set("startdate", startdate);
    }

    public void setAddress(String address) {
        //this.address = address;
        set("address", address);
    }

    public void setCoursedetails(String coursedetails) {
        //this.coursedetails = coursedetails;
        set("coursedetails", coursedetails);
    }

    public void setInstructor(String instructor) {
        //this.instructor = instructor;
        set("instructor", instructor);
    }

    public String getAttribute(String name) {
        return (String) get(name);
    }

    public static class CourseBuilder {

        private String coursename;
        private String duration;
        private String location;
        private Date startdate;
        private String address;
        private String coursedetails;
        private String instructor;

        public CourseBuilder setCoursename(String coursename) {
            this.coursename = coursename;
            return this;
        }

        public CourseBuilder setDuration(String duration) {
            this.duration = duration;
            return this;
        }

        public CourseBuilder setLocation(String location) {
            this.location = location;
            return this;
        }

        public CourseBuilder setStartdate(Date startdate) {
            this.startdate = startdate;
            return this;
        }

        public CourseBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public CourseBuilder setCoursedetails(String coursedetails) {
            this.coursedetails = coursedetails;
            return this;
        }

        public CourseBuilder setInstructor(String instructor) {
            this.instructor = instructor;
            return this;
        }

        public Course build() {
            return new Course(this);
        }
    }

    public static Course getCourseByName(String name) {
        LazyList<Course> list = where("coursename = ?", name);
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

}
