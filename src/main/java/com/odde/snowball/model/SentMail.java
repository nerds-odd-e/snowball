package com.odde.snowball.model;

import com.odde.snowball.model.base.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.mongodb.client.model.Filters.eq;
import static com.odde.snowball.model.base.Repository.repo;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentMail extends Entity<SentMail> {
    private Date sentDate;
    private String subject;
    private String content;
    private Long messageId;
    private String receivers;

    public void addEmailAddress(final String emailAddress) {
        SentMailVisit sentMailVisit = new SentMailVisit();
        sentMailVisit.setEmailAddress(emailAddress);

        addVisit(sentMailVisit);
    }

    public void addVisit(final SentMailVisit sentMailVisit) {
        sentMailVisit.setSentMailId(getId());
        sentMailVisit.save();
    }


    public String extract() {
        ArrayList<String> sarray = new ArrayList<>();
        int count = 0;
        for (SentMailVisit detail : sentMailVisits()) {
            count += detail.getReadCount();
            sarray.add(detail.toJSON());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone tz = TimeZone.getTimeZone("Asia/Singapore");
        dateFormat.setTimeZone(tz);
        String date = null;
        if (getSentDate() != null) {
            date = dateFormat.format(getSentDate());
        }
        return "{\"subject\":\"" + getSubject() + "\", \"sent_at\":\"" + date + "\", \"total_open_count\":" + count + ", \"emails\":[" + String.join(", ", sarray) + "]}";
    }

    public List<SentMailVisit> sentMailVisits() {
        return repo(SentMailVisit.class).find(eq("sentMailId", getId()));
    }

    public static SentMail getSentMailBy(String email) {
        return repo(SentMail.class).findFirstBy("receivers", email);
    }

}
