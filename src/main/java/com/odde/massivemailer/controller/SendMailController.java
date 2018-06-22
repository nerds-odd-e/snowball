package com.odde.massivemailer.controller;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.service.GDPRService;
import com.odde.massivemailer.startup.Universe;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
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

    private volatile GDPRService gdprService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        gdprService = Universe.gdprService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {

            final List<String> recipientList = getRecipientList(req);
            if (recipientList.isEmpty()) {
                return;
            }

            String content = req.getParameter("content");
            String subject = req.getParameter("subject");

            final Mail email = createEmail(System.currentTimeMillis(), content, subject, recipientList);
            SentMail sentMail = email.asSentMail().saveAll();
            email.setSentMail(sentMail);

            email.sendMailWith(getMailService());

            resp.sendRedirect("sendemail.jsp?status=success&msg=Email successfully sent&repcnt=" + email.getReceipts().size());

        } catch (EmailException e) {
            resp.sendRedirect("sendemail.jsp?status=failed&msg=Unable to send");

        } catch (SQLException e) {
            resp.sendRedirect("sendemail.jsp?status=failed&msg=Fail");
            e.printStackTrace();
        }
    }

    public List<String> getRecipientList(HttpServletRequest req) throws SQLException {

        String tempRecipient = req.getParameter("recipient");
        StringTokenizer st = new StringTokenizer(tempRecipient, ";");
        ArrayList<String> recipientList = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String recipient = st.nextToken();
            if (recipient.startsWith("company:")) {

                String[] aaa = recipient.split(":");
                String company = aaa[1];
                List<ContactPerson> contactList = getContactPersons(company);
                if (contactList.isEmpty()) {
                    throw new SQLException();
                }
                for (ContactPerson contactPerson : contactList) {
                    if (gdprService.canContactReceiveEmail(contactPerson)) {
                        recipientList.add(contactPerson.getEmail());
                    }
                }
            } else {
                ContactPerson contact = ContactPerson.getContactByEmail(recipient);
                if (contact == null || gdprService.canContactReceiveEmail(contact)) {
                    recipientList.add(recipient);
                }
            }
        }

        return recipientList;
    }

    public Mail createEmail(long messageId, String content, String subject, List<String> recipientList) {
        Mail email = new Mail();
        email.setMessageId(messageId);
        email.setContent(content);
        email.setSubject(subject);

        email.setReceipts(recipientList);

        return email;
    }

    private List<ContactPerson> getContactPersons(String company) {
        List<ContactPerson> contactList;

        company = company.replaceAll("\"", "");

        contactList = ContactPerson.getContactListFromCompany(company);
        return contactList;
    }


}
