package plagiarism;

import java.util.ArrayList;
import java.util.Arrays;

import helperMethod.HelperMethod;

import java.io.*;

public class CheckPlagiarism {

	HelperMethod helper = new HelperMethod();

	/*
	 * 
	 * Tính IDF của từ: IDF của 1 từ đc tính bằng hệ số giữa tổng số câu trong
	 * văn bản chia cho tổng số câu chứa từ đó input: word - từ cần tính IDF;
	 * sentences - mảng chứa các câu của văn bản đang xét; words - mảng chứa các
	 * từ của văn bản output: giá trị IDF của từ đuuợc đưa vào
	 * 
	 */
	public double getIDF(String word, ArrayList<String> sentences) {
		// Tổng số câu trong văn bản
		int numberOfSentences = sentences.size();
		double count = 0;
		// Tính tổng số lần xuất hiện của từ trong văn bản theo từng câu
		for (String sentence : sentences) {
			for (String tmpWord : helper.seperateWords(sentence)) {
				if (tmpWord.equalsIgnoreCase(word)) {
					break;
				}
			}
			count++;
		}
		/*
		 * for(String tmpWord : words){ if(word.equalsIgnoreCase(tmpWord)){
		 * count++; } }
		 */
		double IDF = numberOfSentences / count;
		return IDF;
	}

	/*
	 * Tính độ tương tự lớn nhất của từ so với 1 văn bản input: word - từ cần
	 * tìm độ tương tự output: độ tương tự lớn nhất của từ so với văn bản
	 */
	public double getMaxSim(String word, ArrayList<String> words) {
		double maxSim = 0;
		word = word.trim();
		for (String tmpWord : words) {
			if (word.equals(tmpWord)) {
				maxSim = 1;
			} else if (word.equalsIgnoreCase(tmpWord)) {
				maxSim = 0.98;
			}
		}
		return maxSim;
	}

	public double getSimS(String content1, String content2) {

		ArrayList<String> words1 = helper.seperateWords(content1);
		ArrayList<String> words2 = helper.seperateWords(content2);
		ArrayList<String> sentences1 = helper.seperateSentences(content1);
		ArrayList<String> sentences2 = helper.seperateSentences(content2);

		double totalMaxSim1 = 0;
		double totalIDF1 = 0;
		for (String word : words1) {
			double wordIDF = getIDF(word, sentences1);
			totalIDF1 += wordIDF;
			totalMaxSim1 += getMaxSim(word, words2);
		}
		double sim1 = 0;
		if (totalIDF1 != 0) {
			sim1 = totalMaxSim1 / totalIDF1;
		}

		double totalMaxSim2 = 0;
		double totalIDF2 = 0;
		for (String word : words2) {
			double wordIDF = getIDF(word, sentences2);
			totalIDF2 += wordIDF;
			totalMaxSim2 += getMaxSim(word, words1);
		}
		double sim2 = 0;
		if (totalIDF2 != 0) {
			sim2 = totalMaxSim2 / totalIDF2;
		}

		return (sim1 + sim2) / 2;
	}

	/*
	 * Lấy ra tập từ phân biệt của 2 văn bản input: 2 văn bản đã được tokenize
	 * output: mảng chứa tập từ phân biệt
	 */
	public ArrayList<String> getListDiffWords(ArrayList<String> content1, ArrayList<String> content2) {
		ArrayList<String> listDiffWords = new ArrayList<>();
		for (String word1 : content1) {
			// Nếu mảng có từ này rồi thì bỏ qua không kiểm tra
			if (listDiffWords.contains(word1)) {
				break;
			}
			// Kiểm tra từ của content1 có trong content2 hay ko, nếu có thì
			// thêm từ vào tập từ phân biệt và xóa từ đó khỏi content2
			for (int i = 0; i < content2.size(); i++) {
				if (content2.contains(word1)) {
					if (!listDiffWords.contains(word1)) {
						listDiffWords.add(word1);
					}
					content2.remove(word1);
				} else {
					break;
				}
			}

		}
		for (String word2 : content2) {
			// Nếu mảng có từ này rồi thì bỏ qua không kiểm tra
			if (listDiffWords.contains(word2)) {
				break;
			}
			// Kiểm tra từ của content2 có trong content1 hay ko, nếu có thì
			// thêm từ vào tập từ phân biệt và xóa từ đó khỏi content1
			for (int i = 0; i < content1.size(); i++) {
				if (content1.contains(word2)) {
					if (!listDiffWords.contains(word2)) {
						listDiffWords.add(word2);
					}
					content1.remove(word2);
				}
			}

		}
		return listDiffWords;
	}

	/*
	 * Lấy vectơ đặc trưng theo thứ tự từ input: mảng chứa tập từ phân biệt của
	 * 2 văn bản, mảng chứa từ của văn bản cần tính vectơ output: mảng chứa
	 * vectơ đặc trưng
	 */
	public ArrayList<ArrayList<Integer>> getVecto(ArrayList<String> listDiffWord, ArrayList<String> content) {
		// Khai báo mảng vecto R
		ArrayList<ArrayList<Integer>> R = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < listDiffWord.size(); i++) {
			// Khai báo mảng tạm tmp để lưu trữ vị trí của 1 từ
			ArrayList<Integer> tmp = new ArrayList<>();
			// Lấy từ thứ i trong mảng listDiffWord
			String diffWord = listDiffWord.get(i).trim();
			// Duyệt qua mảng content để tìm thứ tự của diffWord trong mảng
			for (int j = 0; j < content.size(); j++) {
				String word = content.get(j).trim();
				// Nếu tìm thấy diffWord trong content thì lưu vị trí vào mảng
				// tmp
				if (diffWord.equalsIgnoreCase(word)) {
					tmp.add(j);
				}
			}
			if (tmp.size() == 0) {
				tmp.add(-1);
			}
			// Sau khi duyệt xong và tìm ra thứ tự của 1 từ trong Tập từ phân
			// biệt thì lưu vào mảng vecto R
			R.add(tmp);
		}
		return R;
	}

	/*
	 * Tính độ tương tự theo thứ tự từ input: 2 mảng là 2 vecto của 2 văn bản
	 * cần so sánh output: simO - độ tương tự về thứ tự từ của 2 văn bản
	 */
	public double getSimO(String content1, String content2) {
		double numerator, denominator, simO;
		int i = 0;
		double tmpNum = 0;
		double tmpDen = 0;
		ArrayList<String> words1 = helper.seperateWords(content1);
		ArrayList<String> words2 = helper.seperateWords(content2);
		ArrayList<String> T = getListDiffWords(words1, words2);

		ArrayList<ArrayList<Integer>> R1 = getVecto(T, helper.seperateWords(content1));
		ArrayList<ArrayList<Integer>> R2 = getVecto(T, helper.seperateWords(content2));
		// Tính numerator và denominator trước khi lấy căn
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
	 * Tính độ tương tự giữa 2 văn bản input: 2 văn bản đã đc tách từ output: độ
	 * tương tự đc tính dựa trên Độ tương tự ngữ nghĩa và Độ tương tự thứ tự từ
	 */
	public double getSimSO(String content1, String content2) {
		double simS = getSimS(helper.formatContent(content1), helper.formatContent(content2));
		double simO = getSimO(helper.formatContent(content1), helper.formatContent(content2));
		return simS * 0.5 + simO * 0.5;
	}
}
