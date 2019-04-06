package com.jack.wechat.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @Desciption: 文本消息
 * @author: Jacky Chai
 * @date: 2019/4/5
 */
@Getter
@Setter
public class TextMessage extends BaseMessage {
    /**
     * 消息内容
      */
    private String Content;
}
