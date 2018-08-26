package com.odde.massivemailer.controller;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@WebServlet("/sendMail")
public class SendMailController extends AppController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {

            Mail email = new Mail();
            String tempRecipient = req.getParameter("recipient");
            StringTokenizer st = new StringTokenizer(tempRecipient, ";");
            ArrayList<String> recipientList = getRecipients(st);
            email.setMessageId(System.currentTimeMillis());
            email.setContent(req.getParameter("content"));
            email.setSubject(req.getParameter("subject"));

            email.sendMailToRecipients(recipientList, getMailService());

            resp.sendRedirect("sendemail.jsp?status=success&msg=Email successfully sent&repcnt=" + email.getReceipts().size());

        } catch (EmailException e) {
            resp.sendRedirect("sendemail.jsp?status=failed&msg=Unable to send");

        } catch (SQLException e) {
            resp.sendRedirect("sendemail.jsp?status=failed&msg=Fail");
            e.printStackTrace();
        }
    }

    private ArrayList<String> getRecipients(StringTokenizer st) throws SQLException {
        ArrayList<String> recipientList = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String recipient = st.nextToken();
            if (recipient.startsWith("company:")) {

                String[] aaa = recipient.split(":");
                String company = aaa[1].toString();
                List<ContactPerson> contactList = getContactPersons(company);
                if (contactList.isEmpty()) {
                    throw new SQLException();
                }
                for (ContactPerson contactPerson : contactList) {
                    recipientList.add(contactPerson.getEmail());
                }
            } else {
                recipientList.add(recipient);
            }
        }
        return recipientList;
    }

    private List<ContactPerson> getContactPersons(String company) throws SQLException {
        List<ContactPerson> contactList;

        company = company.replaceAll("\"", "");

        contactList = ContactPerson.getContactListFromCompany(company);
        return contactList;
    }


}
