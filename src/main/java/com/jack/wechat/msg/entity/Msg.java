package com.jack.wechat.msg.entity;

//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author jacky chai
 * @since 2019-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Msg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接收方
     */
    private String toUserName;

    /**
     * 发送方
     */
    private String fromUserName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 消息id
     */
    private Long msgId;

    /**
     * 操作类型
     */
    private Integer actionType;

    //    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消息内容
     */
    private String content;

}
