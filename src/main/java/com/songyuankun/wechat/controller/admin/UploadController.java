package com.songyuankun.wechat.controller.admin;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.OssResource;
import com.songyuankun.wechat.repository.OssResourceRepository;
import com.songyuankun.wechat.util.FileUtil;
import com.songyuankun.wechat.util.WeChatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author songyuankun
 */

@RestController
@RequestMapping("admin/upload")
@Slf4j
public class UploadController {
    private final WeChatUtil weChatUtil;
    private final OssResourceRepository ossResourceRepository;
    @Value("${qiniu.ak}")
    private String accessKey;
    @Value("${qiniu.sk}")
    private String secretKey;
    @Value("${qiniu.bucket}")
    private String bucket;
    @Value("${qiniu.cdn_host}")
    private String cdnHost;


    public UploadController(WeChatUtil weChatUtil, OssResourceRepository ossResourceRepository) {
        this.weChatUtil = weChatUtil;
        this.ossResourceRepository = ossResourceRepository;
    }

    @PostMapping(value = "file")
    
    public com.songyuankun.wechat.common.Response<Map<String, Object>> upload(Authentication authentication, @RequestParam("file") MultipartFile file) {
        Configuration cfg = new Configuration(Zone.zone0());

        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        Response response = null;
        String originalFilename = file.getOriginalFilename();
        String suffix = FileUtil.getSuffix(Objects.requireNonNull(originalFilename));
        String uuId = UUID.randomUUID().toString() + "." + suffix;
        try {
            response = uploadManager.put(file.getInputStream(), uuId, upToken, new StringMap(), null);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
        try {
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response != null ? response.bodyString() : "{}", DefaultPutRet.class);
            Map<String, Object> result = new HashMap<>(16);
            String url = cdnHost + "/" + putRet.key;
            result.put("url", url);
            result.put("putRet", putRet);
            result.put("name", originalFilename);

            OssResource ossResource = new OssResource(originalFilename, url, putRet.key);
            DaoCommon.createDao(authentication, ossResource);
            ossResourceRepository.save(ossResource);
            return ResponseUtils.success(result);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }

        return ResponseUtils.error("上传失败");
    }
}
