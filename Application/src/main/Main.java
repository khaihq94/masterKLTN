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
		
		ArrayList<String> contents = crawlTuoiTre.getContentTuoitreOnline();
		tokenizedContent = tokenize.tokenize(contents);
	}
}
