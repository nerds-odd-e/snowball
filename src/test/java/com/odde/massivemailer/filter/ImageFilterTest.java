package com.odde.massivemailer.filter;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.model.SentMailVisit;
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
import java.util.Date;

import static com.odde.massivemailer.model.base.Repository.repo;
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
        SentMail mail = new SentMail(new Date(), "asdf", "", 0L, "").saveIt();
        SentMailVisit nd = new SentMailVisit("my@a.b.com", 0, mail.getId()).saveIt();
        request.setParameter(ImageFilter.TOKEN, nd.getStringId());
        filter.doFilter(request, response, chain);
        SentMailVisit nd1 = repo(SentMailVisit.class).findById(nd.getId());
        assertEquals(1, nd1.getReadCount());
    }
}
