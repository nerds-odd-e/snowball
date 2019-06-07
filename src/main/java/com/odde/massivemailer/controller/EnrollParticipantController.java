package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Participant;
import org.bson.types.ObjectId;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet("/enroll_participants")
public class EnrollParticipantController extends AppController {

    private static class DBRecord {
        private final String[] line;
        private final String courseId;

        public static Function<String[], DBRecord> mapper(String courseId) {
            return l -> new DBRecord(l, courseId);
        }

        public DBRecord(String[] line, String courseId) {
            this.line = line;
            this.courseId = courseId;
        }

        boolean validate() {
            Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(line[0]);
            return matcher.find();
        }

        public void save() {
            ContactPerson contactPerson = ContactPerson.repository().fromKeyValuePairs(
                    "email", line[0],
                    "firstName", line[1],
                    "lastName", line[2],
                    "company", line[3],
                    "city", line[4],
                    "country", line[5]);
            contactPerson.save();
            ObjectId contactPersonId = ContactPerson.getContactByEmail(line[0]).getId();
            new Participant(contactPersonId, new ObjectId(this.courseId)).saveIt();
        }

        public String getSingleLine() {
            return Stream.of(line).collect(Collectors.joining("  "));
        }
    }

    private enum ValidationResult {
        Valid, Invalid
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String courseId = request.getParameter("courseId");

        String[] participantsLines = request.getParameter("participants").split("\n");
        Map<ValidationResult, List<DBRecord>> validatedRecords = Arrays.stream(participantsLines).map(line -> line.split("\\s+"))
                .map(DBRecord.mapper(courseId))
                .collect(Collectors.groupingBy(record -> {
                    if (record.validate()) return ValidationResult.Valid;
                    else return ValidationResult.Invalid;
                }));
        validatedRecords.computeIfAbsent(ValidationResult.Valid, key -> new ArrayList<>())
                .forEach(DBRecord::save);
        String errors = validatedRecords.computeIfAbsent(ValidationResult.Invalid, key -> new ArrayList<>())
                .stream()
                .map(DBRecord::getSingleLine)
                .collect(Collectors.joining("\n"));
        try {
            response.sendRedirect("course_detail.jsp?id=" + courseId + "&errors=" + URLEncoder.encode(errors, "UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
