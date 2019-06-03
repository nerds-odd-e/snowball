package steps.site.pages;

import com.odde.massivemailer.model.SentMailVisit;

public class Notifications {
    public static int getSentMailVisitCount(String receipient) {
        SentMailVisit nd = SentMailVisit.repository().findFirstBy("email_address", receipient);
        return nd.getReadCount();
    }
}
