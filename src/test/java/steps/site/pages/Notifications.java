package steps.site.pages;

import com.odde.massivemailer.model.SentMailVisit;

public class Notifications {
    public static int getSentMailVisitCount(String receipient) {
        SentMailVisit nd = SentMailVisit.first("email_address = ?", receipient);
        return nd.getReadCount();
    }
}
