package com.odde.massivemailer.filter;

import org.junit.*;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockFilterConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.FilterChain;


public class ImageFilterTest {

    @Test
    public void imageRequestShouldIncreaseViewCount() throws IOException, ServletException {
        ImageFilter filter = new ImageFilter();
        MockHttpServletRequest mockReq = new MockHttpServletRequest();
        MockHttpServletResponse mockResp = new MockHttpServletResponse();
        FilterChain mockFilterChain = new MockFilterChain();

        mockReq.setRequestURI("http://localhost:8070/massive_mailer/resources/images/qrcode.png");
        MockFilterConfig mockFilterConfig = new MockFilterConfig();

        filter.init(mockFilterConfig);
        filter.doFilter(mockReq, mockResp, mockFilterChain);
        Assert.assertEquals(mockResp.getStatus(), HttpServletResponse.SC_OK);
        filter.destroy();
    }
}
