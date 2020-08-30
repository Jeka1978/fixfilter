package com.example.fixfilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

//@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FixFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("fixed------------------->");
        ///String body = new BufferedReader(new InputStreamReader(servletRequest.getInputStream())).lines().collect(Collectors.joining());

        String body = new BufferedReader(new InputStreamReader(servletRequest.getInputStream())).lines().collect(Collectors.joining());

        MyRequestWrapper myRequestWrapper = new MyRequestWrapper((HttpServletRequest) servletRequest, body);
        filterChain.doFilter(myRequestWrapper, servletResponse);


//

    }

    @Override
    public void destroy() {

    }

    class MyRequestWrapper extends HttpServletRequestWrapper {
        private String body;
        private ObjectMapper mapper = new ObjectMapper();

        public MyRequestWrapper(HttpServletRequest request, String body) throws IOException {
            super(request);
            this.body = body;
        }

        public String getBody() {
            return body;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {

            byte[] bytes;
            if (body.contains("ebayProductName")) {

                NewProduct newProduct = mapper.readValue(body, NewProduct.class);
                Product product = Product.builder().ebayProductName(newProduct.getEbayProductName()).cost(newProduct.getPrice() * 3).build();
                String newBody = mapper.writeValueAsString(product);
                bytes = newBody.getBytes(StandardCharsets.UTF_8);
            } else {
                bytes = body.getBytes(StandardCharsets.UTF_8);

            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            return new ServletInputStream() {


                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }
    }
}
