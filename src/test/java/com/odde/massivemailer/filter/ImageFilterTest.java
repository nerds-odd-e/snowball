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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(request.getParameter("messageId")).thenReturn("1");
        when(request.getParameter("userId")).thenReturn("terry@odd-e.com");

        filter.doFilter(request, response, chain);

        ArgumentCaptor<Long> messageIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> emailAddressCaptor = ArgumentCaptor.forClass(String.class);

        verify(trackingService).updateViewCount(messageIdCaptor.capture(), emailAddressCaptor.capture());

        assertThat(messageIdCaptor.getValue(), is(1L));
        assertThat(emailAddressCaptor.getValue(), is("terry@odd-e.com"));
    }

    @Test
    public void FilterMustNotUpdateNotificationDetailIfNoMessageId() throws IOException, ServletException {
        when(request.getParameter("messageId")).thenReturn(null);
        when(request.getParameter("userId")).thenReturn("terry@odd-e.com");

        filter.doFilter(request, response, chain);

        verify(trackingService, never()).updateViewCount(any(Long.class), any(String.class));
    }

    @Test
    public void FilterMustNotUpdateNotificationDetailIfNoUserId() throws IOException, ServletException {
        when(request.getParameter("messageId")).thenReturn("1");
        when(request.getParameter("userId")).thenReturn(null);

        filter.doFilter(request, response, chain);

        verify(trackingService, never()).updateViewCount(any(Long.class), any(String.class));
    }
}
