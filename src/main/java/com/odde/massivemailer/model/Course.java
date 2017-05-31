package com.odde.massivemailer.model;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.text.ParseException;


/**
 * Created by csd on 30/5/17.
 */
@Table("courses")
public class Course extends Model {


    public Course() {

    }

    public Course(CourseBuilder courseBuilder) {

        setCoursename(courseBuilder.coursename);
        setDuration(courseBuilder.duration);
        setLocation(courseBuilder.location);
        setStartdate(courseBuilder.startdate);
        setAddress(courseBuilder.address);
        setCoursedetails(courseBuilder.coursedetails);
        setInstructor(courseBuilder.instructor);

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

    public void setCoursename(String coursename) {
        set("coursename", coursename);
    }

    public void setDuration(String duration) {
        set("duration", duration);
    }

    public void setLocation(String location) {
        set("location", location);
    }

    public void setStartdate(String startdate) {
        set("startdate", startdate);
    }

    public void setAddress(String address) {
        set("address", address);
    }

    public void setCoursedetails(String coursedetails) {
        set("coursedetails", coursedetails);
    }

    public void setInstructor(String instructor) {
        set("instructor", instructor);
    }

    public String getAttribute(String name) {
        return (String) get(name);
    }

    public static class CourseBuilder {

        private String coursename;
        private String duration;
        private String location;
        private String startdate;
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

        public CourseBuilder setStartdate(String startdate) {
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
