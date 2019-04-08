package com.jack.wechat.enums;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

/**
 * @Desciption:
 * @author: Jacky Chai
 * @date: 2019/4/5
 */
@Getter
public enum MsgTypeEnum {
    TEXT("text", "文本"),
    IMAGE("image", "图片"),
    LOCATION("location", "地理位置"),
    LINK("link", "链接"),
    VOICE("voice", "语音"),
    EVENT("event", "事件"),
    SHORT_VIDEO("shortvideo", "短视频"),
    VIDEO("video", "视频");

    private String code;

    private String desc;

    public static Map<String, MsgTypeEnum> MAP;

    static {
        MsgTypeEnum[] values = values();
        MAP = Maps.newHashMapWithExpectedSize(values.length);
        for (MsgTypeEnum value : values) {
            MAP.put(value.code, value);
        }
    }

    MsgTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MsgTypeEnum codeOf(String code) {
        return MAP.get(code);
    }

}
