package com.zp.controller;

import com.zp.component.MinioComp;
import com.zp.domain.R;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class UploadController {

    @Autowired
    private MinioComp minioComp;

    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file,
                    @RequestParam("fileName") String fileName) {
        minioComp.upload(file, fileName);
        String url = minioComp.getUrl(fileName, 7, TimeUnit.DAYS);
        return R.success(url);
    }

    @GetMapping("/policy")
    public R policy(@RequestParam("fileName") String fileName) {
        Map policy = minioComp.getPolicy(fileName, ZonedDateTime.now().plusMinutes(10));
        return R.success(policy);
    }

    @GetMapping("/uploadUrl")
    public R uploadUrl(@RequestParam("fileName") String fileName) {
        String url = minioComp.getPolicyUrl(fileName, Method.PUT, 2, TimeUnit.MINUTES);
        return R.success(url);
    }

    @GetMapping("/url")
    public R getUrl(@RequestParam("fileName") String fileName) {
        String url = minioComp.getUrl(fileName, 7, TimeUnit.DAYS);
        return R.success(url);
    }

}
