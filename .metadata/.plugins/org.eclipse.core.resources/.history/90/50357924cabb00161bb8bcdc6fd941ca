package crawlData;

import java.io.*;
import java.util.ArrayList;

import helperMethod.*;
import plagiarism.CheckPlagiarism;


public class CrawlData {
	
	DataHelper dataHelper = new DataHelper();
	HelperMethod helper = new HelperMethod();
	CrawlNewsZing crawlNewsZing = new CrawlNewsZing();
	CrawlDanTri crawlDanTri = new CrawlDanTri();
	CrawlVietnamNet crawlVietnamNet = new CrawlVietnamNet();
	CrawlLaoDong crawlLaoDong = new CrawlLaoDong();
	CheckPlagiarism checkPlagiarism = new CheckPlagiarism();
	
	public void crawlData(){
		getDanTri();
		getLaoDong();
		getNewsZing();
		getVietnamnet();
	}
	
	public void getDanTri(){
		//Lấy các tin tức từ db với mốc ngày hiện tại trừ 3
		
		//Lấy các link con từ link chủ đề
		ArrayList<ArrayList<String>> links = crawlDanTri.getLinksDanTri();
		ArrayList<String> content1 = new ArrayList<>();
		//Duyệt qua từng link con
		for(int i = 0; i < links.size(); i++){
			//Lấy nội dung của link con
			content1 = crawlDanTri.getContentDantri(links.get(i));
			//Kiểm tra nội dung lấy đc, nếu nội dung chính = "" thì bỏ qua các bước tiếp theo
			if(content1.get(4) == ""){
				continue;
			}
			dataHelper.insertTblNews(content1.get(0), content1.get(1), helper.stringToDate(content1.get(2)), 
					content1.get(3), content1.get(4), content1.get(5), content1.get(6));
			//So sánh link của tin vừa lấy với các tin có trong db đã đc lấy ở trên
			
			/*//Nếu trùng remove content khỏi mảng, bỏ qua các bước dưới
			if(){
				continue;
			}
			//Nếu không trùng thì thực hiện các bước kiểm tra về nội dung với các tin trong db đã lấy ở trên
			//Lấy độ tương đồng giữa 2 văn bản
			double simSO = checkPlagiarism.getSimSO(content1, content2);
			//Nếu độ tương đồng giữa 2 văn bản nhỏ hơn 0.7 thì lưu tin mới vào db
			if(simSO <= 0.7) {
				
			}
			//Nếu lớn hơn 0.7, xóa tin tức và ko lưu vào db
			else {
				
			}*/
		}
		//Xóa hết link con trong mảng
		links.clear();
	}

	public void getLaoDong(){
		
	}
	
	public void getNewsZing(){
		
	}
	
	public void getVietnamnet(){
		
	}
}
