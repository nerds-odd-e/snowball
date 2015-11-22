package com.odde.massivemailer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.odde.massivemailer.controller.ContactsController;
import org.junit.Before;
import org.junit.Test;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.impl.SqliteContact;

public class ContactsControllerTest {
    ContactsController showContactController;
    ContactService contactService;

    @Before
    public void setUpMockService() {
        contactService = mock(SqliteContact.class);
        showContactController = new ContactsController(contactService);
    }

    @Test
    public void shouldReturnJSONDataOfContact() {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp = new ContactPerson();
        cp.setId(1);
        cp.setName("mail@hotmail.com");
        cpList.add(cp);
        //String contactJSON = showContactController.convertContactToJSON(cpList);
        //assertEquals("[{\"id\":1,\"name\":\"mail@hotmail.com\"}]", contactJSON);
    }
}
