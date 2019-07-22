package com.jack.wechat.wrapper;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Desciption: 备份inputstream用于记录日志
 * @author: Jacky Chai
 * @date: 2019/4/24
 */
public class WrappedHttpServletRequest extends HttpServletRequestWrapper {
    private final byte[] body;

    public WrappedHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        body = StreamUtils.copyToByteArray(request.getInputStream());
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            /**
             * Returns true if data can be read without blocking else returns
             * false.
             *
             * @return <code>true</code> if data can be obtained without blocking,
             * otherwise returns <code>false</code>.
             * @since Servlet 3.1
             */
            @Override
            public boolean isReady() {
                return false;
            }

            /**
             * Instructs the <code>ServletInputStream</code> to invoke the provided
             * {@link ReadListener} when it is possible to read
             *
             * @param readListener the {@link ReadListener} that should be notified
             *                     when it's possible to read.
             * @throws IllegalStateException if one of the following conditions is true
             *                               <ul>
             *                               <li>the associated request is neither upgraded nor the async started
             *                               <li>setReadListener is called more than once within the scope of the same request.
             *                               </ul>
             * @throws NullPointerException  if readListener is null
             * @since Servlet 3.1
             */
            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }
}
