package crawlData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import helperMethod.*;
import model.Tblnews;
import plagiarism.CheckPlagiarism;

public class CrawlData {

	DataHelper dataHelper = new DataHelper();
	HelperMethod helper = new HelperMethod();
	CrawlNewsZing crawlNewsZing = new CrawlNewsZing();
	CrawlDanTri crawlDanTri = new CrawlDanTri();
	CrawlVietnamNet crawlVietnamNet = new CrawlVietnamNet();
	CrawlLaoDong crawlLaoDong = new CrawlLaoDong();
	CheckPlagiarism checkPlagiarism = new CheckPlagiarism();

	public void crawlData() {
//		getDanTri();
//		getLaoDong();
		getNewsZing();
//		getVietnamnet();
	}

	public void getDanTri() {

		// Lấy các link con từ link chủ đề
		ArrayList<ArrayList<String>> links = crawlDanTri.getLinksDanTri();

		/*
		 * ArrayList<ArrayList<String>> links = new
		 * ArrayList<ArrayList<String>>(); ArrayList<String> tmp = new
		 * ArrayList<>(); tmp.add(
		 * "http://dantri.com.vn/the-gioi/nga-dua-he-thong-ten-lua-s-400-triumf-vao-nhiem-vu-truc-chien-20161208143317473.htm"
		 * ); tmp.add("thegioi"); links.add(tmp);
		 */

		getContent(links);
	}

	public void getLaoDong() {
		// Lấy các link con từ link chủ đề
		ArrayList<ArrayList<String>> links = crawlLaoDong.getLinksLaoDong();
		
		getContent(links);
	}

	public void getNewsZing() {
		// Lấy các link con từ link chủ đề
		ArrayList<ArrayList<String>> links = crawlNewsZing.getLinksNewsZing();

		/*
		 * ArrayList<ArrayList<String>> links = new
		 * ArrayList<ArrayList<String>>(); ArrayList<String> tmp = new
		 * ArrayList<>(); tmp.add(
		 * "http://news.zing.vn/loet-mieng-co-can-bo-sung-vitamin-post704183.html"
		 * ); tmp.add("suckhoe"); links.add(tmp);
		 */
		
		getContent(links);
	}

	public void getVietnamnet() {
		// Lấy các link con từ link chủ đề
		ArrayList<ArrayList<String>> links = crawlVietnamNet.getLinksVietnamnet();

		/*
		 * ArrayList<ArrayList<String>> links = new
		 * ArrayList<ArrayList<String>>(); ArrayList<String> tmp = new
		 * ArrayList<>(); tmp.add(
		 * "http://vietnamnet.vn/vn/kinh-doanh/thuong-hieu-quoc-gia-8-nam-lien-tiep-vinh-danh-vinamilk-345416.html"
		 * ); tmp.add("kinhdoanh"); links.add(tmp);
		 */

		getContent(links);
	}
	
	public void getContent(ArrayList<ArrayList<String>> links){
		ArrayList<String> content1 = new ArrayList<>();
		List<Tblnews> lists = null;
		// Duyệt qua từng link con
		for (int i = 0; i < links.size(); i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(links.get(i).get(0).trim());
			// So sánh link của tin vừa lấy với các tin có trong db
			// Lấy danh sách tin tức trong db theo link
			List<Tblnews> listLinks = dataHelper.getNewsByLink(links.get(i).get(0).trim());
			// Nếu danh sách có size >= 1, nghĩa là trong db đã có link đó =>
			// continue
			if (listLinks.size() >= 1) {
				continue;
			}

			if (links.get(i).get(0).trim().contains("dantri")) {
				// Lấy nội dung của link con
				content1 = crawlDanTri.getContentDantri(links.get(i));
			} else if (links.get(i).get(0).trim().contains("news.zing.vn")) {
				// Lấy nội dung của link con
				content1 = crawlNewsZing.getContentNewsZing(links.get(i));
			} else if (links.get(i).get(0).trim().contains("vietnamnet")) {
				// Lấy nội dung của link con
				content1 = crawlVietnamNet.getContentVietnamNet(links.get(i));
			} else if (links.get(i).get(0).trim().contains("laodong")) {
				// Lấy nội dung của link con
				content1 = crawlLaoDong.getContentLaoDong(links.get(i));
			}

			// Kiểm tra nội dung lấy đc, nếu nội dung chính = "" thì bỏ qua các
			// bước tiếp theo
			if (content1.size() < 7) {
				continue;
			}

			if(content1.get(4) == ""){
				continue;
			}
			
			// Lấy các tin tức từ db với mốc ngày hiện tại trừ 3
			lists = dataHelper.getNewsListFromLast3DaysBySubjectId(content1.get(6));

			// Nếu không trùng thì thực hiện các bước kiểm tra về nội dung với
			// các tin trong db đã lấy ở trên
			for (int x = 0; x < lists.size(); x++) {
				Tblnews list = lists.get(x);
				// Lấy độ tương đồng giữa 2 văn bản
				double simSO = checkPlagiarism.getSimSO(content1.get(4), list.getContent());
				// Nếu lớn hơn 0.7, xóa tin tức và ko lưu vào db
				if (simSO >= 0.7) {
					lists.clear();
					break;
				}
				// Nếu độ tương đồng giữa 2 văn bản nhỏ hơn 0.7 thì lưu tin mới
				// vào db
				else if (x == lists.size() - 1) {
					dataHelper.insertTblNews(content1.get(0), content1.get(1), helper.stringToDate(content1.get(2)),
							content1.get(3), content1.get(4), content1.get(5), content1.get(6));
					lists.clear();
				}
			}
		}

		// Xóa hết link con trong mảng
		links.clear();
	}
}
