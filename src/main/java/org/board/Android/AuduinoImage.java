package org.board.Android;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class AuduinoImage { 
      
   static long time = System.currentTimeMillis(); 

   static SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

   static String str = dayTime.format(new Date(time)).replaceAll(":","_").replace(" ","_").replace("-","_");
      
   public static String getSource(String url) throws MalformedURLException, IOException { 
      String output = ""; 
      BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream())); 
      String line; 
      while ((line = br.readLine()) != null) { 
      output += line; 
      } 
      return output; 
      } 
   
      

   public static ArrayList<String> getTypedFile(String text, String typeRegex) { 
         String regex = "\"(http|https)[:][/][/][^<>\"]+[.]" + typeRegex + "\""; 
         Matcher m = Pattern.compile(regex).matcher(text); 
         ArrayList<String> output = new ArrayList<>(); 
         while (m.find()) { 
         output.add(m.group().replace("\"", "")); 
         } 
         return output; 
         } 
   
   

   public static void FileDownload(String address, String saveDir) { 
      try { 
      URL u = new URL(address); 
      FileOutputStream fos = new FileOutputStream(saveDir); 
      InputStream is = u.openStream(); 
      byte[] buf = new byte[1024]; 
      int len = 0; 
      while ((len = is.read(buf)) > 0) { 
      fos.write(buf, 0, len); 
      } 
      fos.close(); 
      is.close(); 
      } catch (IOException e) { 
      e.printStackTrace(); 
      } 
      } 
   
   public static String getWebFileName(String filePath) { 
   String[] parts = filePath.split("[/]"); 
   return parts[parts.length - 1]; 
   } 
   

   public static void getTypedFileDown() throws IOException { 
      String saveDir = "c:\\upload\\Arduino"; 
      /* String downloagURL = "http://192.168.43.10"; */ 
      String downloagURL = "http://192.168.43.10";
      String typeRgx = "(jpg|png|jpeg)";
   
   String source = getSource(downloagURL); 
   ArrayList<String> urls = getTypedFile(source, typeRgx); 
   System.out.println("다운로드를 시작합니다."); 
   int size = urls.size(); 
   if(size == 0) {
      System.out.println("파일이 존재하지 않습니다.");
  
   }else {
   int k = 1; 
   for (String i : urls) { 
      System.out.println(); 
      System.out.println("//--------------------------------------"); 
      System.out.println("//   다운로드 중... : " + i); 
      String save = saveDir + "\\" + str+ i.substring(i.length()-4, i.length());
      System.out.println("//    다운로드 위치 : " + save); 
   FileDownload(i, save); 
   System.out.println("//   다운로드 진행률 : " + (k * 100 / size) + "%"); 
   System.out.println("//--------------------------------------"); 
   System.out.println(); 
   k++; 
   } 
   System.out.println("총 " + urls.size() + "개의 파일을 다운로드하였습니다."); 

   }
   }
}
