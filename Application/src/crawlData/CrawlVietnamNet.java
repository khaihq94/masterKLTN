package crawlData;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlVietnamNet {
	/*
	 * Danh sách các link chủ đề
	 */
	public ArrayList<String> addUrl() {
		ArrayList<String> URLs = new ArrayList<String>();
//		URLs.add("http://vietnamnet.vn/vn/kinh-doanh/");
		URLs.add("http://vietnamnet.vn/vn/the-gioi/");
//		URLs.add("http://vietnamnet.vn/vn/the-thao/");
//		URLs.add("http://vietnamnet.vn/vn/cong-nghe/");
//		URLs.add("http://vietnamnet.vn/vn/suc-khoe/");
		return URLs;
	}

	/*
	 * 
	 * Lấy tất cả các link tin tức trong link chủ đề
	 * 
	 * Output: biến trả ra danh sách các link tin tức thuộc chủ đề
	 * 
	 */
	public ArrayList<String> getLinksVietnamnet() {
		ArrayList<String> URLs = addUrl();
		ArrayList<String> link = new ArrayList<String>();
		for (String url : URLs) {
			try {
				// Lay het noi dung HTML cua trang co URL = currentURL
				Document doc = Jsoup.connect(url).userAgent("Mozilla").cookie("auth", "token")
						.timeout(50000).get();

				// Lay cac element la div trong doc
				Elements divs = doc.getElementsByTag("div");
				// Duyệt qua từng element
				for (Element div : divs) {
					if (div.attr("class").trim().equals("Main-Container") && div.tagName().trim().equals("div")) {
						Elements mainContainers = div.children();
						for (Element mainContainer : mainContainers) {
							if (mainContainer.attr("class").trim().equals("Main-Body w-1000") && mainContainer.tagName().trim().equals("div")) {
								Elements w1000s = mainContainer.children();
								for (Element w1000 : w1000s) {
									// Lấy link trong class CatTop
									if (w1000.attr("class").trim().equals("CatTop clearfix") && w1000.tagName().trim().equals("div")) {
										Elements catTops = w1000.children();
										for (Element catTop : catTops) {
											if (catTop.attr("class").trim().equals("w-690 left") && catTop.tagName().trim().equals("div")) {
												Elements w690s = catTop.children();
												for (Element w690 : w690s) {
													if (w690.attr("class").trim().equals("clearfix") && w690.tagName().trim().equals("div")) {
														Elements clearfixs = w690.children();
														for (Element clearfix : clearfixs) {
															// Lấy link trong class CateTopLeft
															if (clearfix.attr("class").trim().equals("CateTopLeft left m-t-10") && clearfix.tagName().trim().equals("div")) {
																Elements cateTopLefts = clearfix.children();
																for (Element cateTopLeft : cateTopLefts) {
																	if (cateTopLeft.attr("class").trim().equals("TopArticle") && cateTopLeft.tagName().trim().equals("div")) {
																		Elements topArticles = cateTopLeft.children();
																		for (Element topArticle : topArticles) {
																			if (topArticle.attr("class").trim().equals("f-20") && topArticle.tagName().trim().equals("h2")) {
																				Elements f20s = topArticle.children();
																				for (Element f20 : f20s) {
																					if (f20.tagName().trim().equals("a")) {
//																						System.out.println("http://vietnamnet.vn" + f20.attr("href"));
																						link.add("http://vietnamnet.vn" + f20.attr("href"));
																					}
																				}
																			}
																		}
																	}
																}
															}
															// Lấy link trong class CateTopCenter
															if (clearfix.attr("class").trim().equals("CateTopCenter left m-l-10") && clearfix.tagName().trim().equals("div")) {
																Elements cateTopCenters = clearfix.children();
																for (Element cateTopCenter : cateTopCenters) {
																	if (cateTopCenter.attr("class").trim().equals("ListHeight m-t-10") && cateTopCenter.tagName().trim().equals("ul")) {
																		Elements listHeights = cateTopCenter.children();
																		for (Element listHeight : listHeights) {
																			if (listHeight.attr("class").trim().equals("dotter") && listHeight.tagName().trim().equals("li")) {
																				Elements dotters = listHeight.children();
																				for (Element dotter : dotters) {
																					if (dotter.tagName().trim().equals("a")) {
//																						System.out.println("http://vietnamnet.vn" + dotter.attr("href"));
																						link.add("http://vietnamnet.vn" + dotter.attr("href"));
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
									// Lấy link trong class HomeBlock
									if (w1000.attr("class").trim().equals("HomeBlock clearfix m-t-10") && w1000.tagName().trim().equals("div")) {
										Elements homeBlocks = w1000.children();
										for (Element homeBlock : homeBlocks) {
											if (homeBlock.attr("class").trim().equals("HomeBlockLeft left") && homeBlock.tagName().trim().equals("div")) {
												Elements homeBlockLefts = homeBlock.children();
												for (Element homeBlockLeft : homeBlockLefts) {
													if (homeBlockLeft.attr("class").trim().equals("m-t-10") && homeBlockLeft.tagName().trim().equals("div")) {
														Elements mt10s = homeBlockLeft.children();
														for (Element mt10 : mt10s) {
															if (mt10.attr("class").trim().equals("ListArticle") && mt10.tagName().trim().equals("ul")) {
																Elements listArticles = mt10.children();
																for (Element listArticle : listArticles) {
																	if (listArticle.attr("class").trim().equals("item clearfix dotter") && listArticle.tagName().trim().equals("li")) {
																		Elements items = listArticle.children();
																		for (Element item : items) {
																			if (item.tagName().trim().equals("a")) {
//																				System.out.println("http://vietnamnet.vn" + item.attr("href"));
																				link.add("http://vietnamnet.vn" + item.attr("href"));
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Loại bỏ những link trùng nhau
		for (int i = 0; i < link.size(); i++) {
			int j = i + 1;
			while (j < link.size()) {
				if (link.get(i).equals(link.get(j))) {
					link.remove(j);
				}
				j++;
			}
		}
		return link;
	}
	
	/*
	 * Hàm lấy nội dung tin tức và trả về 1 mảng
	 * input: link - link của tin tức cần lấy
	 * output: mảng chứa các nội dung tin tức. Trong đó:
	 * 		Phần tử 0 là Tiêu đề (Title) tin
	 * 		Phần tử 1 là thời gian (Time) đăng tin
	 * 		Phần tử 2 là đoạn tóm tắt (Summary) tin
	 * 		Phần tử 3 là nội dung của cả bài báo
	 * 		Phần tử 4 là tên báo mà tin đc lấy về
	 * */
	public ArrayList<String> getContentVietnamNet(String link) {
		ArrayList<String> row = new ArrayList<>();
		// Tạo biến lưu giữ nội dung bài báo
		String content = "";
		/*//Tạo biến lưu tên của hình ảnh trong bài báo
		String imageList = "";*/
		try {
			// Lay het noi dung HTML cua trang co URL = currentURL
			Document doc = Jsoup.connect(link).userAgent("Mozilla").cookie("auth", "token").timeout(50000).get();

			// Lay cac element la div trong doc
			Elements elements = doc.getElementsByTag("div");
			for (Element div : elements) {
				if(div.attr("class").trim().equals("Main-Container") && div.tagName().trim().equals("div")){
					Elements mainContainers = div.children();
					for(Element mainContainer : mainContainers){
						if(mainContainer.attr("class").trim().equals("Main-Body DetailPage w-1000") && mainContainer.tagName().trim().equals("div")){
							Elements w1000s = mainContainer.children();
							for(Element w1000 : w1000s){
								if(w1000.attr("class").trim().equals("HomeBlock clearfix") && w1000.tagName().trim().equals("div")){
									Elements clearfixs = w1000.children();
									for(Element clearfix :clearfixs){
										if(clearfix.attr("class").trim().equals("w-500 left m-r-10") && clearfix.tagName().trim().equals("div")){
											Elements w500s = clearfix.children();
											for(Element w500 : w500s){
												if(w500.attr("class").trim().equals("HomeBlockLeft left") && w500.tagName().trim().equals("div")){
													Elements lefts = w500.children();
													for(Element left : lefts){
														if(left.attr("class").trim().equals("ArticleDetail") && left.tagName().trim().equals("div")){
															Elements articleDetails = left.children();
															for(Element articleDetail : articleDetails){
																// Lấy title
																if(articleDetail.attr("class").trim().equals("title") && articleDetail.tagName().trim().equals("h1")){
																	row.add(articleDetail.text().trim().replace("\u00a0", " "));
																	System.out.println(articleDetail.text().trim().replace("\u00a0", " ") + " \r\n");
																	continue;
																}
																// Lấy time
																if(articleDetail.attr("class").trim().equals("ArticleDateTime") && articleDetail.tagName().trim().equals("div")){
																	String[] a = articleDetail.text().replace("\u00a0", " ").trim().split("\\s+");
																	System.out.println(a[0].trim().replace("\u00a0", " ") + " \r\n");
																	row.add(a[0].trim().replace("\u00a0", " "));
																	continue;
																}
																// Lấy subTitle; photo; subPhoto và content
																if(articleDetail.attr("class").trim().equals("ArticleContent") && articleDetail.tagName().trim().equals("div")){
																	Elements articleContents = articleDetail.children();
																	for(int i = 0; i < articleContents.size(); i++){
																		Element articleContent = articleContents.get(i);
																		// Lấy sub-title
																		if(articleContent.tagName().trim().equals("p") && i == 0){
																			System.out.println(articleContent.text().trim().replace("\u00a0", " ") + " \r\n");
																			row.add(articleContent.text().trim().replace("\u00a0", " "));
																			continue;
																		}
																		// Lấy photo và subPhoto
																		if(articleContent.attr("class").trim().contains("image") && articleContent.tagName().trim().equals("table")){
																			continue;
																		}
																		// Lấy content
																		if(articleContent.tagName().trim().equals("p") && i != 0){
																			if(articleContent.text().trim().length() > 1){
//																				System.out.println(articleContent.text().trim().replace("\u00a0", " ") + " \r\n");
																				content = content + articleContent.text().replace("\u00a0", " ") + " \r\n";
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(content == ""){
			content = "N/A";
		}
		System.out.println(content);
		row.add(content.trim());
		row.add("VietnamNet");
		return row;
	}
}
