package com.bit2017.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bit2017.mysite.repository.GalleryDao;
import com.bit2017.mysite.vo.GalleryVo;

@Service
public class GalleryService {

	private static final String SAVE_PATH = "/upload";
	private static final String URL = "gallery/";
	
	@Autowired
	private GalleryDao galleryDao;
	
	public List<GalleryVo> getList() {
		
		return galleryDao.getList();
	}
	
	public String insert(String commnets, MultipartFile file){
		
		if(file.isEmpty())
			return null;
		
		GalleryVo vo = new GalleryVo();
		String oriFileName = file.getOriginalFilename(); 
		String extName = oriFileName.substring(oriFileName.lastIndexOf(".")+1, oriFileName.length());
		String saveFileName = GenerateSaveFileName(extName); 
		
		try {
			writeFile(file, saveFileName);
		} catch (IOException e) {
			new RuntimeException( "Upload file:" + e );
		}
		
		vo.setComment(commnets);
		vo.setOriFile(oriFileName);
		vo.setSaveFile(saveFileName);
		
		String url = "";
		
		if (galleryDao.insert(vo)) {
			// 저장 성공
		} else {
			// 저장 실패
		}
			
		return url;
	}

	private void writeFile(MultipartFile file, String saveFileName) throws IOException {
		byte[] data = file.getBytes();
		
		FileOutputStream fos = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
		fos.write(data);
		fos.close();
	}

	private String GenerateSaveFileName(String extName) {
		String fileName = "";
		Calendar calendar = Calendar.getInstance();
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += ("." + extName); 

		return fileName;
	}
	
	
}
