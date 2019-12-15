package org.board.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.board.domain.AttachFileDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}
	//�뙆�씪 �뾽濡쒕뱶
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/uploadAjaxAction" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>>
	uploadAjaxPost(MultipartFile[] uploadFile) {
		
		List<AttachFileDTO> list = new ArrayList<>();
		//�뙆�씪 ���옣 寃쎈줈
		 String uploadFolder = "C:\\upload"; 
		/* String uploadFolder = "/usr/local/tomcat9/webapps/upload/"; */
		
		
		String uploadFolderPath = getFolder();
		
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		
		//�뤃�뜑 寃쎈줈 議댁옱�븯吏� �븡�쓣�떆 �뤃�뜑 �깮�꽦
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs(); // yyyy/MM/dd folder
		}
		
		for(MultipartFile multipartFile : uploadFile) {
		
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			// �떎�젣 �뙆�씪�씠由�
			String uploadFileName = multipartFile.getOriginalFilename();
			
			//IE file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			log.info("only file name : " + uploadFileName);
			
			attachDTO.setFileName(uploadFileName);
			// �뙆�씪�씠由� 以묐났 諛⑹�瑜� �쐞�븳 UUID �쟻�슜
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				
				// �씠誘몄� �뙆�씪 ���엯 �솗�씤
				if(checkImageType(saveFile)) {
					attachDTO.setImage(true);
					
					FileOutputStream thumbnail = new FileOutputStream
							(new File(uploadPath, "s_" + uploadFileName));
					
					// �뙆�씪 �씠由꾩씠 s_濡� �떆�옉�븯�뒗 �꽟�꽕�씪 �뙆�씪 ���옣, �썝蹂� �뙆�씪�� 洹몃�濡� ���옣 �꽟�꽕�씪 �뙆�씪�� width, height 100�뵫
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
				
				list.add(attachDTO);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		} //for臾� 醫낅즺
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
		
	// �뙆�씪 寃쎈줈媛� �룷�븿�맂 fileName�쓣 �뙆�씪誘명꽣濡� 諛쏄퀬 byte[] �쟾�넚
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName) {
		log.info("fileName : " + fileName);
		
		  File file = new File("c:\\upload\\" + fileName);
		 
		/* File file = new File("/usr/local/tomcat9/webapps/upload/" + fileName); */
		 
		
		log.info("file : " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 泥⑤� �뙆�씪 �떎�슫濡쒕뱶
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	//User-Agent瑜� �씠�슜�빐 IE/Edge 釉뚮씪�슦���뿉�꽌 �떎�슫濡쒕뱶 臾몄젣 �빐寃�
	public ResponseEntity<Resource> downloadFile
	(@RequestHeader("User-Agent")String userAgent, String fileName) {
		Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
		/*
		 * Resource resource = new
		 * FileSystemResource("/usr/local/tomcat9/webapps/upload" + fileName);
		 */
		
		if(resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		String resourceName = resource.getFilename();
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_")+1);
		HttpHeaders headers = new HttpHeaders();
		
		try {
			String downloadName = null;
			
			if(userAgent.contains("Trident")) {
				log.info("IE browser");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+", "");
			} else if(userAgent.contains("Edge")) {
				log.info("Edge browser");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8");
			} else {
				log.info("Chrome browser");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
			}
			
			log.info("downloadName : " + downloadName);
			
			headers.add("Content-Disposition", "attachment; filename=" + downloadName);
			
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type) {
		log.info("deleteFile : " + fileName);
		File file;
		
		try {
			
			  file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
			 
			/*
			 * file = new File("/usr/local/tomcat9/webapps/upload/" +
			 * URLDecoder.decode(fileName, "UTF-8"));
			 */
			file.delete();
			
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				
				log.info("largeFileName : " + largeFileName);
				
				file = new File(largeFileName);
				file.delete();
			}
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("delete", HttpStatus.OK);
	}
	//�삤�뒛 �궇吏쒖쓽 寃쎈줈瑜� 臾몄옄�뿴濡� �깮�꽦�븳�떎. �깮�꽦�맂 寃쎈줈�뒗 �뤃�뜑 寃쎈줈濡� �닔�젙�맂 �뮘 諛섑솚
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}
	
	// �뾽濡쒕뱶�맂 �뙆�씪�씠 �씠誘몄� �뙆�씪�씤吏� �솗�씤
	private boolean checkImageType(File file) {
		
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		} catch(IOException e	) {
			e.printStackTrace();
		}
		return false;
	}
	

}
