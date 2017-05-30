INSERT OR IGNORE INTO Template (TemplateName, Subject, Content)
VALUES ('Default Template 1', 'Greeting {FirstName}', 'Hi {FirstName} {LastName},

 Please find your course details below

 Course: {CourseName}
 Instructor: {Instructor}
 Location: {Location}

 Best Regards
 {Instructor}');

Update template set "Content" ="Hi {FirstName} {LastName},

 Please find your course details below

 Course: {CourseName}
 Instructor: {Instructor}
 Location: {Location}

 Best Regards
 {Instructor}

 " where templateName='Default Template 1';