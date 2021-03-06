package com.jack.wechat.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @Desciption: 地理位置消息
 * @author: Jacky Chai
 * @date: 2019/4/5
 */
@Getter
@Setter
public class LocationMessage extends BaseMessage {

    /**
     * 地理位置维度
      */
    private String Location_X;
    /**
     * 地理位置经度
      */
    private String Location_Y;
    /**
     * 地图缩放大小
      */
    private String Scale;
    /**
     * 地理位置信息
      */
    private String Label;
}
