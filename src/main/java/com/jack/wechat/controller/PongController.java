package com.jack.wechat.controller;

import com.jack.wechat.msg.entity.Msg;
import com.jack.wechat.msg.mapper.MsgMapper;
import com.jack.wechat.msg.service.IMsgService;
import com.jack.wechat.utils.JsonUtil;
import com.jack.wechat.utils.LocalDateTimeUtil;
import com.jack.wechat.utils.MessageUtil;
import com.jack.wechat.utils.SignUtil;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author: jacky
 * @date: 2019/3/14
 */
@RestController
@RequestMapping("/msg/")
public class PongController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PongController.class);

    @Autowired
    private IMsgService msgService;
    @Autowired
    private MsgMapper msgMapper;

    private static String lastMsg = "";

    @GetMapping("/pong")
    public String handShakeAuth(@RequestParam(name = "signature") String signature,
        @RequestParam(name = "timestamp") String timestamp, @RequestParam(name = "nonce") String nonce,
        @RequestParam(name = "echostr") String echostr) {
        LOGGER.info("signature:{},timestamp:{},nonce:{},echostr:{}", signature, timestamp, nonce, echostr);
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        throw new RuntimeException("非法请求");
    }

    @PostMapping(value = "/pong")
    public void pong(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = MessageUtil.parseXml(request);
        LOGGER.info(map.toString());
        map.remove("createTime");
        Msg msg = JsonUtil.readValue(JsonUtil.toJSon(map), Msg.class);
        msg.setCreateTime(LocalDateTimeUtil.getCurrentDateTime());
        //        msg.setContent(EmojiParser.parseToUnicode(msg.getContent()));
        msgMapper.insert(msg);
        Msg dbMsg = msgMapper.selectById(msg.getId());
        dbMsg.setContent(EmojiParser.parseToUnicode(dbMsg.getContent()));
        lastMsg = JsonUtil.toJSon(dbMsg);
        LOGGER.info(lastMsg);
    }

    @GetMapping(value = "/getLastMsg")
    public String getLastMsg() {
        return lastMsg;
    }

}
