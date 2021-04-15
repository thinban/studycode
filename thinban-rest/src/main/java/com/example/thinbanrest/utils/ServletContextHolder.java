package com.example.thinbanrest.utils;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class ServletContextHolder {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getResponse();
    }

    public static String getUrl() {
        String uri = getRequest().getRequestURI();
        int len = getRequest().getContextPath().length();
        return uri.substring(len);
    }

    /**
     * 下载文件
     *
     * @param dir
     * @param file
     * @return
     */
    public static ResponseEntity<Resource> renderFile(String dir, String file) {
        try {
            Assert.isTrue(StringUtils.isNotBlank(dir));
            Assert.isTrue(StringUtils.isNotBlank(file));
            ServletContextHolder.getResponse().setHeader("Accept", "application/octet-stream");
            Path path = Paths.get(dir).resolve(file).normalize();
            UrlResource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                String mimeType = ServletContextHolder.getRequest().getServletContext().getMimeType(resource.getFile().getAbsolutePath());
                String contentType = StringUtils.isBlank(mimeType) ? "application/octet-stream" : mimeType;
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename() + "")
                        .body(resource);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
