package com.jack.wechat.controller;

import com.jack.wechat.utils.MessageUtil;
import com.jack.wechat.utils.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    //    @Autowired
    //    private IMsgService msgService;
    //    @Autowired
    //    private MsgMapper msgMapper;

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
        //        long id = msgMapper.insert(BeanUtils.mapToBean(map, Msg.class));
        //        LOGGER.info(msgMapper.selectById(Long.valueOf(id))
        //            .toString());
    }

}
