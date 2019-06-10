package steps.site.pages;

import com.odde.massivemailer.model.SentMailVisit;

import static com.odde.massivemailer.model.base.Repository.repo;

public class Notifications {
    public static int getSentMailVisitCount(String receipient) {
        SentMailVisit nd = repo(SentMailVisit.class).findFirstBy("email_address", receipient);
        return nd.getReadCount();
    }
}
