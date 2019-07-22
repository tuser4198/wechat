package com.jack.wechat.msg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.wechat.msg.entity.Msg;
import com.jack.wechat.msg.mapper.MsgMapper;
import com.jack.wechat.msg.service.IMsgService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jacky chai
 * @since 2019-04-08
 */
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements IMsgService {

}
