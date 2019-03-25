package com.jack.wechat.controller;

import com.jack.wechat.utils.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Desciption: 微信回复消息
 * @author: Jacky Chai
 * @date: 2019/3/25
 */
@RestController
@RequestMapping("/msg/")
public class MessageController {
    private static final Logger LOGGER =LoggerFactory.getLogger(MessageController.class);

    @PostMapping(value = "/pong")
    public void pong(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, String> map=MessageUtil.parseXml(request);
        LOGGER.info("payload: {}"+map.get("Content"));
    }
}
