package com.odde.massivemailer.model;

import com.odde.massivemailer.model.base.Entity;
import com.odde.massivemailer.service.LocationProviderService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.odde.massivemailer.model.base.Repository.repo;

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
    @Valid
    private Location geoLocation;

    public static List<Course> findAllCourseNearTo(Location geoCordinate) {
        List<Course> nearbyCources = new ArrayList<>();
        List<Course> allCourse = repo(Course.class).findAll();
        for (Course course : allCourse) {
            if (course.isNearTo(geoCordinate))
                nearbyCources.add(course);
        }
        return nearbyCources;
    }

    private boolean isNearTo(Location geoCordinate) {
        return geoLocation.IsNear(geoCordinate);
    }

    public String location() {
        return LocationProviderService.locationString(city, country);
    }

    public static Course getCourseByName(String name) {
        List<Course> list = repo(Course.class).findBy("courseName", name);
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

    private List<Participant> participations() {
        return repo(Participant.class).findBy("courseId", getId());
    }

    public List<ContactPerson> participants() {
        List<ContactPerson> participantDetails = new ArrayList<>();
        for (Participant partcipant: participations())
            participantDetails.add(partcipant.contactPerson());
        return participantDetails;
    }

    @Override
    public void onBeforeSave() {
        if (city == null || city.isEmpty())
            return;
        geoLocation = new LocationProviderService().getCoordinate(city, country);
    }
}
