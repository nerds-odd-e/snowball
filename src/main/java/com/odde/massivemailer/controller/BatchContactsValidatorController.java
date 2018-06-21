package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/batchContacts")
public class BatchContactsValidatorController extends AppController {

    private static final int EMAIL_INDEX = 0;
    private static final int FIRSTNAME_INDEX = 1;
    private static final int LASTNAME_INDEX = 2;
    private static final int COMPANY_INDEX = 3;
    private static final int COUNTRY_INDEX = 4;
    private static final int CITY_INDEX = 5;

    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resultMsg = "add_contact_batch_validation.jsp";
        String data = req.getParameter("data");
        String[] rows = data.split(";");
        List<ContactPerson> contactPersonList = new ArrayList<>();
        String responseString ="";
        for (int i = 1; i < rows.length; i++) {
            String currentContact = rows[i];
            String[] contactInformation = currentContact.split(",");
            ContactPerson contactPerson = new ContactPerson(contactInformation[EMAIL_INDEX],
                    contactInformation[FIRSTNAME_INDEX], contactInformation[LASTNAME_INDEX],
                    contactInformation[COMPANY_INDEX],
                    contactInformation[CITY_INDEX] + "/" + contactInformation[COUNTRY_INDEX]);
            contactPersonList.add(contactPerson);
            responseString += currentContact + ",;";
        }

        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.print(responseString);
        resp.sendRedirect(resultMsg);
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern p = Pattern.compile(emailPattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

}