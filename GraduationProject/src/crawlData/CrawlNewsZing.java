package crawlData;

import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import helperMethod.HelperMethod;

public class CrawlNewsZing {
	
	HelperMethod helper = new HelperMethod();

	/*
	 * Danh sách các link chủ đề
	 */
	public ArrayList<ArrayList<String>> addUrl() {
		ArrayList<ArrayList<String>> URLs = new ArrayList<ArrayList<String>>();
		ArrayList<String> tmp = new ArrayList<>();
		tmp.add("http://news.zing.vn/the-gioi.html");
		tmp.add("thegioi");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		
		tmp.add("http://news.zing.vn/suc-khoe.html");
		tmp.add("suckhoe");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		
		tmp.add("http://news.zing.vn/kinh-doanh-tai-chinh.html");
		tmp.add("kinhdoanh");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		
		tmp.add("http://news.zing.vn/the-thao.html");
		tmp.add("thethao");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		
		tmp.add("http://news.zing.vn/cong-nghe.html");
		tmp.add("congnghe");
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
	public ArrayList<ArrayList<String>> getLinksNewsZing() {
		ArrayList<ArrayList<String>> URLs = addUrl();
		ArrayList<ArrayList<String>> link = new ArrayList<ArrayList<String>>();
		ArrayList<String> tmp = new ArrayList<>();
		for (int i = 0; i < URLs.size(); i++) {
			try {
				// Lay het noi dung HTML cua trang co URL = currentURL
				Document doc = Jsoup.connect(URLs.get(i).get(0)).data("query", "Java").userAgent("Mozilla").cookie("auth", "token")
						.timeout(50000).get();

				// Lay cac element la div trong doc
				Elements elements = doc.getElementsByTag("div");
				// Duyệt qua từng element
				for (Element e : elements) {
					// Kiểm tra element có attr class = "wrapper"
					if (e.hasAttr("class") && e.attr("class").trim().equals("wrapper")) {
						// Lấy các elements con
						Elements wrappers = e.children();
						for (Element wrapper : wrappers) {
							//Lấy link trong các tag có id = category và class = template1
							if (wrapper.hasAttr("id") && wrapper.attr("id").trim().equals("category")
									&& wrapper.attr("class").trim().equals("template1")) {
								Elements categories = wrapper.children();
								for (Element category : categories) {
									if (category.attr("class").trim().equals("content-wrap")) {
										Elements contentWraps = category.children();
										for (Element contentWrap : contentWraps) {
											// Lấy các link trong element có
											// class featured
											if (contentWrap.attr("class").trim().equals("featured")) {
												Elements featureds = contentWrap.children();
												for (Element featured : featureds) {
													if (featured.tagName().trim().equals("article")) {
														Elements articles = featured.children();
														for (Element article : articles) {
															if (article.tagName().trim().equals("header")) {
																Elements headers = article.children();
																for (Element header : headers) {
																	if (header.attr("class").trim().equals("title")) {
																		Element title = header.child(0);
																		tmp.add("http://news.zing.vn" + title.attr("href"));
																		tmp.add(URLs.get(i).get(1));
																		link.add(tmp);
																		tmp = new ArrayList<>();
//																		System.out.println("http://news.zing.vn"
//																				+ title.attr("href"));
																	}
																}
															}
														}
													}
												}
											}
											// Lấy các link trong element có
											// class cate_content
											if (contentWrap.attr("class").trim().equals("cate_content")) {
												Elements cateContents = contentWrap.getElementsByTag("article");
												for(Element cateContent : cateContents){
													Elements articles = cateContent.children();
													for(Element article : articles){
														//Lấy link trong header
														if(article.tagName().trim().equals("header")){
															Elements headers = article.getElementsByAttributeValue("class", "title");
															for(Element header : headers){
																Elements titles = header.getElementsByAttribute("href");
																for(Element title : titles){
																	tmp.add("http://news.zing.vn" + title.attr("href"));
																	tmp.add(URLs.get(i).get(1));
																	link.add(tmp);
																	tmp = new ArrayList<>();
//																	System.out.println("http://news.zing.vn"
//																				+ title.attr("href"));
																}
															}
														}
														//Lấy lin trong ul relate
														if(article.tagName().trim().equals("ul") && article.attr("class").trim().equals("relate")){
															Elements relates = article.children();
															for(Element relate : relates){
																Elements lis = relate.getElementsByAttribute("href");
																for(Element li : lis){
																	tmp.add("http://news.zing.vn" + li.attr("href"));
																	tmp.add(URLs.get(i).get(1));
																	link.add(tmp);
																	tmp = new ArrayList<>();
//																	System.out.println("http://news.zing.vn"
//																				+ li.attr("href"));
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
							//Lấy link trong các tag có id = category và class = template2
							if (wrapper.hasAttr("id") && wrapper.attr("id").trim().equals("category")
									&& wrapper.attr("class").trim().equals("template2")) {
								Elements categories = wrapper.children();
								for (Element category : categories) {
									if (category.attr("class").trim().equals("content-wrap")) {
										Elements contentWraps = category.children();
										for (Element contentWrap : contentWraps) {
											if (contentWrap.attr("class").trim().equals("cate_content")) {
												Elements cateContents = contentWrap.children();
												for (Element cateContent : cateContents) {
													// Lấy các link trong
													// element có class featured
													if (cateContent.attr("class").trim().equals("featured")) {
														Elements featureds = cateContent.children();
														for (Element featured : featureds) {
															if (featured.tagName().trim().equals("article")) {
																Elements articles = featured.children();
																for (Element article : articles) {
																	if (article.tagName().trim().equals("header")) {
																		Elements headers = article.children();
																		for (Element header : headers) {
																			if (header.attr("class").trim()
																					.equals("title")) {
																				Element title = header.child(0);
																				tmp.add("http://news.zing.vn" + title.attr("href"));
																				tmp.add(URLs.get(i).get(1));
																				link.add(tmp);
																				tmp = new ArrayList<>();
//																				System.out.println("http://news.zing.vn"
//																						+ title.attr("href"));
																			}
																		}
																	}
																}
															}
														}
													}
													// Lấy các link trong
													// element có class
													// cate_content
													if (cateContent.attr("class").trim().equals("cate_content")) {
														Elements cateContent1s = contentWrap.getElementsByTag("article");
														for(Element cateContent1 : cateContent1s){
															Elements articles = cateContent1.children();
															for(Element article : articles){
																//Lấy link trong header
																if(article.tagName().trim().equals("header")){
																	Elements headers = article.getElementsByAttributeValue("class", "title");
																	for(Element header : headers){
																		Elements titles = header.getElementsByAttribute("href");
																		for(Element title : titles){
																			tmp.add("http://news.zing.vn" + title.attr("href"));
																			tmp.add(URLs.get(i).get(1));
																			link.add(tmp);
																			tmp = new ArrayList<>();
//																			System.out.println("http://news.zing.vn"
//																						+ title.attr("href"));
																		}
																	}
																}
																//Lấy lin trong ul relate
																if(article.tagName().trim().equals("ul") && article.attr("class").trim().equals("relate")){
																	Elements relates = article.children();
																	for(Element relate : relates){
																		Elements lis = relate.getElementsByAttribute("href");
																		for(Element li : lis){
																			tmp.add("http://news.zing.vn" + li.attr("href"));
																			tmp.add(URLs.get(i).get(1));
																			link.add(tmp);
																			tmp = new ArrayList<>();
//																			System.out.println("http://news.zing.vn"
//																						+ li.attr("href"));
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
		//Loại bỏ những link trùng nhau
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
	public ArrayList<String> getContentNewsZing(ArrayList<String> link) {
		ArrayList<String> row = new ArrayList<>();
		row.add(link.get(0));
		// Tạo biến lưu giữ nội dung bài báo
		String content = "";
		/*// Tạo biến lưu tên của hình ảnh trong bài báo
		String imageList = "";*/
		try {
			// Lay het noi dung HTML cua trang co URL = currentURL
//			Document doc = Jsoup.parse(new URL(link.get(0)).openStream(), "UTF-8", link.get(0));
			Document doc = Jsoup.connect(link.get(0)).data("query", "Java").userAgent("Mozilla").cookie("auth", "token")
					.timeout(50000).get();
			// Lay cac element la div trong doc
			Elements elements = doc.getElementsByTag("div");
			for (Element div : elements) {
				if(div.attr("class").trim().equals("page-wrapper")){
					Elements pageWrappers = div.children();
					for(Element pageWrapper : pageWrappers){
						if(pageWrapper.tagName().trim().equals("article")){
							Elements articles = pageWrapper.children();
							for(Element article : articles){
								if(article.attr("class").trim().equals("main")){
									Elements mains = article.children();
									for(Element main : mains){
										//Lấy tiêu đề và thời gian đăng tin
										if(main.attr("class").trim().equals("the-article-header")){
											Elements headers = main.children();
											for(Element header : headers){
												// Lấy tiêu đề
												if(header.attr("class").trim().equals("the-article-title cms-title")){
													row.add(header.text().trim().replace("\u00a0", " "));
													System.out.println(header.text().trim().replace("\u00a0", " ") + " \r\n");
													continue;
												}
												//Lấy thời gian 
												if(header.attr("class").trim().equals("the-article-meta")){
													Elements metas = header.children();
													for(Element meta : metas){
														if(meta.attr("class").trim().equals("the-article-publish cms-date")){
															String[] a = meta.text().trim().split("\\s+");
															System.out.println(a[1].trim().replace("\u00a0", " ") + " \r\n");
															row.add(a[1].trim().replace("\u00a0", " "));
															continue;
														}
													}
												}
											}
										}
										//Lấy đoạn tóm tắt tin tức
										if(main.attr("class").trim().equals("the-article-summary cms-desc")){
											System.out.println(main.text().trim().replace("\u00a0", " ") + " \r\n");
											row.add(main.text().trim().replace("\u00a0", " "));
											continue;
										}
										//Lấy nội dung tin
										if(main.attr("class").trim().equals("the-article-body cms-body")){
											Elements bodys = main.children();
											//Biến lưu số ảnh trong 1 tin tức
											int noImg = 0;
											for(Element body : bodys){
												//Lấy hình ảnh và phụ đề hình ảnh
												if(body.attr("class").trim().equals("picture") && body.tagName().trim().equals("table")){
													//Lấy hình ảnh
													Element tbody = body.child(0);
													Elements trs = tbody.children();
													// Trường hợp chỉ có photo mà ko có subPhoto 
													if(trs.size() == 1) {
														// Lấy photo
														Element trPhoto = trs.get(0);
														Element tdPhoto = trPhoto.child(0);
														Element img = tdPhoto.child(0);
														//Lấy tên cho file ảnh
														String fileName = link.get(0).replace(":", ".").replace("/", ".") + "_" + noImg + ".jpg";
														//Lưu file ảnh vào folder dantri
														helper.getImage(img.attr("src").trim(), "newszing", fileName);
														//Thêm đoạn cho biết số ở vị trí này có ảnh
														content = content + "img=" + fileName  + " \r\n";
														//Thêm tên ảnh vào danh sách file hình
//														imageList = imageList + fileName + " ";
														//Tăng biến lưu số ảnh trong 1 tin tức
														noImg++;
//														System.out.println("img=" + fileName  + " \r\n");
														continue;
													}
													// Trường hợp có cả photo và subPhoto
													else if(trs.size() == 2){
														// Lấy photo
														Element trPhoto = trs.get(0);
														Element tdPhoto = trPhoto.child(0);
														Element img = tdPhoto.child(0);
														//Lấy tên cho file ảnh
														String fileName = link.get(0).replace(":", ".").replace("/", ".") + "_" + noImg + ".jpg";
														//Lưu file ảnh vào folder dantri
														helper.getImage(img.attr("src").trim(), "newszing", fileName);
														//Thêm đoạn cho biết số ở vị trí này có ảnh
														content = content + "img=" + fileName  + " \r\n";
														//Thêm tên ảnh vào danh sách file hình
//														imageList = imageList + fileName + " ";
														//Tăng biến lưu số ảnh trong 1 tin tức
														noImg++;
//														System.out.println("img=" + fileName  + " \r\n");
														
														// Lấy phụ đề hình
														Element trSubPhoto = trs.get(1);
														Element caption = trSubPhoto.child(0);
														content = content + "subPhoto=" + caption.text() + " \r\n";
//														System.out.println(caption.text().trim());
														continue;
													}
												}
												//Lấy nội dung của tin tức
												if(body.tagName().trim().equals("p")){
													if(body.text().trim().length() > 1){
														System.out.println(body.text());
														content = content + body.text().trim().replace("\u00a0", " ") + " \r\n";
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
		/*if(content == ""){
			content = "N/A";
		}*/
		System.out.println(content);
		// Thêm nội dung tin tức
		row.add(content.trim());
		
		// Thêm tên báo
		row.add("newszing");
		
		// Thêm Chủ đề tin tức
		row.add(link.get(1));
		return row;
	}
}
