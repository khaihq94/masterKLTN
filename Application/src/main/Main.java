package main;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import crawlData.*;
import plagiarism.CheckPlagiarism;
import tokenize.Tokenize;

public class Main {
	
	/*static String urlMySQL = "jdbc:mysql://localhost/testcrawldata?useUnicode=true&characterEncoding=utf8";
	static String user = "root";
	static String password = "";*/
	
//	static String[] columns = new String[]{"title", "link"};
	static String currentURL = "http://tuoitre.vn/tin/chinh-tri-xa-hoi";

	public static void main(String[] args) {
//		MySQLAccess mySQLAccess = new MySQLAccess();
		CrawlTuoiTre crawlTuoiTre = new CrawlTuoiTre();
		CheckPlagiarism checkPlagiarism = new CheckPlagiarism();
		Tokenize tokenize = new Tokenize();
		String[] tokenizedContent;
		
		/*if(!mySQLAccess.connect(urlMySQL, user, password)){
			System.out.println("---Fail---");
			return;
		}*/
		
		/*//Lấy các link con từ link chủ đề
		ArrayList<String> links = crawlTuoiTre.getLinkItemsTuoiTre();
		ArrayList<String> sentences = new ArrayList<>();
		ArrayList<String> words = new ArrayList<>();
		String content;
		//Duyệt qua từng link con
		for(String link : links){
			//Lấy nội dung của link con
			content = crawlTuoiTre.getContentTuoitreOnline(link);
			//Lưu nội dung vào DB
			
			//Tokenize cho nội dung
			content = tokenize.tokenize(content);
			//Dùng đoạn văn đã tokenize tách thành từng câu
			sentences = checkPlagiarism.seperateSentences(content);
			//Dùng đạon văn đã tokenize tách thành từng từ và loại bỏ stopword
			words = checkPlagiarism.seperateWords(content);
		}
		//Xóa hết link con trong mảng
		links.clear();
		//Xóa hết câu trong mảng
		sentences.clear();
		//Xóa hết từ trong mảng
		words.clear();*/
		
	}
}
