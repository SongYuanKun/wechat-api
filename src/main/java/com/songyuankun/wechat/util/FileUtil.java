package com.songyuankun.wechat.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author songyuankun
 */
@Slf4j
public class FileUtil {

    private static final String D = ".";

    private FileUtil() {
    }

    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(D) + 1);
    }

}
