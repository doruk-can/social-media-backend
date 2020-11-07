package com.group308.socialmedia.api.filter;

import brave.Span;
import brave.Tracer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class AddResponseHeaderFilter implements Filter {

    private final Tracer tracer;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        Span currentSpan = this.tracer.currentSpan();
        ((HttpServletResponse) response).addHeader("X-B3-TraceId", currentSpan.context().traceIdString());

        chain.doFilter(request, response);
    }
}