package com.jack.wechat.controller;

import com.jack.wechat.utils.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


/**
 * @author: jacky
 * @date: 2019/3/14
 */
@RestController
@RequestMapping("/auth/")
public class AuthController {

    private static final Logger LOGGER =LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/handshake")
    public String handShakeAuth(
            @RequestParam(name = "signature") String signature,
            @RequestParam(name = "timestamp") String timestamp,
            @RequestParam(name = "nonce") String nonce,
            @RequestParam(name = "echostr") String echostr
    ) {
        LOGGER.info("signature:{},timestamp:{},nonce:{},echostr:{}",signature,timestamp,nonce,echostr);
        if(SignUtil.checkSignature(signature,timestamp,nonce)){
            return echostr;
        }
        throw new RuntimeException("非法请求");
    }

}
