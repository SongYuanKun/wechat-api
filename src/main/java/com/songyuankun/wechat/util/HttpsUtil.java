package com.songyuankun.wechat.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;


/**
 * @author songyuankun
 */
@Slf4j
public class HttpsUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private HttpsUtil() {
    }

    private static String getParams(Map<String, String> parameters) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        String params = "";
        if (parameters.size() > 0) {
            if (parameters.size() == 1) {
                for (Map.Entry entry : parameters.entrySet()) {
                    sb.append(entry.getKey()).append("=").append(
                            java.net.URLEncoder.encode(String.valueOf(entry.getValue()),
                                    DEFAULT_CHARSET));
                }
                params = sb.toString();
            } else {
                for (Map.Entry entry : parameters.entrySet()) {
                    sb.append(entry.getKey()).append("=").append(
                            java.net.URLEncoder.encode(String.valueOf(entry.getValue()),
                                    DEFAULT_CHARSET))
                            .append("&");
                }
                String tempParams = sb.toString();
                params = tempParams.substring(0, tempParams.length() - 1);
            }
        }
        return params;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    private static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("contentType", DEFAULT_CHARSET);
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), DEFAULT_CHARSET));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url        发送请求的URL
     * @param parameters 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, Map<String, String> parameters) {
        String params = "";
        try {
            params = getParams(parameters);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return sendGet(url, params);

    }

}