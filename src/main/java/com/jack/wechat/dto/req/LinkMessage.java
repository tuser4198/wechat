package com.jack.wechat.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @Desciption:链接信息
 *
 * @author: Jacky Chai
 * @date: 2019/4/5
 */
@Getter
@Setter
public class LinkMessage extends BaseMessage {
    /**
     * 消息标题
     */
    private String Title;
    /**
     * 消息描述
     */
    private String Description;
    /**
     * 消息链接
     */
    private String Url;
}
