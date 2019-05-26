package com.odde.massivemailer.filter;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.model.SentMailVisit;
import com.odde.massivemailer.model.Template;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class ImageFilterTest {
    private ImageFilter filter;

    private final MockHttpServletRequest request = new MockHttpServletRequest();

    @Mock
    private ServletResponse response;

    @Mock
    private FilterChain chain;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        filter = new ImageFilter();
    }

    @Test
    public void FilterMustUpdateSentMailvisitMatchingToken() throws IOException, ServletException {
        Template template = new Template("Template", "", "").saveIt();
        SentMail mail = SentMail.createIt("template_id", template.getStringId());
        SentMailVisit nd = SentMailVisit.createIt("sent_mail_id", mail.getId(), "email_address", "my@a.b.com", "read_count", 0);
        request.setParameter(ImageFilter.TOKEN, nd.getString("id"));
        filter.doFilter(request, response, chain);
        SentMailVisit nd1 = SentMailVisit.findById(nd.getLongId());
        nd.refresh();
        assertEquals(1, nd1.getReadCount());
    }
}
