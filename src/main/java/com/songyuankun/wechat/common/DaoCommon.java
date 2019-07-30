package com.songyuankun.wechat.common;

import com.songyuankun.wechat.dao.BaseDao;
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

    public static <T extends BaseDao> void createDao(Authentication authentication, T baseDao) {
        Date date = new Date();
        Integer userId = Integer.valueOf(authentication.getName());
        baseDao.setCreateTime(date);
        baseDao.setCreateUserId(userId);
        baseDao.setUpdateTime(date);
        baseDao.setUpdateUserId(userId);
    }

    public static <T extends BaseDao> void updateDao(Authentication authentication, T baseDao) {
        Date date = new Date();
        Integer userId = Integer.valueOf(authentication.getName());
        baseDao.setUpdateTime(date);
        baseDao.setUpdateUserId(userId);
    }
}
