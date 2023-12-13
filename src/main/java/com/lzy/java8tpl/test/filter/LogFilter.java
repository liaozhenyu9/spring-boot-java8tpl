package com.lzy.java8tpl.test.filter;

import com.alibaba.fastjson2.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.util.*;

@WebFilter(filterName = "logFilter", urlPatterns = "/*")
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class LogFilter implements Filter {

    private static final Set<String> IGNORE_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/**/swagger-ui/**", "/**/swagger-resources/**", "/**/api-docs")));

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        // 忽略文件上传
        String contentType = httpServletRequest.getHeader("content-type");
        if (contentType != null && contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // 忽略指定url
        String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length()).replaceAll("[/]+$", "");
        PathMatcher matcher = new AntPathMatcher();
        boolean isIgnore = IGNORE_PATHS.stream().anyMatch(ignore -> matcher.match(ignore, path));
        if (isIgnore) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        ResponseWrapper wrapperResponse = new ResponseWrapper(httpServletResponse);
        RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
        long before = System.currentTimeMillis();

        log.info("========================================== Request Start ==========================================");
        log.info("URL            : {}", httpServletRequest.getRequestURL().toString());

        Map<String, String> headers = new HashMap<>(32);
        Enumeration<String> names = httpServletRequest.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            headers.put(name, httpServletRequest.getHeader(name));
        }
        log.info("Headers            : {}", headers);
        log.info("HTTP Method    : {}", httpServletRequest.getMethod());
        log.info("IP             : {}", httpServletRequest.getRemoteAddr());
        log.info("Request Body   : {}", JSON.toJSON(requestWrapper.getBody()));
        filterChain.doFilter(requestWrapper, wrapperResponse);
        byte[] content = wrapperResponse.getContent();
        log.info("Response Status  : {}", wrapperResponse.getStatus());
        log.info("Response Content  : {}", JSON.toJSON(new String(content)));
        log.info("Time-Consuming : {} ms", System.currentTimeMillis() - before);
        log.info("=========================================== End ===========================================");
        ServletOutputStream out = httpServletResponse.getOutputStream();
        out.write(content);
        out.flush();
    }
}


class ResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream buffer;

    private final ServletOutputStream out;

    public ResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        buffer = new ByteArrayOutputStream();
        out = new WrapperOutputStream(buffer);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return out;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (out != null) {
            out.flush();
        }
    }

    public byte[] getContent() throws IOException {
        flushBuffer();
        return buffer.toByteArray();
    }

    static class WrapperOutputStream extends ServletOutputStream {
        private final ByteArrayOutputStream bos;

        public WrapperOutputStream(ByteArrayOutputStream bos) {
            this.bos = bos;
        }

        @Override
        public void write(int b) {
            bos.write(b);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener arg0) {

        }
    }

}

class RequestWrapper extends HttpServletRequestWrapper {
    private final String body;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ignored) {

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        body = stringBuilder.toString();
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };

    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBody() {
        return this.body;
    }

}
