package com.odde.massivemailer.filter;

import com.odde.massivemailer.service.TrackingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.InterruptedIOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ImageFilterTest {
    private ImageFilter filter;

    @Mock
    private ServletRequest request;

    @Mock
    private ServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private TrackingService trackingService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        filter = new ImageFilter();
        filter.setTrackingService(trackingService);
    }

    @Test
    public void FilterMustUpdateNotificationDetailMatchingToken() throws IOException, ServletException {
        when(request.getParameter(ImageFilter.TOKEN)).thenReturn("123456");

        filter.doFilter(request, response, chain);

        ArgumentCaptor<Integer> tokenCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(trackingService).updateViewCount(tokenCaptor.capture());

        assertThat(tokenCaptor.getValue(), is(123456));
    }

    @Test
    public void FilterMustNotUpdateNotificationDetailIfNoToken() throws IOException, ServletException {
        when(request.getParameter(ImageFilter.TOKEN)).thenReturn(null);

        filter.doFilter(request, response, chain);

        verify(trackingService, never()).updateViewCount(any(Integer.class));
    }
}
