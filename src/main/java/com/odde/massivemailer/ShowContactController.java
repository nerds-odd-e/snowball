package com.odde.massivemailer;

import java.util.ArrayList;
import java.util.List;

import com.odde.massivemailer.model.ContactPerson;

public class ShowContactController {

	public List<ContactPerson> getContactData() {
		List<ContactPerson> cpList = new ArrayList<ContactPerson>();
		ContactPerson cp = new ContactPerson();
		cp.setId(1);
		cp.setName("mail@hotmail.com");
		cpList.add(cp);
		return cpList;
	}

}
