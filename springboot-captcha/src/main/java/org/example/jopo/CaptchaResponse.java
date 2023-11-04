package org.example.jopo;

import lombok.Data;

@Data
public class CaptchaResponse {
    private String image;
    private String id;

    public CaptchaResponse(String captchaImage, String captchaId) {
        this.image = captchaImage;
        this.id = captchaId;
    }
    public CaptchaResponse(){}
}