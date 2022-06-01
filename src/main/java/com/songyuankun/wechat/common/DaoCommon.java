package com.songyuankun.wechat.common;

import com.songyuankun.wechat.entity.BaseEntity;
import org.springframework.security.core.Authentication;

import java.util.Date;

/**
 * 创建更新参数统一获取
 *
 * @author songyuankun
 */
public class DaoCommon {

    private DaoCommon() {
    }

    public static <T extends BaseEntity> void createDao(Authentication authentication, T baseDao) {
        Date date = new Date();
        String name = authentication.getName();
        String[] split = name.split(":");
        Integer userId = Integer.valueOf(split[0]);
        baseDao.setCreateTime(date);
        baseDao.setCreateUserId(userId);
        updateDao(authentication, baseDao);
    }

    public static <T extends BaseEntity> void updateDao(Authentication authentication, T baseDao) {
        Date date = new Date();
        Integer userId = Integer.valueOf(authentication.getName());
        baseDao.setUpdateTime(date);
        baseDao.setUpdateUserId(userId);
    }
}
