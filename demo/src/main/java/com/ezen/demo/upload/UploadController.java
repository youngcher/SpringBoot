package com.ezen.demo.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/files")
public class UploadController 
{
   @Autowired  
   ResourceLoader resourceLoader;  //파일 로드하는 Spring 기능

   @Autowired
   UploadMapper dao;
   
   @Autowired
   UploadService svc;
   
   
   @GetMapping("/upload")
   public String getForm() {
      return "/upload/upload_form";
   }
   
   @PostMapping("/upload")
   public String upload(@RequestParam("files")MultipartFile[] mfiles, //여러 파일의 정보를 담는 MultipartFile[] 도 Spring에서 지원함
         HttpServletRequest request, Upload upload, Model model) {
      
	   ServletContext context = request.getServletContext();
       String savePath = context.getRealPath("/WEB-INF/files");
       upload.setFpath(savePath);
       
       List<String> attlist = new ArrayList<String>();
      /* static/upload 디렉토리에 업로드하려면, 아래처럼 절대경로를 구하여 사용하면 된다
      * Resource resource = resourceLoader.getResource("classpath:/static");
      * String absolutePath = resource.getFile().getAbsolutePath();
      */ 
      try {
    	 //List<String> list = new ArrayList<>();
    	 
         for(int i=0;i<mfiles.length;i++) {
        	 String line[] = mfiles[i].getOriginalFilename().split(".jpg");
             String fakeName = line[0]+"_"+System.nanoTime()+".jpg";
             //경로 = savePath , 파일명 = fakeName
             //attlist.add(mfiles[i].getOriginalFilename());
            attlist.add(fakeName);
             mfiles[i].transferTo(
              new File(savePath+"/"+fakeName));
             /* MultipartFile 주요 메소드
             String cType = mfiles[i].getContentType();
             String pName = mfiles[i].getName();
             Resource res = mfiles[i].getResource();
             long fSize = mfiles[i].getSize();
             boolean empty = mfiles[i].isEmpty();
             */
//             String msg = String.format("파일명:%s, 사이즈:%d \t", fakeName,mfiles[i].getSize());
//             list.add(msg);
          
         }
//         String msg = String.format("파일(%d)개 저장성공(작성자:%s)", mfiles.length, author);
         
//         return msg + mfiles[0].getOriginalFilename();
//         return list.toString();
         upload.setFname(attlist);
         
         if(upload.getWriter()==null) {
        	 boolean addfiles = svc.addfiles(upload);
        	 System.out.println(addfiles);
        	 return "/upload/detail";
         }
         
         boolean result = svc.upload(upload);
         System.out.println(result);
         
         //List<String> list = dao.getList();
         List<Upload> list = svc.getList();
         
         model.addAttribute("list", list);
         return "upload/uploadList";
         
      } catch (Exception e) {
         e.printStackTrace();
         return "파일 저장 실패:";
      }
   }
   
   @GetMapping("/download/{filename}")
   public ResponseEntity<Resource> download(
         HttpServletRequest request,
         @PathVariable String filename){
      Resource resource = resourceLoader.getResource("WEB-INF/files/"+filename);
      System.out.println("파일명:"+resource.getFilename());
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
 
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
   }

   @GetMapping("/download/{num}/{filename}")
   public ResponseEntity<Resource> fileDownload(HttpServletRequest request, @PathVariable int num, 
		   @PathVariable String filename){
	   Resource resource = resourceLoader.getResource("WEB-INF/files/"+filename);
	   
	   String contentType = null;
	   try {
		   contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
   
	   if(contentType == null) {
		   contentType = "application/octet-stream";
	   }

	   return ResponseEntity.ok()
               .contentType(MediaType.parseMediaType(contentType))
               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
               .body(resource);
   
   }
   
   @GetMapping("/detail/{num}")
   public String detail(@PathVariable int num, Model model){
	   Upload vo = svc.getUpload(num);
	   model.addAttribute("vo", vo);
	   return "/upload/detail";
   }
   
   @GetMapping("/remove/{num}")
   @ResponseBody
   public Map<String,Object> remove(@PathVariable int num) {
	   Map<String,Object> map = new HashMap<String, Object>();
	   boolean result = svc.removed(num);
	   map.put("result", result);
	   return map;
   }
   
   @GetMapping("/allremove/{num}")
   @ResponseBody
   public Map<String,Object> allremove(HttpServletRequest request, @PathVariable int num) {
	   Upload vo = svc.getUpload(num);
	   Map<String,Object> map = new HashMap<String, Object>();
	   List<String> list = vo.getFname();
	   ServletContext context = request.getServletContext();
       String savePath = context.getRealPath("/WEB-INF/files");
	   int fileRemove = 0;
	   
	   boolean result = false;
		try {
			result = svc.allremoved(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	   for(int i=0; i<list.size(); i++) {
		   File file = new File(savePath+"/"+list.get(i));
		   if(file.delete()) {
			 fileRemove += 1;  
		   }
	   }
	   boolean fileRemoveComplete = (fileRemove==list.size());
	   
	   map.put("result", result && fileRemoveComplete);
	   return map;
   }
   
   @PostMapping("/addfiles")
   @ResponseBody
   public Map<String,Object> addfiles(@RequestParam("files")MultipartFile[] mfiles, //여러 파일의 정보를 담는 MultipartFile[] 도 Spring에서 지원함
	         HttpServletRequest request, Upload upload, Model model){
	   Map<String,Object> map = new HashMap<String, Object>();
	   ServletContext context = request.getServletContext();
       String savePath = context.getRealPath("/WEB-INF/files");
       upload.setFpath(savePath);
		
       List<String> attlist = new ArrayList<String>();
      try {
    	 
         for(int i=0;i<mfiles.length;i++) {
        	 String line[] = mfiles[i].getOriginalFilename().split(".jpg");
             String fakeName = line[0]+"_"+System.nanoTime()+".jpg";
            attlist.add(fakeName);
             mfiles[i].transferTo(
              new File(savePath+"/"+fakeName));
         }
         
         upload.setFname(attlist);
         boolean addfiles = svc.addfiles(upload);
         map.put("result", addfiles);
       	 return map;
       	 
      } catch (Exception e) {
    	  e.printStackTrace();
     	   map.put("result", false);
    	  return map;
      }
   }
}
