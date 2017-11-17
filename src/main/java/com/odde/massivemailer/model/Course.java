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
        setGeoCoordinates();
    }


    public static Course createCourse(Map map) throws Exception {
        Course course = new Course(map);
        course.saveIt();
        return course;
    }


    public Course(Map<String, Object> map) throws Exception {

        if (map.get("city") != null && map.get("city").equals("Foobar")) {
            throw new Exception("CityName is invalid");
        }

        String[] keys = {"coursename", "address", "coursedetails", "duration", "instructor", "startdate", "latitude", "longitude"};

        for (String key :keys) {
            set(key, map.get(key));
        }

        if (map.get("city") == null) {
            set("location", map.get("country"));
        } else {
            set("location", map.get("country") + "/" + map.get("city"));
        }

        setGeoCoordinates();
    }

    private void setGeoCoordinates() {
        if(!StringUtils.isEmpty(getLocation())){
            LocationProviderService locationProviderService = new LocationProviderService();
            Location location = locationProviderService.getLocationForName(getLocation());

            set("latitude", location.getLat());
            set("longitude", location.getLng());
        }
    }

    public static List<Course> findAllCourseNearTo(Location geoCordinate) {
        List<Course> nearbyCources = new ArrayList<>();
        List<Course> allCourse = Course.findAll();
        for (Course course: allCourse) {
            if(course.isNearTo(geoCordinate))
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
