package org.board.Android;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ImageGetter {
	    
	    /**
	     * �ش� ����� �̹��� ���� ��� ��ȯ 
	     */
	    public static List<File> getImgFileList(String path){        
	         
	        return getImgFileList(new File(path));
	    }    

	    /**
	     * �ش� ����� �̹��� ���� ��� ��ȯ 
	     */
	    public static List<File> getImgFileList(File file){        
	            
	        List<File> resultList = new ArrayList<File>(); //�̹��� ������ ������ ����Ʈ ����
	        
	         //������ �̹��������� ���� ���� ������� �� ����Ʈ ��ȯ.
	        //System.out.println("�������� ����: "+file.exists());
	        if(!file.exists()) return resultList;
	        
	        File[] list = file.listFiles(new FileFilter() { //���ϴ� ���ϸ� �������� ���� FileFilter����
	            
	            String strImgExt = "jpg|jpeg|png|gif|bmp"; //����� �̹���Ÿ��
	            
	            @Override
	            public boolean accept(File pathname) {                            
	                
	                //System.out.println(pathname);
	                boolean chkResult = false;
	                if(pathname.isFile()) {
	                    String ext = pathname.getName().substring(pathname.getName().lastIndexOf(".")+1);
	                    //System.out.println("Ȯ����:"+ext);
	                    chkResult = strImgExt.contains(ext.toLowerCase());        
	                    //System.out.println(chkResult +" "+ext.toLowerCase());
	                } else {
	                    chkResult = true;
	                }
	                return chkResult;
	            }
	        });        
	        
	        for(File f : list) {            
	            if(f.isDirectory()) {
	                //�����̸� �̹�������� �������� ����޼��带 ���ȣ��
	                resultList.addAll(getImgFileList(f));                 
	            }else {            
	                //������ �ƴϰ� �����̸� ����Ʈ(resultList)�� �߰�
	                resultList.add(f);
	            }
	        }            
	        return resultList; 
	    }
	    
	    //Ȯ���ڸ� ������ ���� �̸� �� ���
	    public static String getFileNameNoExt(String filepath){        
	        String fileName = filepath.substring(0,  filepath.lastIndexOf("."));
	        return fileName;
	    }    
	    
	    //���� Ȯ���ڸ� ���
	    public static String getFileExt(String filepath){
	        String ext = filepath.substring(filepath.lastIndexOf(".")+1);
	        return ext;
	    }
	    
	    //�����н����� �̹��� ����� ���
	    //�����ο��� �̹�������(images)�� �߽����� ����θ� ��ȯ.
	    //�н� : ������/images/1-A/1-A_0.jpg 
	    //    => 1-A/1-A_0.jpg
	    public static String getImgSrc(File target){
	        String url = target.getPath().substring(target.getPath().lastIndexOf("images"));
	        return url;
	    }
	    
	    //�̹����� �����ϰ��ִ� ������ �̸� ���. ( ���� �뵵�� �ۼ��� �޼��� �̹Ƿ� �����ص� ���� )
	    public static String getImgDirName(File target){
	        String url = getImgSrc(target);
	        
	        //System.out.println(url);
	        //System.out.println(url.indexOf("\\")+1+"/"+url.lastIndexOf("\\"));
	        
	        int comp = url.lastIndexOf("\\") - (url.indexOf("\\")+1) ;
	        //System.out.println(comp);
	        String dirName ="";
	        if(comp<0) {
	            dirName ="�̹���";
	        }else  {
	            dirName = url.substring(url.indexOf("\\")+1,url.lastIndexOf("\\"));    
	        }        
	        System.out.println("������:"+dirName);
	        
	        return dirName;
	    }
	}