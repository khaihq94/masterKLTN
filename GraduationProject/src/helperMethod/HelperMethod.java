package helperMethod;

import java.io.*;
import java.net.URL;
import java.text.*;
import java.util.ArrayList;
import java.util.Date;
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
	
	/*
	 * 
	 * Dùng để xóa các stopword trong một văn bản
	 * 
	 * @input: text: đoạn văn cần loại bỏ stopword fileName: tên file stopword
	 * 
	 * @output: textResult: đoạn văn sau khi loại bỏ hết các stopword
	 */
	public String deleteStopWords(String content, String fileName) {
		ArrayList<String> stopWordsList = readStopWordsFile(fileName);
		ArrayList<String> wordList = new ArrayList<>();
		for (String word : content.split("\\s+")) {
			// System.out.println(word);
			wordList.add(word);
		}
		for (int i = 0; i < wordList.size(); i++) {
			for (String stopWord : stopWordsList) {
				if (wordList.get(i).equalsIgnoreCase(stopWord)) {
					wordList.remove(i);
				}
			}
		}
		return "";
	}

	/*
	 * 
	 * Dùng để đọc file chứa các stopword
	 * 
	 * @input: fileName: tên file chứa các stopwords
	 * 
	 * @output: chuỗi các stopword
	 */
	public ArrayList<String> readStopWordsFile(String fileName) {
		ArrayList<String> stopWords = new ArrayList<>();
		// Lấy đường dẫn của file stopword
		String path = System.getProperty("user.dir") + "//stopword//" + fileName;

		// Khởi tạo biến line để duyệt qua từng dòng
		String line = null;

		try {
			// FileReader đọc file theo Encode mặc định
			FileReader fileReader = new FileReader(path);

			// Tạo bufferedReader để duyệt qua từng dòng trong file
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
//				System.out.println(line);
				stopWords.add(line);
			}

			// Đóng buffered
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		return stopWords;
	}
	
	/*
	 * 
	 * Hàm seperateSentences
	 * Chức năng: tách văn bản đã qua tokenize thành các câu
	 * Input: văn bản đã được tokenize
	 * Output: mảng chứa các câu của văn bản.
	 * 
	 * */
	
	public ArrayList<String> seperateSentences(String text){
		ArrayList<String> sentences = new ArrayList<>();
		text = text.replace("./.", ".");
		// Loại bỏ các dấu đặc biệt trừ ?, ! và .
		text = text.replaceAll("[-^=+\\\\|\\[{\\]};:'\",<>/#()]*", "").replaceAll("\u2026", "").trim();
				
		//biến k duyệt qua từng ký tự trong văn bản
		int k = 0;
		//biến position: lưu lại vị trí ký tự cuối cùng của câu trước đó
		int position = 0;
		while(k < text.length()){
			// Nếu ký tự ở vị trí k trùng với dấu ! hoặc ? thì thêm câu đó vào mảng
			if(text.charAt(k) == '!' || text.charAt(k) == '?'){
				//Thêm câu có vị trí ký tự từ position đến k(bỏ dấu)
				sentences.add(text.substring(position, k).trim());
				//Tăng k để duyệt ký tự kế
				k++;
				// Gán position = k
				position = k;
			} 
			//Kiểm tra dấu chấm
			else if (text.charAt(k) == '.') {
				//Kiểm tra xem 2 ký tự kế tiếp có nằm trong chiều dài chuỗi ko
				if(k + 1 < text.length() && k + 2 < text.length()){
					//Kiểm tra xem phải là dấu ... hay không
					if (text.charAt(k + 1) == '.' && text.charAt(k + 2) == '.') {
						// Thêm câu có vị trí ký tự từ position đến k(bỏ dấu)
						sentences.add(text.substring(position, k).trim());
						// Tăng k + 3 để duyệt ký tự kế, bỏ qua duyệt các ký tự kế của dấu ...
						k = k + 3;
						// Gán position = k để câu sau lấy từ sau dấu ...
						position = k;
						continue;
					}
				}
				
				//Kiểm tra xem dấu . có phải là ngăn cách giữa các số, VD: 25.000
				if(k + 1 < text.length()){
					String beforeCharAtK = text.charAt(k - 1) + "";
					String afterCharAtK = text.charAt(k + 1) + "";
					//Nếu ký tự trước k và sau K là con số thì continue
					if(beforeCharAtK.matches("^\\d+$") && afterCharAtK.matches("^\\d+$")){
						k++;
						continue;
					}
				}
				// Thêm câu có vị trí ký tự từ position đến k(bỏ dấu)
				sentences.add(text.substring(position, k).trim());
				// Tăng k để duyệt ký tự kế
				k++;
				// Gán position = k để duyệt ký tự sau cấu ngắt câu
				position = k;
			} else{
				k++;
			}
		}
		return sentences;
	}

	/*
	 * 
	 * Hàm seperateWordss
	 * Chức năng: tách văn bản đã qua tokenize thành các từ và loại bỏ stopword
	 * Input: văn bản đã được tokenize
	 * Output: mảng chứa các từ của văn bản.
	 * 
	 * */
	public ArrayList<String> seperateWords(String text){
		text = text.toLowerCase().trim();
		//Khai báo mảng words để chứa các từ trong văn bản
		ArrayList<String> words = new ArrayList<>();
		//Khai báo mảng stopWordsList để đọc file chứa các từ dừng
		ArrayList<String> stopWords = readStopWordsFile("vietnamese-stopwords.txt");
		
		// Loại bỏ các dấu đặc biệt
		String content = text.replaceAll("[-^=+\\\\|\\[{\\]};:'\".,<>/#!?()]*", "").replaceAll("\u2026", "").trim();
		
		// biến k duyệt qua từng ký tự trong văn bản
		int k = 0;
		// biến position: lưu lại vị trí ký tự cuối cùng của câu trước đó
		int position = 0;
		// biến word: lưu các từ
		String word;
		//Duyệt qua văn bản để loại bỏ các dấu trong văn bản
		while(k < content.length()){
			String character = content.charAt(k) + "";
			// Gặp khoảng trắng thì tách từ
			if(character.equals(" ")){
				String beforeCharacter = content.charAt(k-1) + "";
				// Nếu ký tự trước ký tự hiện tại cũng là khoảng trắng thì bỏ qua, ko tách từ
				if(beforeCharacter.equals(" ")){
					k++;
					position = k;
					continue;
				}
				word = content.substring(position, k).trim();
				/*//Kiểm tra xem từ đó có phải là stopword ko
				for(String stopWord : stopWords){
					//Nếu là stopword, gán bằng rỗng, thoát khỏi vòng lặp kiểm tra
					if(word.equals(stopWord)){
						word = "";
						break;
					}
				}*/
				// Nếu từ khác rỗng thì thêm vào mảng
				if(!word.equals("")){
					words.add(word.trim());
				}
				// Tăng k để duyệt ký tự kế tiếp
				k++;
				// Gán position bằng k để lấy vị trí kế
				position = k;
			} else {
				k++;
			}
		}
		//Loại bỏ stopword khỏi mảng
		for(String stopWord : stopWords){
			//Nếu là stopword, xóa khỏi mảng
			for(int i = 0; i < words.size(); i ++){
				if(words.contains(stopWord)){
					words.remove(stopWord);
				} else{
					break;
				}
			}
			
		}
		return words;
	}
	
	/*
	 * Chuyển từ String sang Date
	 * input: chuỗi String có nội dung là ngày
	 * output: ngày có kiểu dữ liệu Date
	 * */
	public Date stringToDate(String stringDate){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dateDate = null;
		try {
			dateDate = df.parse(stringDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateDate;
	}
}
