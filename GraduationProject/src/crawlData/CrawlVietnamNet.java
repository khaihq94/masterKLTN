package crawlData;

import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import helperMethod.HelperMethod;

public class CrawlVietnamNet {
	
	HelperMethod helper = new HelperMethod();
	
	/*
	 * Danh sách các link chủ đề
	 */
	public ArrayList<ArrayList<String>> addUrl() {
		ArrayList<ArrayList<String>> URLs = new ArrayList<ArrayList<String>>();
		ArrayList<String> tmp = new ArrayList<>();
		tmp.add("http://vietnamnet.vn/vn/kinh-doanh/");
		tmp.add("kinhdoanh");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		
		tmp.add("http://vietnamnet.vn/vn/the-gioi/");
		tmp.add("thegioi");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		
		tmp.add("http://vietnamnet.vn/vn/the-thao/");
		tmp.add("thethao");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		
		tmp.add("http://vietnamnet.vn/vn/cong-nghe/");
		tmp.add("congnghe");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		
		tmp.add("http://vietnamnet.vn/vn/suc-khoe/");
		tmp.add("suckhoe");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		return URLs;
	}

	/*
	 * 
	 * Lấy tất cả các link tin tức trong link chủ đề
	 * 
	 * Output: biến trả ra danh sách các link tin tức thuộc chủ đề
	 * 
	 */
	public ArrayList<ArrayList<String>> getLinksVietnamnet() {
		ArrayList<ArrayList<String>> URLs = addUrl();
		ArrayList<ArrayList<String>> link = new ArrayList<ArrayList<String>>();
		ArrayList<String> tmp = new ArrayList<>();
		for (int i = 0; i < URLs.size(); i++) {
			try {
				// Lay het noi dung HTML cua trang co URL = currentURL
				Document doc = Jsoup.connect(URLs.get(i).get(0)).userAgent("Mozilla").cookie("auth", "token")
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
																						tmp.add("http://vietnamnet.vn" + f20.attr("href"));
																						tmp.add(URLs.get(i).get(1));
																						link.add(tmp);
																						tmp = new ArrayList<>();
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
																						tmp.add("http://vietnamnet.vn" + dotter.attr("href"));
																						tmp.add(URLs.get(i).get(1));
																						link.add(tmp);
																						tmp = new ArrayList<>();
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
																				tmp.add("http://vietnamnet.vn" + item.attr("href"));
																				tmp.add(URLs.get(i).get(1));
																				link.add(tmp);
																				tmp = new ArrayList<>();
																			}
																		}
																	}
																}
															}
														}
													} else {
														if (homeBlockLeft.attr("class").trim().equals("ListArticle") && homeBlockLeft.tagName().trim().equals("ul")) {
															Elements listArticles = homeBlockLeft.children();
															for (Element listArticle : listArticles) {
																if (listArticle.attr("class").trim().equals("item clearfix dotter") && listArticle.tagName().trim().equals("li")) {
																	Elements items = listArticle.children();
																	for (Element item : items) {
																		if (item.tagName().trim().equals("a")) {
//																			System.out.println("http://vietnamnet.vn" + item.attr("href"));
																			tmp.add("http://vietnamnet.vn" + item.attr("href"));
																			tmp.add(URLs.get(i).get(1));
																			link.add(tmp);
																			tmp = new ArrayList<>();
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
				if (link.get(i).get(0).equals(link.get(j).get(0))) {
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
	 * 		Phần tử 0 là link của tin tức
	 * 		Phần tử 1 là Tiêu đề (Title) tin
	 * 		Phần tử 2 là thời gian (Time) đăng tin
	 * 		Phần tử 3 là đoạn tóm tắt (Summary) tin
	 * 		Phần tử 4 là nội dung của cả bài báo
	 * 		Phần tử 5 là tên báo mà tin đc lấy về
	 * 		Phần tử 6 là chủ đề của tin tức
	 * */
	public ArrayList<String> getContentVietnamNet(ArrayList<String> link) {
		ArrayList<String> row = new ArrayList<>();
		// Tạo biến lưu giữ nội dung bài báo
		String content = "";
		/*//Tạo biến lưu tên của hình ảnh trong bài báo
		String imageList = "";*/
		row.add(link.get(0));
		try {
			// Lay het noi dung HTML cua trang co URL = currentURL
//			Document doc = Jsoup.parse(new URL(link.get(0)).openStream(), "UTF-8", link.get(0));
			Document doc = Jsoup.connect(link.get(0)).userAgent("Mozilla").cookie("auth", "token")
					.timeout(50000).get();
			// Lay cac element la div trong doc
			Elements elements = doc.getElementsByTag("div");
			for (Element div : elements) {
				if(div.attr("class").trim().equals("Main-Container") && div.tagName().trim().equals("div")){
					Elements mainContainers = div.children();
					for(Element mainContainer : mainContainers){
						if(mainContainer.attr("class").trim().contains("Main-Body") && mainContainer.tagName().trim().equals("div")){
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
																	//Biến lưu số ảnh trong 1 tin tức
																	int noImg = 0;
																	for(int i = 0; i < articleContents.size(); i++){
																		Element articleContent = articleContents.get(i);
																		// Lấy sub-title
																		if((articleContent.tagName().trim().equals("p") && i == 0) || (articleContent.tagName().trim().equals("strong") && i == 0)){
																			System.out.println(articleContent.text().trim().replace("\u00a0", " ") + " \r\n");
																			row.add(articleContent.text().trim().replace("\u00a0", " "));
																			continue;
																		}
																		// Lấy photo và subPhoto
																		if(articleContent.attr("class").trim().contains("image") && articleContent.tagName().trim().equals("table")){
																			Element tbody = articleContent.child(0);
																			Elements trs = tbody.children();
																			// Trường hợp chỉ có photo mà ko có subPhoto 
																			if(trs.size() == 1) {
																				// Lấy photo
																				Element trPhoto = trs.get(0);
																				Element tdPhoto = trPhoto.child(0);
																				Element aPhoto = tdPhoto.child(0);
																				//Lấy tên cho file ảnh
																				String fileName = link.get(0).replace(":", ".").replace("/", ".") + "_" + noImg + ".jpg";
																				//Lưu file ảnh vào folder dantri
																				helper.getImage(aPhoto.attr("src").trim(), "vietnamnet", fileName);
																				//Thêm đoạn cho biết số ở vị trí này có ảnh
																				content = content + "img=" + fileName  + " \r\n";
																				//Thêm tên ảnh vào danh sách file hình
//																				imageList = imageList + fileName + " ";
																				//Tăng biến lưu số ảnh trong 1 tin tức
																				noImg++;
//																				System.out.println("img=" + fileName  + " \r\n");
																				continue;
																			}
																			// Trường hợp có cả photo và subPhoto
																			else if(trs.size() == 2) {
																				// Lấy photo
																				Element trPhoto = trs.get(0);
																				Element tdPhoto = trPhoto.child(0);
																				Element aPhoto = tdPhoto.child(0);
																				//Lấy tên cho file ảnh
																				String fileName = link.get(0).replace(":", ".").replace("/", ".") + "_" + noImg + ".jpg";
																				//Lưu file ảnh vào folder dantri
																				helper.getImage(aPhoto.attr("src").trim(), "vietnamnet", fileName);
																				//Thêm đoạn cho biết số ở vị trí này có ảnh
																				content = content + "img=" + fileName  + " \r\n";
																				//Thêm tên ảnh vào danh sách file hình
//																				imageList = imageList + fileName + " ";
																				//Tăng biến lưu số ảnh trong 1 tin tức
																				noImg++;
//																				System.out.println("img=" + fileName  + " \r\n");
																				
																				// Lấy subPhoto
																				Element trSubPhoto = trs.get(1);
																				Element tdSubPhoto = trSubPhoto.child(0);
																				content = content + "subPhoto=" + tdSubPhoto.text() + " \r\n";
																				continue;
																			}
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
								if(w1000.attr("class").trim().equals("CatTop clearfix") && w1000.tagName().trim().equals("div")){
									Elements catTops = w1000.children();
									for(Element catTop : catTops){
										if(catTop.attr("class").trim().equals("w-690 left") && catTop.tagName().trim().equals("div")){
											Elements w690s = catTop.children();
											for(Element w690 : w690s){
												if(w690.attr("class").trim().equals("clearfix") && w690.tagName().trim().equals("div")){
													Elements clearfixs = w690.children();
													for(Element clearfix : clearfixs){
														if(clearfix.attr("class").trim().contains("w-500") && clearfix.tagName().trim().equals("div")){
															Elements w500s = clearfix.children();
															for(Element w500 : w500s){
																if(w500.attr("class").trim().equals("ArticleDetail") && w500.tagName().trim().equals("div")){
																	Elements articleDetails = w500.children();
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
																			//Biến lưu số ảnh trong 1 tin tức
																			int noImg = 0;
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
																					Element tbody = articleContent.child(0);
																					Elements trs = tbody.children();
																					// Trường hợp chỉ có photo mà ko có subPhoto 
																					if(trs.size() == 1) {
																						// Lấy photo
																						Element trPhoto = trs.get(0);
																						Element tdPhoto = trPhoto.child(0);
																						Element aPhoto = tdPhoto.child(0);
																						//Lấy tên cho file ảnh
																						String fileName = link.get(0).replace(":", ".").replace("/", ".") + "_" + noImg + ".jpg";
																						//Lưu file ảnh vào folder dantri
																						helper.getImage(aPhoto.attr("src").trim(), "vietnamnet", fileName);
																						//Thêm đoạn cho biết số ở vị trí này có ảnh
																						content = content + "img=" + fileName  + " \r\n";
																						//Thêm tên ảnh vào danh sách file hình
//																						imageList = imageList + fileName + " ";
																						//Tăng biến lưu số ảnh trong 1 tin tức
																						noImg++;
//																						System.out.println("img=" + fileName  + " \r\n");
																						continue;
																					}
																					// Trường hợp có cả photo và subPhoto
																					else if(trs.size() == 2) {
																						// Lấy photo
																						Element trPhoto = trs.get(0);
																						Element tdPhoto = trPhoto.child(0);
																						Element aPhoto = tdPhoto.child(0);
																						//Lấy tên cho file ảnh
																						String fileName = link.get(0).replace(":", ".").replace("/", ".") + "_" + noImg + ".jpg";
																						//Lưu file ảnh vào folder dantri
																						helper.getImage(aPhoto.attr("src").trim(), "vietnamnet", fileName);
																						//Thêm đoạn cho biết số ở vị trí này có ảnh
																						content = content + "img=" + fileName  + " \r\n";
																						//Thêm tên ảnh vào danh sách file hình
//																						imageList = imageList + fileName + " ";
																						//Tăng biến lưu số ảnh trong 1 tin tức
																						noImg++;
//																						System.out.println("img=" + fileName  + " \r\n");
																						
																						// Lấy subPhoto
																						Element trSubPhoto = trs.get(1);
																						Element tdSubPhoto = trSubPhoto.child(0);
																						content = content + "subPhoto=" + tdSubPhoto.text() + " \r\n";
																						continue;
																					}
																				}
																				// Lấy content
																				if(articleContent.tagName().trim().equals("p") && i != 0){
																					if(articleContent.text().trim().length() > 1){
//																						System.out.println(articleContent.text().trim().replace("\u00a0", " ") + " \r\n");
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
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*if(content == ""){
			content = "N/A";
		}*/
		System.out.println(content);
		row.add(content.trim());
		row.add("vietnamnet");
		row.add(link.get(1));
		return row;
	}
}
