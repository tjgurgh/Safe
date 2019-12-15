package org.board.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.board.domain.BoardAttachVO;
import org.board.mapper.BoardAttachMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FileCheckTask {
	
	//어제 날짜로 보관되는 모든 첨부파일 목록 가져오는 용도
	@Setter(onMethod_= {@Autowired})
	private BoardAttachMapper attachMapper;
	
	//DB에서 어제 사용된 파일 목록을 얻어오고 파일 목록에서 DB에 없는 파일을 찾아 없는 파일들을 삭제
	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -1);
		String str = sdf.format(cal.getTime());
		
		return str.replace("-", File.separator);
	}
	
	
	@Scheduled(cron="0 0 2 * * *") // 동작하는 주기를 제어 매일 새벽 2시에 동작
	public void checkFiles() throws Exception{
		log.warn("File Check Task run....................");
		log.warn(new Date());
		
		List<BoardAttachVO> fileList = attachMapper.getOldFiles();
		List<Path> fileListPaths = fileList.stream().map(vo ->
		
		Paths.get("c:\\upload", vo.getUploadPath(), "s_" + vo.getUuid() + "_" + vo.getFileName()))
				.collect(Collectors.toList());
		
		fileList.stream().filter(vo -> vo.isFileType() == true)
			.map(vo -> Paths.get("c:\\upload", vo.getUploadPath(), "s_" + vo.getUuid() + "_" + vo.getFileName()))
			.forEach(p -> fileListPaths.add(p));
		
		log.warn("====================================");
		
		fileListPaths.forEach(p -> log.warn(p));
		
		File targetDir = Paths.get("c:\\upload", getFolderYesterDay()).toFile();
		
		//파일들 목록에서 DB에 없는 파일을 찾아서 목록으로 준비하고 삭제
		File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);
		
		log.warn("------------------------------------");
		for(File file : removeFiles) {
			log.warn(file.getAbsolutePath());
			file.delete();
		}
	}
}
