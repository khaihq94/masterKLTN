package main;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import crawlData.CrawlData;
import plagiarism.CheckPlagiarism;

public class Main {
	
	static String urlMySQL = "jdbc:mysql://localhost/testcrawldata?useUnicode=true&characterEncoding=utf8";
	static String user = "root";
	static String password = "";
	
	static String[] columns = new String[]{"title", "link"};
	static String currentURL = "http://tuoitre.vn/tin/chinh-tri-xa-hoi";

	public static void main(String[] args) {
//		MySQLAccess mySQLAccess = new MySQLAccess();
		CrawlData crawlData = new CrawlData();
		CheckPlagiarism checkPlagiarism = new CheckPlagiarism();
		
		/*if(!mySQLAccess.connect(urlMySQL, user, password)){
			System.out.println("---Fail---");
			return;
		}*/
//		mySQLAccess.close();
//		crawlData.getLinkItemsTuoiTre(currentURL);
//		crawlData.getLinkItemsTuoiTre("http://tuoitre.vn/tin/song-khoe");
		
		crawlData.getContent(currentURL);
//		File file = crawlData.createInputFile("test");
//		crawlData.writeContentIntoInputFile(file);
//		crawlData.deleteInputFile(file);
//		crawlData.runTonkenizer();
//		crawlData.isTokenizeDone(10000);
//		crawlData.deleteInput();
//		checkPlagiarism.checkPlag1();
//		checkPlagiarism.readStopWordsFile("vietnamese-stopwords.txt");
		/*String line = null;
        String content = "";
        try {
            // FileReader đọc file theo Encode mặc định
            FileReader fileReader = 
                new FileReader("D:\\DeCuongMoi\\KLTN\\TestDataCrawling\\Application\\vnTokenizer\\output\\http...tuoitre.vn.tin.chinh-tri-xa-hoi.20161031.boi-ghe-ra-tam-ho-phu-ninh-hai-hoc-sinh-lop-8-chet-duoi.1210975.html.txt");

            // Tạo bufferedReader để duyệt qua từng dòng trong file
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
            	content = content + line;
            }   

            // Đóng buffered
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            
        }
        catch(IOException ex) {
            
        }
		checkPlagiarism.separateWords(content);*/
	}
}
