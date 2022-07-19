package com.ezen.demo.upload;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UploadService {

	@Autowired
	private UploadMapper dao;
	
	public boolean upload(Upload upload) {
		
		dao.upload_upload(upload);
		int pnum = upload.getNum();
		
		List<String> attlist = upload.getFname();
		
		int totalSuccess = 0;
		for(int i=0; i<attlist.size(); i++) {
			Map<String, Object> fmap = new HashMap<>();
			fmap.put("pnum", Integer.valueOf(pnum));
			fmap.put("fname", attlist.get(i));
			fmap.put("fpath", upload.getFpath());
			totalSuccess += dao.upload_attach(fmap);
		}
		
		return totalSuccess == attlist.size();
	}

	public List<Upload> getList() {

		List<Map<String,Object>> list = dao.getList();
		List<Upload> uploadList = new ArrayList<>();
		
		
		for(int i=0; i<list.size(); i++) {
			Map<String, Object> map = list.get(i);
			
			int num = ((BigDecimal)map.get("NUM")).intValue();
			String fname = (String)map.get("FNAME");
			
			Upload upload = new Upload();
			upload.setNum(num);
			
			if(uploadList.contains(upload)) {
				uploadList.get(uploadList.size()-1).getFname().add(fname);
			} else {
				upload.setWriter((String) list.get(i).get("WRITER"));
				Date uudate = new Date(((Timestamp)list.get(i).get("UDATE")).getTime());
				upload.setUdate(uudate);
				upload.setComments((String) list.get(i).get("COMMENTS"));
				upload.getFname().add(fname);

				uploadList.add(upload);
			}
			
		}
		
		return uploadList;
	}

	public Upload getUpload(int num) {
		List<Map<String,Object>> list = dao.getUpload(num);
		Upload vo = new Upload();
		
		
		for(int i=0; i<list.size(); i++) {
			Map<String, Object> map = list.get(i);
			
			String fname = (String)map.get("FNAME");
			
			vo.setNum(num);
			vo.setWriter((String) list.get(i).get("WRITER"));
			Date uudate = new Date(((Timestamp)list.get(i).get("UDATE")).getTime());
			vo.setUdate(uudate);
			vo.setComments((String) list.get(i).get("COMMENTS"));
			
			vo.getFname().add(fname);
		}
		
		return vo;
	}

	public boolean removed(int num) {
		int result = dao.removedatt(num);
		return result>0;
	}

	public boolean addfiles(Upload upload) {
		List<String> attlist = upload.getFname();
		Map<String, Object> fmap = new HashMap<>();
		int addfiles = 0;
		for(int i=0; i<attlist.size(); i++) {
			fmap.put("pnum", upload.getNum());
			fmap.put("fname", attlist.get(i));
			fmap.put("fpath", upload.getFpath());
			addfiles = dao.upload_attach(fmap);
			
		}
		return addfiles>0;
	}

	@Transactional(rollbackFor= {Exception.class})
	public boolean allremoved(int num) throws Exception {
		int result = dao.removedatt(num);
		if(result<0) throw new Exception("tb_attach rows delete fail");
		int result2 = dao.removedup(num);
		if(result2<0) throw new Exception("tb_upload rows delete fail");

		return result+result2>=2;
	}
	
	
}
