package com.odde.massivemailer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.odde.massivemailer.controller.ShowContactController;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.impl.SqliteContact;

public class ShowContactControllerTest {
    ShowContactController showContactController = new ShowContactController();
    ContactService contactService;

    @Before
    public void setUpMockService() {
        contactService = mock(SqliteContact.class);
        showContactController.setContactService(contactService);
    }

    @Test
    public void testGetContactDataCallContactService() {
        showContactController.getContactData();

        verify(contactService, times(1)).getContactList();
    }

    @Test
    public void testGetContactData() {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp = new ContactPerson();
        cp.setId(1);
        cp.setName("mail12@hotmail.com");
        cpList.add(cp);
        cp = new ContactPerson();
        cp.setId(2);
        cp.setName("mailxx@hotmail.com");
        cpList.add(cp);
        when(contactService.getContactList()).thenReturn(cpList);

        List<ContactPerson> returnedCpList = showContactController.getContactData();

        assertEquals(returnedCpList.get(0).getId(), cpList.get(0).getId());
        assertEquals(returnedCpList.get(0).getName(), cpList.get(0).getName());
    }

    @Test
    public void shouldReturnJSONDataOfContact() {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp = new ContactPerson();
        cp.setId(1);
        cp.setName("mail@hotmail.com");
        cpList.add(cp);
        String contactJSON = showContactController.convertContactToJSON(cpList);
        assertEquals("[{\"id\":1,\"name\":\"mail@hotmail.com\"}]", contactJSON);
    }
}
