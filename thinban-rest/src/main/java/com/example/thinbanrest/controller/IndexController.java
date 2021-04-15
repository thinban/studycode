package com.example.thinbanrest.controller;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.thinbanrest.aop.Decrypt;
import com.example.thinbanrest.aop.Encrypt;
import com.example.thinbanrest.aop.LogIt;
import com.example.thinbanrest.domain.Person;
import com.example.thinbanrest.service.PersonService;
import com.example.thinbanrest.utils.ServletContextHolder;
import com.google.common.net.MediaType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by mbart on 28.02.2016.
 */
@Controller
@Slf4j
@Api(value = "首页", tags = "首页")
public class IndexController {

    @Autowired
    private PersonService personService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation(value = "页面-首页", notes = "页面-首页")
    @GetMapping("/")
    public String showIndex(Model model) {
        List<Person> personList = personService.loadAll();

        model.addAttribute("personList", personList);
        log.error("启动完成error");
        log.warn("启动完成warn");
        log.info("启动完成info");
        log.debug("启动完成debug");
        log.trace("启动完成trace");
        return "index"; // return index.html Template
    }

    @GetMapping("/webSocket")
    @ApiOperation(value = "页面-webSocket", notes = "页面-webSocket")
    public String socket(Model model) {
        return "webSocket";
    }


    @ResponseBody
    @GetMapping("/test")
    @ApiOperation(value = "测试redis", notes = "测试redis")
    public Object test() {
        redisTemplate.opsForValue().set("foo", "bar", 1, TimeUnit.MINUTES);
        return redisTemplate.opsForValue().get("foo");
    }

    @Autowired
    private JavaMailSenderImpl mailSender;

    @ResponseBody
    @GetMapping("/mail")
    @ApiOperation(value = "发送邮件接口", notes = "发送邮件接口")
    public void sendMail() throws Exception {
        //简单邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("yourfrom@163.com");
        simpleMailMessage.setTo("your@qq.com");
        simpleMailMessage.setSubject("BugBugBug");
        simpleMailMessage.setText("一杯茶，一根烟，一个Bug改一天");
        mailSender.send(simpleMailMessage);
//        //复杂邮件
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//        messageHelper.setFrom("jiuyue@163.com");
//        messageHelper.setTo("September@qq.com");
//        messageHelper.setSubject("BugBugBug");
//        messageHelper.setText("一杯茶，一根烟，一个Bug改一天！");
//        messageHelper.addInline("bug.gif", new File("xx/xx/bug.gif"));
//        messageHelper.addAttachment("bug.docx", new File("xx/xx/bug.docx"));
//        mailSender.send(mimeMessage);
    }


    @LogIt(noHeader = false)
    @Decrypt
    @Encrypt
    @ResponseBody
    @PostMapping("/person")
    @ApiOperation(value = "测试加解密", notes = "请求参数:{\"app_id\":\"xykh_wx_mini\",\"sign\":\"free\",\"timestamp\":1605682099012,\"nonce\":\"alert\",\"biz_content\":\"2d9e3a1f7911fc1ee62c9a4d592b71ee040e02cc7edee2f20334cb1bb0c8328346ba3fc9af2f6561db4e85f18e510d37e3e3fede7c69f0c1faf1e1f44d857a12\"},加密见：AppAesUtils.aesDecrypt,签名见：param.validateSign()")
    public Object person(@RequestBody(required = false) Person person) {
        return person;
    }

    @ResponseBody
    @LogIt
    @PostMapping("/person2")
    @ApiOperation(value = "测试加解密", notes = "测试加解密")
    public Object person2(@RequestBody(required = false) Person person) {
        return person;
    }

    @ResponseBody
    @PostMapping("/upload")
    @ApiOperation(value = "文件上传", notes = "文件上传")
    public Object file(@RequestParam("foo") String foo, @RequestParam("f") MultipartFile file) {
        return foo + "~" + file.getOriginalFilename();
    }

    @GetMapping("/download")
    @ApiOperation(value = "文件上传", notes = "文件上传")
    public ResponseEntity<Resource> download(@RequestParam("f") String file) throws Exception {
        String dir = this.getClass().getResource("/").getPath();
        dir = dir.substring(1);
        return ServletContextHolder.renderFile(dir, "application.yml");
    }

}
