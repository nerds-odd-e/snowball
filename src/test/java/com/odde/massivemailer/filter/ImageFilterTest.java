package com.odde.massivemailer.filter;


import com.odde.massivemailer.service.TrackingService;
import org.junit.Before;
import org.junit.*;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;


import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.Mockito.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;

public class ImageFilterTest {
    ImageFilter imageFilter;
    TrackingService trackingService;

    @Before
    public void setUpMockService() {
        trackingService = mock(TrackingService.class);
        imageFilter = mock(ImageFilter.class);
    }

    @Test
    public void imageRequestShouldIncreaseViewCount() throws IOException, ServletException {

        HttpServletRequest mockReq = mock(HttpServletRequest.class);
        HttpServletResponse mockResp = mock(HttpServletResponse.class);
        FilterChain mockFilterChain = mock(FilterChain.class);
        FilterConfig mockFilterConfig = mock(FilterConfig.class);
        when(mockReq.getRequestURI()).thenReturn("/");

        imageFilter.init(mockFilterConfig);
        imageFilter.doFilter(mockReq, mockResp, mockFilterChain);
        imageFilter.destroy();
    }
}
