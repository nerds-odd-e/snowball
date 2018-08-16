package com.odde.massivemailer.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class LoginFilterTest {
    private LoginFilter filter;

    private MockHttpServletRequest request = new MockHttpServletRequest();

    @Mock
    private ServletResponse response;

    @Mock
    private FilterChain chain;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        filter = new LoginFilter();
    }

    @Test
    public void testFilter() throws IOException, ServletException {
        filter.doFilter(request, response, chain);
    }
}
