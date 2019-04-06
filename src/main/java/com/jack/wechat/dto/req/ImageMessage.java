package com.jack.wechat.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @Desciption:图片信息
 * @author: Jacky Chai
 * @date: 2019/4/5
 */
@Getter
@Setter
public class ImageMessage extends BaseMessage {

    /**
     * 图片链接
     */
    private String PicUrl;

}
