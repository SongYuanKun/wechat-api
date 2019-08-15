package com.songyuankun.wechat.constant;

/**
 * RedisCacheNames
 *
 * @author songyuankun
 */
public class RedisCacheNames {

    private static final String PRO_FIX = "BLOG:";
    /**
     * 文章缓存空间名称
     */
    public static final String ARTICLE = PRO_FIX + "ARTICLE";
    /**
     * 图书缓存空间名称
     */
    public static final String BOOK = PRO_FIX + "BOOK";
    /**
     * 笔记缓存空间名称
     */
    public static final String BOOK_NOTE = PRO_FIX + "BOOK_NOTE";
    /**
     * 友情链接列表
     */
    public static final String LINK = PRO_FIX + "LINK";
    /**
     * 推荐列表
     */
    public static final String RECOMMEND = PRO_FIX + "RECOMMEND";
    /**
     * 标签列表
     */
    public static final String TAG = PRO_FIX + "TAG";
    /**
     * 分类列表
     */
    public static final String CATEGORY = PRO_FIX + "CATEGORY";
    /**
     * 时光轴
     */
    public static final String TIMELINE = PRO_FIX + "TIMELINE";

    private RedisCacheNames() {
    }

}
