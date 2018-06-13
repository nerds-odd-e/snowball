INSERT INTO template (TemplateName, Subject, Content)
SELECT * FROM (SELECT "Pre-course Template", "Your course date is coming close", "Welcome!") AS tmp
WHERE NOT EXISTS (
    SELECT TemplateName FROM template WHERE TemplateName = 'Pre-course Template'
) LIMIT 1;

