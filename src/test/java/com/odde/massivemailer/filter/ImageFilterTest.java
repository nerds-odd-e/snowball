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
        SentMailVisit nd = new SentMailVisit("my@a.b.com", 0, mail.getId().toString()).saveIt();
        request.setParameter(ImageFilter.TOKEN, nd.getStringId());
        filter.doFilter(request, response, chain);
        SentMailVisit nd1 = SentMailVisit.repository().findById(nd.getId());
        assertEquals(1, nd1.getReadCount());
    }
}
