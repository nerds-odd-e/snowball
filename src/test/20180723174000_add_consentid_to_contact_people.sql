IF NOT EXISTS (
  select
    null
  from information_schema.columns
  where table_name = 'contact_people'
    and table_schema = 'massive_mailer_development'
    and column_name = 'consent_id'
) THEN
alter table contact_people add column consent_id varchar(64) default null;
END IF;
