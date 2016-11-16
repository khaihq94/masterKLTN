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
		CrawlNewsZing crawlNewsZing = new CrawlNewsZing();
		CheckPlagiarism checkPlagiarism = new CheckPlagiarism();
		Tokenize tokenize = new Tokenize();
		String[] tokenizedContent;
		
		/*if(!mySQLAccess.connect(urlMySQL, user, password)){
			System.out.println("---Fail---");
			return;
		}*/
		
		/*
		//Lấy các tin tức từ db với mốc ngày hiện tại trừ 3
		
		//Lấy các link con từ link chủ đề
		ArrayList<String> links = crawlTuoiTre.getLinkItemsTuoiTre();
		ArrayList<String> sentences = new ArrayList<>();
		ArrayList<String> words = new ArrayList<>();
		ArrayList<String> content = new ArrayList<>();
		//Duyệt qua từng link con
		for(String link : links){
			//Lấy nội dung của link con
			content = crawlTuoiTre.getContentTuoitreOnline(link);
			//So sánh link của tin vừa lấy với các tin có trong db đã đc lấy ở trên
			
			//Nếu trùng remove content khỏi mảng, bỏ qua các bước dưới
			
			//Nếu không trùng thì thực hiện các bước kiểm tra về nội dung với các tin trong db đã lấy ở trên
			
			
			//Tokenize cho nội dung
			content = tokenize.tokenize(content.get(3));
			//Dùng đoạn văn đã tokenize tách thành từng câu
			sentences = checkPlagiarism.seperateSentences(content);
			//Dùng đoạn văn đã tokenize tách thành từng từ và loại bỏ stopword
			words = checkPlagiarism.seperateWords(content);
			
			//Lấy độ tương đồng giữa 2 văn bản
			
			//Nếu độ tương đồng giữa 2 văn bản nhỏ hơn 0.7 thì lưu tin mới vào db
			
			//Nếu lớn hơn 0.7, xóa tin tức và ko lưu vào db
			
		}
		//Xóa hết link con trong mảng
		links.clear();
		//Xóa hết câu trong mảng
		sentences.clear();
		//Xóa hết từ trong mảng
		words.clear();*/
		
		/*String content1 = tokenize.tokenize("Hôm 10-11, Tổng thống Philippines Rodrigo Duterte và Thủ tướng Malaysia Najib Razak đã cùng hát karaoke tại bữa tiệc chiêu đãi cấp nhà nước ở hội trường khu vực Seri Perdana - Malaysia.");
		String content2 = tokenize.tokenize("Tổng thống Philippines Rodrigo Duterte đã cùng hòa giọng với Thủ tướng Malaysia Najib Razak trong một tiết mục hát karaoke tại bữa quốc yến được tổ chức hôm 10/11 ở Malaysia.");
		
		System.out.println(checkPlagiarism.getSim(content1, content2));*/
		
		// Lấy các link con từ link chủ đề
		ArrayList<String> links = crawlNewsZing.getLinksNewsZing();
		ArrayList<String> sentences = new ArrayList<>();
		ArrayList<String> words = new ArrayList<>();
		ArrayList<String> content = new ArrayList<>();
		// Duyệt qua từng link con
		for (String link : links) {
			// Lấy nội dung của link con
			content = crawlNewsZing.getContentNewsZing(link);
			System.out.println(content.get(3));
		}
	}
}
