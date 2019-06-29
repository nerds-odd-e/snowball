package cucumber.steps.site.pages;

import com.odde.snowball.model.SentMailVisit;

import static com.odde.snowball.model.base.Repository.repo;

public class Notifications {
    public static int getSentMailVisitCount(String receipient) {
        SentMailVisit nd = repo(SentMailVisit.class).findFirstBy("email_address", receipient);
        return nd.getReadCount();
    }
}
