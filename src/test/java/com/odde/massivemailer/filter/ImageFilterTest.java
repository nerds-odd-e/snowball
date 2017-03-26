package com.odde.massivemailer.filter;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.NotificationDetail;
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;

@RunWith(TestWithDB.class)
public class ImageFilterTest {
    private ImageFilter filter;

    private MockHttpServletRequest request = new MockHttpServletRequest();

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
    public void FilterMustUpdateNotificationDetailMatchingToken() throws IOException, ServletException {
        NotificationDetail nd = NotificationDetail.createIt("notification_id", "1", "email_address", "my@a.b.com", "read_count", 0);
        request.setParameter(ImageFilter.TOKEN, nd.getString("id"));
        filter.doFilter(request, response, chain);
        NotificationDetail nd1 = NotificationDetail.findById(nd.getLongId());
        nd.refresh();
        assertEquals(1, nd1.getReadCount());

    }
}
