package it.it114.SimpleMailService.controller;


import it.it114.SimpleMailService.Captch.CaptchaValidator;
import it.it114.SimpleMailService.model.Mail;
import it.it114.SimpleMailService.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class mailcontroller {

    @Autowired
    MailService mailService;

    @Autowired
    CaptchaValidator validator;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/")
    public String hello()
    {
        return "index.html";
    }

//    @GetMapping("/home")
//    public void home()
//    {
//        System.out.println("Sending a mail");
//        this.mailService.sendMail("Ayush","19it127@charusat.edu.in","Hello, how are you?");
//        System.out.println("Email sent successfully");
//    }
    @RequestMapping("/sendmail")
    public String sendMail(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("message") String message,,@RequestParam("g-recaptcha-response") String captcha) {
        if (validator.isValidCaptcha(captcha)) {
            try {
                mailService.sendMail(name,email,message);
            } catch (Exception e) {
                System.out.println(e);
                return "Something went wrong!!!";
            }
            return "The message has been sent!!!";
        } else {
            return "Please validate captcha!!!";
        }
    }


}
