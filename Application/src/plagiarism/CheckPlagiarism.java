package plagiarism;

import java.util.ArrayList;
import java.io.*;

public class CheckPlagiarism {

	public void checkPlag1() {

	}

	public ArrayList<String> nonWordList() {
		ArrayList<String> nonWordList = new ArrayList<>();
		nonWordList.add("+");
		nonWordList.add("-");
		nonWordList.add(".");
		nonWordList.add(",");
		nonWordList.add("!");
		nonWordList.add("@");
		nonWordList.add("#");
		nonWordList.add("$");
		nonWordList.add("\'");
		nonWordList.add("^");
		nonWordList.add("&");
		nonWordList.add("*");
		nonWordList.add("(");
		nonWordList.add(")");
		nonWordList.add(";");
		nonWordList.add("\\");
		nonWordList.add("/");
		nonWordList.add("|");
		nonWordList.add(">");
		nonWordList.add("<");
		nonWordList.add("?");
		nonWordList.add("\"");
		nonWordList.add(":");
		nonWordList.add("`");
		nonWordList.add("~");
		nonWordList.add("\"");
		nonWordList.add("[");
		nonWordList.add("]");
		nonWordList.add("{");
		nonWordList.add("}");
		return nonWordList;
	}

	/*public ArrayList<String> separateWords(String[] content) {
		ArrayList<String> wordList = new ArrayList<>();
		ArrayList<String> nonWordList = nonWordList();
		for (String word : content.split("\\s+")) {
			// System.out.println(word);
			wordList.add(word);
		}

		for (int i = 0; i < wordList.size(); i++) {
			for (String nonWord : nonWordList) {
				String word = wordList.get(i).trim();
				// Nếu từ có chứa dấu thì xóa dấu
				if ((word.contains(nonWord) && word.length() > 1) || (word.equals(nonWord))) {
					wordList.set(i, wordList.get(i).replace(nonWord, ""));
				}

			}
		}

		for (int i = 0; i < wordList.size(); i++) {
			String word = wordList.get(i);
			if (word.equals("")) {
				wordList.remove(i);
				// wordList.set(i, wordList.get(i).replace(" ", ""));
			}
		}


		wordList = deleteStopWords(wordList, "vietnamese-stopwords.txt");

		for (String word : wordList) {
			System.out.println(word);
		}

		return wordList;
	}*/

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
				if(k + 1 <= text.length() && k + 2 <= text.length()){
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
				if(k + 1 <= text.length()){
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
		//Khai báo mảng words để chứa các từ trong văn bản
		ArrayList<String> words = new ArrayList<>();
		//Khai báo mảng stopWordsList để đọc file chứa các từ dừng
		ArrayList<String> stopWords = readStopWordsFile("vietnamese-stopwords.txt");
		
		// Loại bỏ các dấu đặc biệt
		String content = text.replaceAll("[-^=+\\\\|\\[{\\]};:'\",<>/#()]*", "").replaceAll("\u2026", "");
		
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
				//Kiểm tra xem từ đó có phải là stopword ko
				for(String stopWord : stopWords){
					//Nếu là stopword, gán bằng rỗng, thoát khỏi vòng lặp kiểm tra
					if(word.equals(stopWord)){
						word = "";
						break;
					}
				}
				// Nếu từ khác rỗng thì thêm vào mảng
				if(!word.equals("")){
					words.add(word);
				}
				// Tăng k để duyệt ký tự kế tiếp
				k++;
				// Gán position bằng k để lấy vị trí kế
				position = k;
			} else {
				k++;
			}
		}
		return words;
	}

}
