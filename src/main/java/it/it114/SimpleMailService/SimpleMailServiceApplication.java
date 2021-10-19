package it.it114.SimpleMailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@SpringBootApplication
public class SimpleMailServiceApplication implements CommandLineRunner {

	@Autowired
	JavaMailSender javaMailSender;

	public static void main(String[] args) {
	SpringApplication.run(SimpleMailServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Sending an Email with Attachment....");
		sendEmail();
		System.out.println("Email Sent Successfully....");
		System.out.println("Sending an Email with Attachment....");
		sendMMEmail();
		System.out.println("Email Sent Successfully....");
	}

	void sendEmail(){
		SimpleMailMessage simple = new SimpleMailMessage();
		simple.setTo("19it114@charusat.edu.in");
		simple.setSubject("Hello this is a automatic mail generated from Spring Boot!!!!");
		simple.setText("Hello, This mail is regarding the verification of your mail.");
		javaMailSender.send(simple);
	}

	void sendMMEmail() throws MessagingException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(new String[]{"19it114@charusat.edu.in","virag290301@gmail.com"});
		helper.setSubject("Hello this is a automatic mail generated from 19IT114!!!!");
		helper.setText("<h1>Hello, This mail is regarding the verification of your Email.</h1>");
		helper.addAttachment("pom.xml", new ClassPathResource("application.properties"));
		javaMailSender.send(msg);
	}
}
