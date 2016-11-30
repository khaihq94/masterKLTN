package tokenize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import helperMethod.HelperMethod;
import vn.hus.nlp.tokenizer.VietTokenizer;

public class Tokenize {
	
	HelperMethod helper = new HelperMethod();
	
	/*
	 * TODO
	 * Chạy vnTokenizer
	 * 
	 * */
	public void runTonkenizer(){
		String line = "vnTokenizer.bat -i input -o output";
		try {
			Runtime rt = Runtime.getRuntime();
			String path = System.getProperty("user.dir") + "\\vnTokenizer";
			//Bật cmd.exe và chạy lệnh line, sau đó exit
			rt.exec("cmd.exe /c cd \"" + path + "\" & start cmd.exe /k \"" + line + " && exit\"");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public String[] tokenize(ArrayList<String> contents){
		VietTokenizer token = new VietTokenizer();
		String[] tokenizedContent = null;
		for(String content : contents){
			tokenizedContent = token.tokenize(content);
			System.out.println(tokenizedContent);
		}
		return tokenizedContent;
	}
	
	public void tokenize1(ArrayList<String> contents){
		VietTokenizer token = new VietTokenizer();
		String[] tokenizedContent;
		for(String content : contents){
			tokenizedContent = token.tokenize(content);
			System.out.println(helper.arrayToString(tokenizedContent));
		}
	}
	
	public String tokenizeToString(String content) {
		VietTokenizer token = new VietTokenizer();
		String[] tokenizedContent = token.tokenize(content);
		System.out.println(helper.arrayToString(tokenizedContent));
		return helper.arrayToString(tokenizedContent);
	}
	
	public String[] tokenizeToArray(String content){
		VietTokenizer token = new VietTokenizer();
		String[] tokenizedContent = token.tokenize(content);
		System.out.println(helper.arrayToString(tokenizedContent));
		return tokenizedContent;
	}
	
	/*TODO
	 * Hàm kiểm tra xem việc tonkenize hoàn thành chưa
	 * 
	 * */
	public boolean isTokenizeDone(int maximumTimeoutInMilisec){
		boolean done = false;
		long startTime = System.currentTimeMillis();
		long memory = startTime;
		long endTime = startTime + maximumTimeoutInMilisec;
		while(startTime < endTime){
			//Gán lại thời gian mới (thay cho việc tăng thời gian)
			startTime = System.currentTimeMillis();
			//Kiểm tra việc hoàn thành tokenize cứ sau 2.5 giây
			if(startTime - memory == 2500){
				memory = startTime;
				try {
					//Đọc các process đang chạy, kiểm tra xem có cmd hay không
					Process p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
					BufferedReader input =  new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line;
					String pidInfo ="";
					while ((line = input.readLine()) != null) {
					    pidInfo+=line; 
					}
					input.close();
					if(!pidInfo.contains("Kcmd.exe"))
					{
						//Khi không có cmd chạy, trả về true và thoát vòng lặp
					    done = true;
					    break;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return done;
	}
}
