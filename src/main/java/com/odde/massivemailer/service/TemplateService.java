package com.odde.massivemailer.service;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Template;

import java.util.List;

/**
 * Created by Cadet on 11/26/2015.
 */
public interface TemplateService {

    List<Template> getTemplateList();
}
