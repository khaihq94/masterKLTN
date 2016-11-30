package plagiarism;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class CheckPlagiarism {

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
		text = text.toLowerCase();
		//Khai báo mảng words để chứa các từ trong văn bản
		ArrayList<String> words = new ArrayList<>();
		//Khai báo mảng stopWordsList để đọc file chứa các từ dừng
		ArrayList<String> stopWords = readStopWordsFile("vietnamese-stopwords.txt");
		
		// Loại bỏ các dấu đặc biệt
		String content = text.replaceAll("[-^=+\\\\|\\[{\\]};:'\".,<>/#()]*", "").replaceAll("\u2026", "");
		
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
	 * 
	 * Tính IDF của từ: IDF của 1 từ đc tính bằng hệ số giữa tổng số câu trong văn bản chia cho tổng số câu chứa từ đó
	 * input: word - từ cần tính IDF;	sentences - mảng chứa các câu của văn bản đang xét;		words - mảng chứa các từ của văn bản
	 * output: giá trị IDF của từ đuuợc đưa vào
	 * 
	 * */
	public double getIDF(String word, ArrayList<String> sentences){
		//Tổng số câu trong văn bản
		int numberOfSentences = sentences.size();
		double count = 0;
		//Tính tổng số lần xuất hiện của từ trong văn bản theo từng câu
		for(String sentence : sentences){
			for(String tmpWord : seperateWords(sentence)){
				if(tmpWord.equalsIgnoreCase(word)){
					break;
				}
			}
			count++;
		}
		/*for(String tmpWord : words){
			if(word.equalsIgnoreCase(tmpWord)){
				count++;
			}
		}*/
		double IDF = numberOfSentences/count;
		return IDF;
	}
	
	/*
	 * Tính độ tương tự lớn nhất của từ so với 1 văn bản
	 * input: word - từ cần tìm độ tương tự
	 * output: độ tương tự lớn nhất của từ so với văn bản
	 * */
	public double getMaxSim(String word, ArrayList<String> words){
		double maxSim = 0;
		word = word.trim();
		for(String tmpWord : words){
			if(word.equals(tmpWord)){
				maxSim = 1;
			}else if(word.equalsIgnoreCase(tmpWord)){
				maxSim = 0.98;
			}
		}
		return maxSim;
	}
	
	public double getSimS(String content1, String content2){
		
		
		ArrayList<String> words1 = seperateWords(content1);
		ArrayList<String> words2 = seperateWords(content2);
		ArrayList<String> sentences1 = seperateSentences(content1);
		ArrayList<String> sentences2 = seperateSentences(content2);
		
		double totalMaxSim1 = 0;
		double totalIDF1 = 0;
		for(String word : words1){
			double wordIDF = getIDF(word, sentences1);
			totalIDF1 += wordIDF;
			totalMaxSim1 += getMaxSim(word, words2);
		}
		double sim1 = 0;
		if(totalIDF1 != 0){
			sim1 = totalMaxSim1 / totalIDF1;
		}
		
		double totalMaxSim2 = 0;
		double totalIDF2 = 0;
		for(String word : words2){
			double wordIDF = getIDF(word, sentences2);
			totalIDF2 += wordIDF;
			totalMaxSim2 += getMaxSim(word, words1);
		}
		double sim2 = 0;
		if(totalIDF2 != 0){
			sim2 = totalMaxSim2 / totalIDF2;
		}
		
		return (sim1 + sim2)/2;
	}
	
	/*
	 * Lấy ra tập từ phân biệt của 2 văn bản
	 * input: 2 văn bản đã được tokenize
	 * output: mảng chứa tập từ phân biệt
	 * */
	public ArrayList<String> getListDiffWords(ArrayList<String> content1, ArrayList<String> content2){
		ArrayList<String> listDiffWords = new ArrayList<>();
		for(String word1 : content1){
			//Nếu mảng có từ này rồi thì bỏ qua không kiểm tra
			if(listDiffWords.contains(word1)){
				break;
			}
			//Kiểm tra từ của content1 có trong content2 hay ko, nếu có thì thêm từ vào tập từ phân biệt và xóa từ đó khỏi content2
			for(int i = 0; i < content2.size(); i++){
				if(content2.contains(word1)){
					if(!listDiffWords.contains(word1)){
						listDiffWords.add(word1);
					}
					content2.remove(word1);
				} else {
					break;
				}
			}
			
		}
		for(String word2 : content2){
			//Nếu mảng có từ này rồi thì bỏ qua không kiểm tra
			if(listDiffWords.contains(word2)){
				break;
			}
			//Kiểm tra từ của content2 có trong content1 hay ko, nếu có thì thêm từ vào tập từ phân biệt và xóa từ đó khỏi content1
			for(int i = 0; i < content1.size(); i++){
				if(content1.contains(word2)){
					if(!listDiffWords.contains(word2)){
						listDiffWords.add(word2);
					}
					content1.remove(word2);
				}
			}
			
		}
		return listDiffWords;
	}
	
	/*
	 * Lấy vectơ đặc trưng theo thứ tự từ
	 * input: mảng chứa tập từ phân biệt của 2 văn bản, mảng chứa từ của văn bản cần tính vectơ
	 * output: mảng chứa vectơ đặc trưng
	 * */
	public ArrayList<ArrayList<Integer>> getVecto(ArrayList<String> listDiffWord, ArrayList<String> content){
		//Khai báo mảng vecto R
		ArrayList<ArrayList<Integer>> R = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < listDiffWord.size(); i++){
			//Khai báo mảng tạm tmp để lưu trữ vị trí của 1 từ
			ArrayList<Integer> tmp = new ArrayList<>();
			//Lấy từ thứ i trong mảng listDiffWord
			String diffWord = listDiffWord.get(i).trim();
			//Duyệt qua mảng content để tìm thứ tự của diffWord trong mảng
			for(int j = 0; j < content.size(); j++){
				String word = content.get(j).trim();
				//Nếu tìm thấy diffWord trong content thì lưu vị trí vào mảng tmp
				if(diffWord.equalsIgnoreCase(word)){
					tmp.add(j);
				}
			}
			//Sau khi duyệt xong và tìm ra thứ tự của 1 từ trong Tập từ phân biệt thì lưu vào mảng vecto R
			R.add(tmp);
		}
		return R;
	}
	
	
	/*
	 * Tính độ tương tự theo thứ tự từ
	 * input: 2 mảng là 2 vecto của 2 văn bản cần so sánh
	 * output: simO - độ tương tự về thứ tự từ của 2 văn bản
	 * */
	public double getSimO(String content1, String content2){
		double numerator, denominator, simO;
		int i = 0;
		double tmpNum = 0;
		double tmpDen = 0;
		ArrayList<String> words1 = seperateWords(content1);
		ArrayList<String> words2 = seperateWords(content2);
		ArrayList<String> T = getListDiffWords(words1, words2);
		
		ArrayList<ArrayList<Integer>> R1 = getVecto(T, seperateWords(content1));
		ArrayList<ArrayList<Integer>> R2 = getVecto(T, seperateWords(content2));
		//Tính numerator và denominator trước khi lấy căn
		while (i < R1.size() && i < R2.size()) {
			int j = 0;
			int maxSize;
			// Tìm ra số lần xuất hiện thấp nhất của từ trong 2 văn bản
			if (R1.get(i).size() < R2.get(i).size()) {
				maxSize = R1.get(i).size();
			} else {
				maxSize = R2.get(i).size();
			}
			while (j < maxSize) {
				tmpNum = tmpNum + (R1.get(i).get(j) - R2.get(i).get(j)) * (R1.get(i).get(j) - R2.get(i).get(j));
				tmpDen = tmpDen + (R1.get(i).get(j) + R2.get(i).get(j)) * (R1.get(i).get(j) + R2.get(i).get(j));
				j++;
			}
			i++;
		}
		numerator = Math.sqrt(tmpNum);
		denominator = Math.sqrt(tmpDen);
		simO = 1 - (numerator / denominator);
		return simO;
	}
	
	/*
	 * Tính độ tương tự giữa 2 văn bản
	 * input: 2 văn bản đã đc tách từ
	 * output: độ tương tự đc tính dựa trên Độ tương tự ngữ nghĩa và Độ tương tự thứ tự từ
	 * */
	public double getSimSO(String content1, String content2){
		double simS = getSimS(content1, content2);
		double simO = getSimO(content1, content2);
		return simS * 0.5 + simO * 0.5;
	}
}
