package com.jack.wechat.filter;

import com.jack.wechat.utils.LogUtil;
import com.jack.wechat.wrapper.WrappedHttpServletRequest;
import com.jack.wechat.wrapper.WrappedHttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Desciption: 记录请求响应log
 * @author: Jacky Chai
 * @date: 2019/4/23
 */
public class LogFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    /**
     * 换行分隔符
     */
    public static final String LINE_SEP = System.lineSeparator();
    /**
     * 日志时间格式
     */
    public static final String LOG_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 响应打日志的阀值 1M
     */
    public static final int RESPONSE_PRINT_THRESHOLD = 1 * 1024 * 2014;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        // response wrapper
        HttpServletResponse httpServletResponse = new WrappedHttpServletResponse((HttpServletResponse)response);
        //request wrapper
        HttpServletRequest httpServletRequest = new WrappedHttpServletRequest((HttpServletRequest)request);
        StringBuilder requestInfoBuilder = new StringBuilder();
        // 收集 request uri, header
        LogUtil.collectBaseInfo(httpServletRequest, requestInfoBuilder);
        long requestStartAt = System.currentTimeMillis();
        try {
            chain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            String responseContent = ((WrappedHttpServletResponse)httpServletResponse).getResponseContent();
            final long requestEndTime = System.currentTimeMillis();
            long cost = requestEndTime - requestStartAt;
            if (responseContent.length() > RESPONSE_PRINT_THRESHOLD) {
                logger.info("{}{} cost time: {} ms {}", requestInfoBuilder.toString(), LINE_SEP, cost, LINE_SEP);
                return;
            }
            logger.info("{}{} cost time: {} ms {} response info: {}{}", requestInfoBuilder.toString(), LINE_SEP, cost,
                LINE_SEP,
                responseContent, LINE_SEP);
        }

    }

    @Override
    public void destroy() {

    }
}
