package org.example.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.example.jopo.CaptchaResponse;
import org.example.jopo.CaptchaVerifyParam;
import org.example.jopo.CaptchaVerifyRes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource
    DefaultKaptcha defaultKaptcha;

    private final Map<String,String> captchaIdTextMap = new ConcurrentHashMap<>();

    public CaptchaResponse generateCaptchaImage(String captchaId) {
        // 生成验证码文本
        String text = defaultKaptcha.createText();
        captchaIdTextMap.put(captchaId, text);

        // 生成验证码图片
        BufferedImage image = defaultKaptcha.createImage(text);

        // 将验证码图片转为字节数组
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] captchaBytes = outputStream.toByteArray();
        String captchaBase64 = Base64.getEncoder().encodeToString(captchaBytes);

        // 将验证码文本与图片保存到缓存或数据库中，以便后续验证
        return new CaptchaResponse(captchaBase64, captchaId);
    }


    @ResponseBody
    @GetMapping(value = "/image")
    public CaptchaResponse getCaptchaImage() {
        String captchaId = UUID.randomUUID().toString().replace("-","");
        return generateCaptchaImage(captchaId);
    }


    @ResponseBody
    @PostMapping(value = "/verify")
    public CaptchaVerifyRes verify(
            @RequestBody CaptchaVerifyParam param
    ) {
        String text = this.captchaIdTextMap.get(param.getId());
        CaptchaVerifyRes res = new CaptchaVerifyRes();
        res.setCode(text.equals(param.getCaptcha().toLowerCase())?200:403);
        res.setData(null);
        return res;
    }
}

// TODO: 失败后返回新的img
// TODO：成功后自动删除
// TODO 一段时间没用时自动删除（或者每晚固定时间删除，使用其他缓存模型实现删除）