package com.ezen.demo.upload;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UploadMapper {

	int upload_attach(Map<String, Object> map);

	int upload_upload(Upload upload);
	
	List<Map<String, Object>> getList();

	String getFname(int num);

	List<Map<String, Object>> getUpload(int num);

	int removedatt(int num);

	int removedup(int num);


}
