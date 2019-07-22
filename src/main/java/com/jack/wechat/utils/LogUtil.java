package com.jack.wechat.utils;

import com.jack.wechat.wrapper.WrappedHttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 * @Desciption:
 * @author: Chai jin qiu
 * @date: 2019/5/24
 */
@Slf4j
public class LogUtil {

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

    public static void collectBaseInfo(HttpServletRequest httpServletRequest, StringBuilder requestInfoBuilder) {
        try {
            requestInfoBuilder.append(LINE_SEP)
                .append("ip: ")
                .append(httpServletRequest.getRemoteAddr())
                .append(LINE_SEP);
            requestInfoBuilder.append(httpServletRequest.getMethod());
            requestInfoBuilder.append(" ");
            requestInfoBuilder.append(httpServletRequest.getRequestURL());
            requestInfoBuilder.append(LINE_SEP);
            requestInfoBuilder.append("params: ");
            collectParams(httpServletRequest, requestInfoBuilder);
            requestInfoBuilder.append(LINE_SEP);
            collectHeader(httpServletRequest, requestInfoBuilder);
        } catch (Throwable ignore) {
            // ignore
            log.error("error occured when parsing basic param", ignore);
        }
    }

    /**
     * 获取参数
     *
     * @param httpServletRequest
     * @param stringBuilder
     */
    private static void collectParams(HttpServletRequest httpServletRequest, StringBuilder stringBuilder) {
        if (StringUtils.startsWith(httpServletRequest.getContentType(), ContentType.APPLICATION_JSON.getMimeType())) {
            collectBodyParm(httpServletRequest, stringBuilder);
        } else {
            collectUrlParams(httpServletRequest, stringBuilder);
        }
    }

    private static void collectUrlParams(HttpServletRequest httpServletRequest, StringBuilder stringBuilder) {
        Iterator<Map.Entry<String, String[]>> entryIterator = httpServletRequest.getParameterMap()
            .entrySet()
            .iterator();
        Map.Entry<String, String[]> paramEntry;
        if (entryIterator.hasNext()) {
            paramEntry = entryIterator.next();
            stringBuilder.append(paramEntry.getKey())
                .append("=")
                .append(getStringFromStringArray(paramEntry.getValue()));
        }

        while (entryIterator.hasNext()) {
            paramEntry = entryIterator.next();
            stringBuilder.append("&");
            stringBuilder.append(paramEntry.getKey())
                .append("=")
                .append(getStringFromStringArray(paramEntry.getValue()));
        }
    }

    private static String collectBodyParm(HttpServletRequest request, StringBuilder stringBuilder) {
        stringBuilder.append(new String(((WrappedHttpServletRequest)request).getBody()));
        return stringBuilder.toString();
    }

    /**
     * 收集请求中的header信息
     *
     * @param httpServletRequest http请求
     * @param stringBuilder      日志builder
     */
    private static void collectHeader(HttpServletRequest httpServletRequest, StringBuilder stringBuilder) {
        final Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String headerName = headerNames.nextElement();
            if ("content-length".equalsIgnoreCase(headerName)) {
                continue;
            }
            stringBuilder.append(headerName);
            stringBuilder.append(": ");
            final String header = httpServletRequest.getHeader(headerName);
            stringBuilder.append(header);
            stringBuilder.append(LINE_SEP);
        }
        stringBuilder.append(LINE_SEP);
    }

    private static String getStringFromStringArray(String[] source) {
        if (source == null || source.length == 0) {
            return StringUtils.EMPTY;
        }
        if (source.length == 1) {
            return source[0];
        }

        return Arrays.toString(source);
    }

}
