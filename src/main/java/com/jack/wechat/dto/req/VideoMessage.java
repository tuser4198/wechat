package com.jack.wechat.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @Desciption: 视频/小视屏消息
 * @author: Jacky Chai
 * @date: 2019/4/5
 */
@Getter
@Setter
public class VideoMessage extends BaseMessage {
    /**
     * 视频消息媒体 id，可以调用多媒体文件下载接口拉取数据
     */
    private String MediaId;
    /**
     * 视频消息缩略图的媒体 id，可以调用多媒体文件下载接口拉取数据
     */
    private String ThumbMediaId;
}
