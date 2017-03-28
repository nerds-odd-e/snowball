package com.odde.massivemailer.model;

import com.odde.massivemailer.model.validator.UniquenessValidator;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Table("contact_people")
public class ContactPerson extends Model {
    static {
        validatePresenceOf("email");
        validateWith(new UniquenessValidator("email"));
    }

    public static final String FIRSTNAME = "FirstName";
    public static final String LASTNAME = "LastName";
    public static final String EMAIL = "Email";
    public static final String COMPANY = "Company";
    public static final String LOCATION = "Location";

    public Map<String, String> attributes = new HashMap<>();

    public ContactPerson() { }

    public ContactPerson(String name, String email, String lastname) {
        this(name, email, lastname, "");
    }

    public ContactPerson(String name, String email, String lastname, String company) {
        setName(name);
        setEmail(email);
        setLastname(lastname);
        setCompany(company);
    }

    public String getName() {
        return getAttribute(FIRSTNAME);
    }

    public void setName(String name) {
        setAttribute(FIRSTNAME, name);
    }

    public String getEmail() {
        return getAttribute(EMAIL);
    }

    public void setEmail(String email) {
        setAttribute(EMAIL, email);
    }

    public String getLastname() {
        return getAttribute(LASTNAME);
    }

    public void setLastname(String lastname) {
        setAttribute(LASTNAME, lastname);
    }

    public String getCompany() {
        return getAttribute(COMPANY);
    }

    public void setCompany(String company) {
        setAttribute(COMPANY, company);
    }

    private void setAttribute(String name, String value) {
        attributes.put(name, value);
        set(name.toLowerCase(), value);
    }

    public String getAttribute(String name) {
        Object o = get(name);
        if(o != null)
            return o.toString();
        return "";
    }

    public Set<String> getAttributeKeys() {
        return getMetaModel().getAttributeNamesSkipId();
    }

    public static List<ContactPerson> getContactListFromCompany(String company) {
        return where("company = ?", company);
    }

    public static ContactPerson getContactByEmail(String emailAddress) {
        LazyList<ContactPerson> list = where("email = ?", emailAddress);
        if (list.size()> 0)
            return list.get(0);
        return null;
    }

    public ContactPerson setLocation(String location) {
        return this;
    }
}
