package helperMethod;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class HelperMethod {

	/*
	 * Tải hình ảnh
	 * input: imgURL: link đến hình ảnh ; savedLocationPath: tên folder để tải hình trong folder image mặc định ; fileName: tên hình ảnh
	 * */
	public void getImage(String imgURL, String savedLocationPath, String fileName) throws IOException{
		String path = System.getProperty("user.dir") + "\\image\\";
		//Mở URL stream
		URL url = new URL(imgURL);
		InputStream inputStream = url.openStream();
		OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path + savedLocationPath + "\\" + fileName));
		for (int b; (b = inputStream.read()) != -1;) {
			outputStream.write(b);
		}
		outputStream.close();
		inputStream.close();
	}
	
	/*
	 * Loại bỏ các mã img và subPhoto ra khỏi đoạn văn
	 * input: content: văn bản cần loại bỏ img và subPhoto
	 * output: văn bản đã được bỏ các dòng img và subPhotot
	 * */
	public String formatContent(String content){
		String newContent = "";
		Scanner scanner = new Scanner(content);
		scanner.useDelimiter("\r\n");
		while (scanner.hasNext()) {
			String line = scanner.next();
			if(!line.contains("img=") && !line.contains("subPhoto=")){
				newContent = newContent + line + " \r\n";
			}
		}
		return newContent;
	}
	/*
	 * Chuyển từ mảng sang chuỗi
	 * input: array: mảng có kiểu dữ liệu là String
	 * output: chuỗi từ mảng
	 * */
	public String arrayToString(String[] array){
		StringBuilder builder = new StringBuilder();
		for(String s : array){
			builder.append(s);
		}
		return builder.toString();
	}
}
