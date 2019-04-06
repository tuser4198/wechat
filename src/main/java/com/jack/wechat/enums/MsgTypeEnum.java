package com.jack.wechat.enums;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @Desciption:
 * @author: Jacky Chai
 * @date: 2019/4/5
 */
@Getter
public enum MsgTypeEnum {
    EVENT(1, "事件"),
    TEXT(2, "文本"),
    IMAGE(3, "图片"),
    LOCATION(4, "地理位置"),
    LINK(5, "链接"),
    VIDEO(6, "视频");

    private int id;

    private String desc;

    public static Map<Integer,MsgTypeEnum> MAP;

    static {
        MsgTypeEnum[] values = values();
        MAP = Maps.newHashMapWithExpectedSize(values.length);
        for (MsgTypeEnum value : values) {
            MAP.put(value.id, value);
        }
    }

    MsgTypeEnum(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public static MsgTypeEnum codeOf(String code) {
        return MAP.get(code);
    }

}
