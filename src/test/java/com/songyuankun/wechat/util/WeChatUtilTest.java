package com.songyuankun.wechat.util;

import com.songyuankun.wechat.WeChatApiApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WeChatUtilTest extends WeChatApiApplicationTests {

    @Autowired
    private WeChatUtil weChatUtil;

    @Test
    public void addImageFromUrl() {
        String s = weChatUtil.addThumbMedia("http://pic-oss.songyuankun.top/001988c1-e3a8-4f65-8d4a-2dbf72f3ab98.jpg");
        System.out.println(s);
    }
    @Test
    public void replaceUrl2WeChatUrl() {
        String s = weChatUtil.replaceUrl2WeChatUrl("<p><strong>众所周知，吉他是一种普遍能够被广大群众接受的乐器，从其选购的价格上也可以体现，\n" +
                "因为吉他的价格从几十元到上万元不等，任何阶层的朋友都可以购买一把自己能力范围内中意的吉他娱乐。\n" +
                "今天，小编主要向大家推荐市面上千元左右，性价比较为出色的吉他。</strong></p>\n" +
                "<h1 id=\"-saga-sf700c-\">一：Saga萨伽SF700C（云杉面板、玫瑰木指板、沙比利背侧）</h1>\n" +
                "<p><img src=\"http://pic-oss.songyuankun.top/25b51e8c-01e2-4668-abdf-eee4e5fa2ebc.jpg\" alt=\"9a90d8ee1addda5a1b2c826525d13048.jpg\">\n" +
                "这款琴是入门单板圈里口碑很好的一款吉他，配置是入门面板琴的标配。标准D型琴体的吉他，\n" +
                "低音浑厚，声音层次感分明，音色清亮，适合扫弦等节奏明快歌曲，市场参考价980元左右！</p>\n" +
                "<h1 id=\"-yairi-d950-\">二：Yairi雅伊利D950（云杉单板，背侧板采用奥古曼木合板）</h1>\n" +
                "<p><img src=\"http://pic-oss.songyuankun.top/ec254ece-f04c-4311-ad05-caeeea722d93.jpg\" alt=\"7078c5cbce255fe56528319c52b9ffee.jpg\">\n" +
                "针对初学者推出的面单吉他，搭配的是达达里奥的琴弦，做工精致稳定，比较简洁，简单的黑色包边，\n" +
                "如果觉得琴弦硬，换一套伊利克斯的琴弦会好弹点。市场价格990左右！</p>\n" +
                "<h1 id=\"-gd100s-\">三：三益GD100s（云杉面板、玫瑰木指板、沙比利背侧）</h1>\n" +
                "<p><img src=\"http://pic-oss.songyuankun.top/e909ab0c-919f-4a23-a521-2be6811626d6.jpg\" alt=\"76cd42ebd81e07666babf8a721f039c1.jpg\">\n" +
                "国际上较为知名的品牌，早些年工厂曾为很多品牌吉他代工。整体而言，用料足，音色比较饱满均衡，弹久了开音后音色更加稳定。从外形上看，琴身的胶合抛光等处理的很到位，手感很好，比较有特色的是流线型的琴头，显得前卫。市场价1280元左右。</p>\n" +
                "<h1 id=\"-fg800-\">四：雅马哈入门面单 FG800（云杉木面板，玫瑰木指板）</h1>\n" +
                "<p><img src=\"http://pic-oss.songyuankun.top/d28a5497-92b6-4153-8e48-950544064558.jpg\" alt=\"1c9c040cefee6ae12c214daa18b04193.jpg\">\n" +
                "这是雅马哈FG系列的一款入门单板琴，秉持雅马哈一贯追求的精细做工，音色偏清亮，共鸣宽广，可演奏大多风格的音乐。市场参考价1590元左右。</p>\n" +
                "<h1 id=\"-dd220-\">五：鸽子DD220（云杉单板，沙比利背侧合板）</h1>\n" +
                "<p><img src=\"http://pic-oss.songyuankun.top/5ff8e92a-4338-4fc3-9aee-d2ed8b618f7f.jpg\" alt=\"f37415d9c0850fc0c07116d7964e1fa4.jpg\">\n" +
                "这款吉他设计风格比较朴素，宜家风，青年用户居多，价格在1600元左右，手感不错，</p>\n" +
                "<p>高中低音相对较为均衡。</p>\n");
        System.out.println(s);
    }
}