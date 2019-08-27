package com.songyuankun.wechat.controller.admin;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.OssResource;
import com.songyuankun.wechat.repository.OssResourceRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author songyuankun
 */
@Api(tags = "上传接口")
@RestController
@RequestMapping("admin/upload")
@Slf4j
public class UploadController {
    @Value("${qiniu.ak}")
    private String accessKey;
    @Value("${qiniu.sk}")
    private String secretKey;
    @Value("${qiniu.bucket}")
    private String bucket;
    @Value("${qiniu.cdn_host}")
    private String cdnHost;

    private OssResourceRepository ossResourceRepository;

    @PostMapping(value = "file")
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public com.songyuankun.wechat.common.Response<Map<String, Object>> upload(@ApiParam(name = "文件名") @RequestParam("fileName") String fileName, @ApiParam(name = "文件") @RequestParam("file") MultipartFile file) {
        Configuration cfg = new Configuration(Zone.zone0());

        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        Response response = null;
        try {
            response = uploadManager.put(file.getInputStream(), file.getOriginalFilename(), upToken, new StringMap(), null);
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
            result.put("name", fileName);

            OssResource ossResource = new OssResource(fileName, url, putRet.key);
            ossResourceRepository.save(ossResource);
            return ResponseUtils.success(result);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return ResponseUtils.error("上传失败");
    }
}
