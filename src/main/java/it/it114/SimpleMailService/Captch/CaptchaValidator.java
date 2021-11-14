package it.it114.SimpleMailService.Captch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CaptchaValidator {

    @Autowired
    private RestTemplate restTemplate;

    public boolean isValidCaptcha(String captcha) {

        String url= "https://www.google.com/recaptcha/api/siteverify";
        System.out.println("Captcha Verified Successfully");
        String params="?secret=6LdTNTIdAAAAACTXCeMtG9JH0DJ3xIWDx9uwZcOg&response="+captcha;
        String completeUrl=url+params;
        CaptchaResponse resp = restTemplate.postForObject(completeUrl, null, CaptchaResponse.class);
        System.out.println(resp.isSuccess());
        return resp.isSuccess();
    }
}
