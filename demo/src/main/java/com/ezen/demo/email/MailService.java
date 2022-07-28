package com.ezen.demo.email;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ezen.demo.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailService {
	
	@Autowired
	private JavaMailSender sender;

	private String getRandomText() {
		UUID randomUUID = UUID.randomUUID();
		return randomUUID.toString().replaceAll("-", "");
	}
	
	public Boolean sendSimpleText() {
		  //송신자가 여러명일 수 있으니까 List
	      List<String> receivers = new ArrayList<>();
	      receivers.add("youngcher1@naver.com");

	      String[] arrReceiver = (String[])receivers.toArray(new String[receivers.size()]);
	      
	      //SimpleMailMessage를 만들어줌
	      SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
	      
	      //수신자
	      simpleMailMessage.setTo(arrReceiver);
	      //메일 제목
	      simpleMailMessage.setSubject("Spring Boot Mail Test");
	      //메일 내용
	      simpleMailMessage.setText("스프링에서 메일 보내기 테스트");
	      
	      
	      sender.send(simpleMailMessage);
	      
	      return true;
	}
	
	public boolean sendMimeMessage()
	   {
	      MimeMessage mimeMessage = sender.createMimeMessage();

	      try {
	         InternetAddress[] addressTo = new InternetAddress[1];
	         addressTo[0] = new InternetAddress("****@naver.com");

	         mimeMessage.setRecipients(Message.RecipientType.TO, addressTo);

	         mimeMessage.setSubject("마임 메시지 테스트");
	         String random = getRandomText();
	         mimeMessage.setContent("<a href='http://localhost/mail/auth/"
	         		+ random
	         		+ "'>매일인증</a>", "text/html;charset=utf-8");
	         
	         sender.send(mimeMessage);
	         return true;
	      } catch (MessagingException e) {
	         log.error("에러={}", e);
	      }

	      return false;
	   }

	   public boolean sendAttachMail()
	   {
		   //mimeMessage는 message의 폼
	      MimeMessage mimeMessage = sender.createMimeMessage();
	      
	      Multipart multipart = new MimeMultipart();

	      try {
	         InternetAddress[] addressTo = new InternetAddress[1];
	         addressTo[0] = new InternetAddress("****@naver.com");

	         mimeMessage.setRecipients(Message.RecipientType.TO, addressTo);
	         
	         // message 제목
	         mimeMessage.setSubject("마임 메시지(HTML) 테스트");
	         
	         // Fill the message
	         BodyPart messageBodyPart = new MimeBodyPart();
	         
	         // message 내용
	         messageBodyPart.setContent("<a href='http://localhost/mail/auth/abc123'>메일주소 인증</a>", "text/html;charset=utf-8");
	         
	         multipart.addBodyPart(messageBodyPart);
	          
	         // Part two is attachment
	         // message 첨부파일
	         messageBodyPart = new MimeBodyPart();
	         File file = new File("C:/Users/young/OneDrive/바탕 화면/이미지/첨부.txt");
	         FileDataSource fds = new FileDataSource(file);
	         messageBodyPart.setDataHandler(new DataHandler(fds));
	         
	         String fileName = fds.getName();
	         messageBodyPart.setFileName(fileName);
	         
	         multipart.addBodyPart(messageBodyPart);
	          
	         // Put parts in message
	         mimeMessage.setContent(multipart);
	         
	         sender.send(mimeMessage);
	         
	         return true;
	      }catch(Exception ex) {
	         log.error("에러={}", ex);
	      }
	      return false;
	   }

	public boolean sendMSG(String title, String contents, List<String> fileNames) {

		MimeMessage mimeMessage = sender.createMimeMessage();
	      
	      Multipart multipart = new MimeMultipart();

	      try {
	         InternetAddress[] addressTo = new InternetAddress[1];
//	         addressTo[0] = new InternetAddress("*****@naver.com");
	         addressTo[0] = new InternetAddress("****@gmail.com");
	         
	         mimeMessage.setRecipients(Message.RecipientType.TO, addressTo);
	         
	         // message 제목
	         mimeMessage.setSubject(title);
	         
	         // Fill the message
	         BodyPart messageBodyPart = new MimeBodyPart();
	         
	         // message 내용
	         messageBodyPart.setContent(contents, "text/html;charset=utf-8");
	         
	         multipart.addBodyPart(messageBodyPart);
	          
	         // Part two is attachment
	         // message 첨부파일
	         for(int i=0; i<fileNames.size(); i++) {
	        	 messageBodyPart = new MimeBodyPart();
		         File file = new File("C:/Users/young/git/SpringBoot/demo/src/main/webapp/WEB-INF/files/"+fileNames.get(i));
		         FileDataSource fds = new FileDataSource(file);
		         messageBodyPart.setDataHandler(new DataHandler(fds));
	         
		         String fileName2 = fds.getName();
		         messageBodyPart.setFileName(fileName2);
		         
		         multipart.addBodyPart(messageBodyPart);
	         
	         }
	         // Put parts in message
	         mimeMessage.setContent(multipart);
	         
	         sender.send(mimeMessage);
	         
	         return true;
	      }catch(Exception ex) {
	         log.error("에러={}", ex);
	      }
	      return false;
		
	}

	public void logincheck(HttpSession session) {
		
	}

	public User getUser(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	public String checkmail(String email) {
		String random = getRandomText();
		
		MimeMessage mimeMessage = sender.createMimeMessage();

	      try {
	         InternetAddress[] addressTo = new InternetAddress[1];
	         addressTo[0] = new InternetAddress("youngcher1@naver.com");

	         mimeMessage.setRecipients(Message.RecipientType.TO, addressTo);

	         mimeMessage.setSubject("마임 메시지 테스트");
	         mimeMessage.setContent(random,"text/html;charset=utf-8");
	         
	         sender.send(mimeMessage);
	         return random;
	      } catch (MessagingException e) {
	         log.error("에러={}", e);
	      }
		
		return null;
	}

	   
	   
	   
}
