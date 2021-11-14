package it.it114.SimpleMailService.controller;


import it.it114.SimpleMailService.Captch.CaptchaValidator;
import it.it114.SimpleMailService.model.Mail;
import it.it114.SimpleMailService.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
//        this.mailService.sendMail("Virag","19it114@charusat.edu.in","Hello, how are you?");
//        System.out.println("Email sent successfully");
//    }

    @RequestMapping("/sendmail")
    public String sendMail(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("message") String message,@RequestParam("g-recaptcha-response") String captcha) {
        if (validator.isValidCaptcha(captcha)) {
            try {
                mailService.sendMail(name,email,message);
            } catch (Exception e) {
                System.out.println(e);
                return "Something went wrong!!!";
            }
            System.out.println("The message has been sent!!!");
        } else {
            return "Please validate captcha!!!";
        }
        return "redirect:/#contact";
    }

    @GetMapping(value = "/download")
    public ResponseEntity<Object> downloadFile() throws IOException
    {
        String filename = "19IT114_Virag_Resume.pdf";
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }
}
