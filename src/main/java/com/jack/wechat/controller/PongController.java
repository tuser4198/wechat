package com.jack.wechat.controller;

import com.jack.wechat.msg.entity.Msg;
import com.jack.wechat.msg.mapper.MsgMapper;
import com.jack.wechat.msg.service.IMsgService;
import com.jack.wechat.utils.JsonUtil;
import com.jack.wechat.utils.LocalDateTimeUtil;
import com.jack.wechat.utils.MessageUtil;
import com.jack.wechat.utils.SignUtil;
import org.apache.commons.lang3.StringUtils;
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
        //        dbMsg.setContent(EmojiParser.parseToUnicode(dbMsg.getContent()));
        dbMsg.setContent(filterEmoji(dbMsg.getContent()));
        LOGGER.info("test equals:{}", lastMsg.equals(dbMsg.getContent()));
        lastMsg = dbMsg.getContent();
        LOGGER.info(lastMsg);
    }

    @GetMapping(value = "/getLastMsg")
    public String getLastMsg() {
        return lastMsg;
    }

    private static String filterEmoji(String source) {
        if (StringUtils.isEmpty(source)) {
            return "";
        }
        source = source.replace("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "")
            .replace("??", "");
        if (!containsEmoji(source)) {
            return source; //如果不包含，直接返回
        }
        //到这里铁定包含
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (!isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            } else {
            }
        }

        if (buf == null) {
            return source; //如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {
                //这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }

    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }

        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
            (codePoint == 0x9) ||
            (codePoint == 0xA) ||
            (codePoint == 0xD) ||
            ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
            ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
            ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

}
