package com.odde.massivemailer.model;

import com.google.common.base.Strings;
import com.odde.massivemailer.model.base.Entity;
import com.odde.massivemailer.model.base.Repository;
import com.odde.massivemailer.model.base.ValidationException;
import com.odde.massivemailer.service.LocationProviderService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javalite.activejdbc.Errors;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Course extends Entity<Course> {
    private String courseName;
    private String courseDetails="";
    private String instructor="";
    private String duration="";
    private String city="";
    private String country="";
    private String startDate="";
    private String address="";
    private Double latitude;
    private Double longitude;

    public static Repository<Course> repository() {
        return new Repository<>(Course.class, "courses");
    }

    public static List<Course> findAllCourseNearTo(Location geoCordinate) {
        List<Course> nearbyCources = new ArrayList<>();
        List<Course> allCourse = Course.repository().findAll();
        for (Course course : allCourse) {
            if (course.isNearTo(geoCordinate))
                nearbyCources.add(course);
        }
        return nearbyCources;
    }

    public static Course create(String ...args) {
        return repository().fromKeyValuePairs(args);
    }

    private boolean isNearTo(Location geoCordinate) {
        return getGeoCoordinates().IsNear(geoCordinate);
    }

    public String getLocation() {
        return LocationProviderService.locationString(city, country);
    }

    private Location getGeoCoordinates() {
        return new Location(getLocation(), latitude, longitude);
    }

    public static Course getCourseByName(String name) {
        List<Course> list = repository().findBy("courseName", name);
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

    private List<Participant> participations() {
        return Participant.repository().findBy("courseId", getId());
    }

    public List<ContactPerson> participants() {
        List<ContactPerson> participantDetails = new ArrayList<>();
        for (Participant partcipant: participations())
            participantDetails.add(partcipant.getContactPerson());
        return participantDetails;
    }

    public Course saveIt() {
        repository().save(this);
        return this;
    }

    @Override
    public boolean onBeforeSave() {
        if (Strings.isNullOrEmpty(city))
            return true;
        Location coordinate = new LocationProviderService().getCoordinate(city, country);
        if (coordinate.getLat() == null) {
            // throw new ValidationException("city cannot be located");
            return false;
        }
        setLatitude(coordinate.getLat());
        setLongitude(coordinate.getLng());
        return true;
    }

    public boolean save() {
        saveIt();
        return getId() != null;
    }

    public Errors errors() {
        Errors errors = new Errors();
        errors.put("city", "cannot be located");
        return errors;
    }

}
