package com.odde.massivemailer.model;

import com.odde.massivemailer.service.LocationProviderService;
import org.apache.commons.lang3.StringUtils;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.annotations.Table;

import java.util.*;

@Table("courses")
public class Course extends ApplicationModel {
    public Course() {
    }

    public Course(String title, String content, String location) {
        setCourseName(title);
        setCourseDetails(content);
        setLocation(location);
    }

    public static Course createCourse(Map map) {
        Course course = new Course(map);
        course.saveIt();
        return course;
    }


    public Course(Map<String, Object> map) {
        this((String) map.get("coursename"), (String) map.get("coursedetails"), locationToAddress(map.get("city"), map.get("country")));
        String[] keys = {"address", "duration", "instructor", "startdate"};

        for (String key : keys) {
            set(key, map.get(key));
        }
    }

    private static String locationToAddress(Object city, Object country) {
        String location;
        if (city == null) {
            location = (String) country;
        } else {
            location = country + "/" + city;
        }
        return location;
    }

    public static List<Course> findAllCourseNearTo(Location geoCordinate) {
        List<Course> nearbyCources = new ArrayList<>();
        List<Course> allCourse = Course.findAll();
        for (Course course : allCourse) {
            if (course.isNearTo(geoCordinate))
                nearbyCources.add(course);
        }
        return nearbyCources;
    }

    private boolean isNearTo(Location geoCordinate) {
        return getGeoCoordinates().IsNear(geoCordinate);
    }

    public void setCourseName(String courseName) {
        set("coursename", courseName);
    }

    public void setInstructor(String instructor) {
        set("instructor", instructor);
    }

    public void setLocation(String location) {
        set("location", location);
        if (StringUtils.isEmpty(location))
            return;
        Location coordinate = new LocationProviderService().getCoordinate(location);
        set("latitude", coordinate.getLat());
        set("longitude", coordinate.getLng());
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

    private Location getGeoCoordinates() {
        return new Location(getLocation(), getDoubleAttribute("latitude"), getDoubleAttribute("longitude"));
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

    public double getDoubleAttribute(String name) {
        return (double) get(name);
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
