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
		return nonWordList;
	}

	public ArrayList<String> separateWords(String content) {
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
	}

	/*
	 * 
	 * Dùng để xóa các stopword trong một văn bản
	 * 
	 * @input: text: đoạn văn cần loại bỏ stopword fileName: tên file stopword
	 * 
	 * @output: textResult: đoạn văn sau khi loại bỏ hết các stopword
	 */
	public ArrayList<String> deleteStopWords(ArrayList<String> contents, String fileName) {
		ArrayList<String> stopWordsList = readStopWordsFile(fileName);
		for (int i = 0; i < contents.size(); i++) {
			for (String stopWord : stopWordsList) {
				if (contents.get(i).equalsIgnoreCase(stopWord)) {
					contents.remove(i);
				}
			}
		}
		return contents;
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
}
