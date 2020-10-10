package com.songyuankun.wechat.event;

import lombok.Data;

/**
 * 阅读 点赞 事件对象
 *
 * @author songyuankun
 */
@Data
public class NumberEvent {
    private Integer type;
    private Integer value;
}
