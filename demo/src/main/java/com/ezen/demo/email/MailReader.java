package com.ezen.demo.email;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeUtility;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailReader
{
   String pop3Host = "pop.gmail.com";
   String userName = "*******@gmail.com";
   String password = "yrpfhwrlqqqzyyob";

   public void readEmail()
   {
       //Set properties
       Properties props = new Properties();
       props.put("mail.store.protocol", "pop3");
       props.put("mail.pop3.host", pop3Host);
       props.put("mail.pop3.port", "995");
       props.put("mail.pop3.starttls.enable", "true");
    
       // Get the Session object.
       Session session = Session.getInstance(props);
    
       try {
           //Create the POP3 store object and connect to the pop store.
      Store store = session.getStore("pop3s");
      store.connect(pop3Host, userName, password);
    
      //폴더 이름 확인하기
      Folder root = store.getDefaultFolder();
      Folder[] folders = root.list();
      for(int i=0;i<folders.length;i++) {
         log.info("{}.{}", i+1, folders[i].getFullName());  // INBOX
      }

      //Create the folder object and open it in your mailbox.
      Folder emailFolder = store.getFolder("INBOX");
      
      if (emailFolder == null) {
            throw new Exception("Invalid folder");
        }
        // try to open read/write and if that fails try read-only
        try {
           emailFolder.open(Folder.READ_WRITE);
           log.info("읽고 쓰기 모드");
        } catch (MessagingException ex) {
           emailFolder.open(Folder.READ_ONLY);
           log.info("읽기 모드");
        }
    
        int totalCnt = emailFolder.getMessageCount();
        
        log.info("총 메일 수 : {}", totalCnt);
        
      //Retrieve the messages from the folder object.
      Message[] messages = emailFolder.getMessages();
      log.info("가져온 메일의 수 : {}", messages.length);
    
      //Iterate the messages
      for (int i=0; i < totalCnt; i++) 
      {
         if((i%10) != 0) continue;
         
         //Message message = emailFolder.getMessage(i);
         Message message = messages[i];
         /*
         Calendar cal = Calendar.getInstance();
         cal.set(2022, 0,1);
         Date date = cal.getTime();
         if(message.getSentDate().before(date)) continue;
         */
         Address[] toAddress = 
                message.getRecipients(Message.RecipientType.TO);
            log.info("---------------------------------");  
            log.info("Details of Email Message " + (i + 1) + " :");
            
            String subject = message.getSubject();
            //제목의 한글이 깨지면 프로젝트 > 마우스 우측 > Properties > Resources > Text File Encoding 항목 설정 변경
           
            log.info("Subject: {}", subject);  
           
            //깨진 코드를 다시 인코딩 하는 과정
            String from = MimeUtility.decodeText(message.getFrom()[0].toString());
            log.info("From: {}", from);  
    
            //Iterate recipients 
            for(int j = 0; j < toAddress.length; j++){
               String to = MimeUtility.decodeText(toAddress[j].toString());
               log.info("To: {}", to);
            }
           
            log.info("보낸 날짜 : {}", message.getSentDate());
           
            log.info("Conetnt-Type : {}", message.getContentType());
           
            try {  // 텍스트 메시지인 경우
               String contents = (String)message.getContent();
              log.info("텍스트 메시지={}",contents);
              continue;
           }catch(ClassCastException cce) {
              
           }
           
           //Iterate multiparts
           Multipart multipart = (Multipart) message.getContent();
           
           for(int k = 0; k < multipart.getCount(); k++){

              BodyPart bodyPart = multipart.getBodyPart(k);
              
              String bodyContentType = bodyPart.getContentType();
              log.info("바디파트 content-type : {}", bodyContentType);
              
              if (bodyPart.isMimeType("text/*")) //멀티파트 안에 있는 텍스트 파트
              {
            	 //텍스트 바디를 가져온다.
                 String strContent = bodyPart.getContent().toString();
                 log.info("텍스트 바디 : {}", strContent);
                 continue;
              }
              else
              {
                 String fname = bodyPart.getFileName();
                 if(fname==null || fname.equals("")) continue;
                 
                 fname = MimeUtility.decodeText(fname);
                 log.info("첨부파일 이름 : {}", fname);
                 
                 //메모리를 사용하여 성능을 높였다(Buffered) 가공스트림, 노드스트림으로 나뉜다.
                 BufferedInputStream bin = new BufferedInputStream(bodyPart.getInputStream());
                 byte[] buf = new byte[1024*10];
                 
                 BufferedOutputStream bout = null;
                 try {
                    bout = new BufferedOutputStream(new FileOutputStream(fname));
                 }catch(FileNotFoundException fne) {
                    continue;
                 }
                 int read = 0;
                 //buf라는 공간에서 계속 읽어 드린다.
                 while((read=bin.read(buf, 0, buf.length)) != -1)
                 {
                	// buf에서 bout로 출력한다.
                    bout.write(buf, 0, read);
                 }
                 bout.close();
                 bin.close();
                 log.info("파일 저장 성공({})", fname);
              }

             log.info("\n");  
            } //outer for() */

         }
    
         //close the folder and store objects
         emailFolder.close(false);
         store.close();
      } catch (NoSuchProviderException e) {
         e.printStackTrace();
      } catch (MessagingException e){
         e.printStackTrace();
      } catch (Exception e) {
             e.printStackTrace();
      }
    
   }
   
   public void deleteMail(Message message)
   {
      try {
         int msgNum = message.getMessageNumber();
         message.setFlag(Flags.Flag.DELETED, true);
         log.info("메일 삭제 성공 :{}", msgNum);
         
      } catch (MessagingException e) {
         e.printStackTrace();
      }
   }
   
   
//   ============================삭제===================================
   public boolean removeMail(int num) 
   {
       //Set properties
       Properties props = new Properties();
       props.put("mail.store.protocol", "pop3");
       props.put("mail.pop3.host", pop3Host);
       props.put("mail.pop3.port", "995");
       props.put("mail.pop3.starttls.enable", "true");
    
       // Get the Session object.
       Session session = Session.getInstance(props);
    
       try {
           //Create the POP3 store object and connect to the pop store.
         Store store = session.getStore("pop3s");
         store.connect(pop3Host, userName, password);

         Folder emailFolder = store.getFolder("INBOX");

           try {
              emailFolder.open(Folder.READ_WRITE);
              log.info("읽고 쓰기 모드");
           } catch (MessagingException ex) {
              emailFolder.open(Folder.READ_ONLY);
              log.info("읽기 모드");
           }

         Message[] messages = emailFolder.getMessages();
         
         messages[num].setFlag(Flags.Flag.DELETED, true);
         log.info("메일 삭제 성공 :{}", num);
         
         return true;
       }catch(Exception ex) {
          ex.printStackTrace();
       }
      return false;
   }
   
}