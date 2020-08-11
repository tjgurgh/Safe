package org.board.Android;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class FolderGet {
	
	private static String photo_permission = "jpg,jpeg,png,gif,swf,bmp"; 


	public static ArrayList<HashMap<String, Object>> getPhotoListFromZipDirectory(String strPhotoSavedPath) {

	  ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

	  // ���� ��ü ����
	  File dirFile = new File(strPhotoSavedPath);

	  // ���� �����ϰ� ���丮�� ���
	  if (dirFile.exists() && dirFile.isDirectory()) {
	   File[] fileList = dirFile.listFiles();

	   for (File tempFile : fileList) {
	    // ������ ���, ����ũ�Ⱑ 0 �̻��� ���
	    if (tempFile.isFile() && tempFile.length() > 0) {
	     // ���� ���
	     String tempPath = tempFile.getParent();
	     // ���ϸ�(Ȯ���� ����)
	     String fileFullName = tempFile.getName();
	     // ���� Ȯ����
	     String onlyFileExt = (fileFullName.substring(fileFullName.lastIndexOf(".") + 1)).toLowerCase();
	     // ���ϸ� (Ȯ���� ����)
	     String onlyFileName = fileFullName.toLowerCase().substring(0, fileFullName.lastIndexOf("."));

	     // ���� Ȯ���� üũ
	     if (photo_permission.contains(onlyFileExt)) {

	      // �������� ����
	      HashMap<String, Object> photo = new HashMap<String, Object>();
	      

	      //���ϴµ����͸� map�� ����ָ� �ȴ�. ���ڴ� Ȯ���� ���� ���ϳ��� �� ����Ȯ���� �� ����־���.

	      photo.put("filename", onlyFileName);
	      photo.put("fileext", onlyFileExt);

	      list.add(photo);
	     }
	    }
	   }
	 

	 }
	  return list;
	 }
}
