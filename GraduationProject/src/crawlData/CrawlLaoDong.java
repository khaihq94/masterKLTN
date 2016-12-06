package crawlData;

import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import helperMethod.HelperMethod;

public class CrawlLaoDong {
	
	HelperMethod helper = new HelperMethod();

	/*
	 * Danh sách các link chủ đề
	 */
	public ArrayList<ArrayList<String>> addUrl() {
		ArrayList<ArrayList<String>> URLs = new ArrayList<ArrayList<String>>();
		ArrayList<String> tmp = new ArrayList<>();
		
		tmp.add("http://laodong.com.vn/the-gioi");
		tmp.add("thegioi");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		
		tmp.add("http://laodong.com.vn/suc-khoe");
		tmp.add("suckhoe");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		
		tmp.add("http://laodong.com.vn/kinh-te");
		tmp.add("kinhdoanh");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		
		tmp.add("http://laodong.com.vn/the-thao");
		tmp.add("thethao");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		
		tmp.add("http://laodong.com.vn/tin-nong-cong-nghe");
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
	public ArrayList<ArrayList<String>> getLinksLaoDong() {
		ArrayList<ArrayList<String>> URLs = addUrl();
		ArrayList<ArrayList<String>> link = new ArrayList<ArrayList<String>>();
		ArrayList<String> tmp = new ArrayList<>();
		for (int i = 0; i < URLs.size(); i++) {
			try {
				// Lay het noi dung HTML cua trang co URL = currentURL
				Document doc = Jsoup.connect(URLs.get(i).get(0)).userAgent("Mozilla").cookie("auth", "token").timeout(50000).get();

				// Lay cac element la div trong doc
				Elements elements = doc.getElementsByTag("div");
				// Duyệt qua từng element
				for(Element div : elements){
					if (div.hasAttr("class") && div.attr("class").trim().equals("site-wrap")) {
						Elements siteWraps = div.children();
						for(Element siteWrap : siteWraps){
							if (siteWrap.attr("class").trim().equals("container") && siteWrap.tagName().trim().equals("div")) {
								Elements containers = siteWrap.children();
								for(Element container : containers){
									if (container.attr("id").trim().equals("aspnetForm") && container.tagName().trim().equals("form")) {
										Elements aspnetForms = container.children();
										for(Element aspnetForm : aspnetForms){
											if (aspnetForm.attr("class").trim().equals("other-zones") && aspnetForm.tagName().trim().equals("div")) {
												Elements otherZones = aspnetForm.children();
												for(Element otherZone : otherZones){
													if (otherZone.attr("class").trim().equals("main") && otherZone.tagName().trim().equals("div")) {
														Elements mains = otherZone.children();
														for(Element main : mains){
															// Lấy link trong class abf clearfix
															if (main.attr("class").trim().equals("abf clearfix") && main.tagName().trim().equals("div")) {
																Elements abfs = main.children();
																for(Element abf : abfs){
																	if (abf.attr("class").trim().equals("highlight") && abf.tagName().trim().equals("div")) {
																		Elements highlights = abf.children();
																		for(Element highlight : highlights){
																			// Lấy link trong article class story feature
																			if (highlight.attr("class").trim().equals("story feature") && highlight.tagName().trim().equals("article")) {
																				Elements storyFeatures = highlight.children();
																				for(Element storyFeature : storyFeatures){
																					if (storyFeature.attr("class").trim().equals("cover") && storyFeature.tagName().trim().equals("figure")) {
																						Element a = storyFeature.child(0);
																						tmp.add("http://laodong.com.vn" + a.attr("href"));
																						tmp.add(URLs.get(i).get(1));
																						link.add(tmp);
																						tmp = new ArrayList<>();
//																						System.out.println("http://laodong.com.vn" + a.attr("href"));
																					}
																				}
																			}
																			// Lấy link trong div class more
																			if (highlight.attr("class").trim().equals("more") && highlight.tagName().trim().equals("div")) {
																				Elements mores = highlight.children();
																				for(Element more : mores){
																					if (more.attr("class").trim().equals("story") && more.tagName().trim().equals("article")) {
																						Elements storys = more.children();
																						for(Element story : storys){
																							if (story.attr("class").trim().equals("cover") && story.tagName().trim().equals("figure")) {
																								Element a = story.child(0);
																								tmp.add("http://laodong.com.vn" + a.attr("href"));
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
															// Lấy link trong class clearfix zone-6-3 zone-cate-list
															if (main.attr("class").trim().equals("clearfix zone-6-3 zone-cate-list") && main.tagName().trim().equals("div")) {
																Elements zone63s = main.children();
																for(Element zone63 : zone63s){
																	if (zone63.attr("class").trim().equals("list-article hzol-clear") && zone63.tagName().trim().equals("div")) {
																		Elements hzols = zone63.children();
																		for(Element hzol : hzols){
																			if (hzol.attr("class").trim().equals("story") && hzol.tagName().trim().equals("article")) {
																				Elements storys = hzol.children();
																				for(Element story : storys){
																					if (story.attr("class").trim().equals("cover") && story.tagName().trim().equals("figure")) {
																						Element a = story.child(0);
																						tmp.add("http://laodong.com.vn" + a.attr("href"));
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
						}
					}
				}
			} catch(Exception e){
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
	public ArrayList<String> getContentLaoDong(ArrayList<String> link) {
		ArrayList<String> row = new ArrayList<>();
		row.add(link.get(0));
		// Tạo biến lưu giữ nội dung bài báo
		String content = "";
		/*// Tạo biến lưu tên của hình ảnh trong bài báo
		String imageList = "";*/
		try {
			// Lay het noi dung HTML cua trang co URL = currentURL
			Document doc = Jsoup.parse(new URL(link.get(0)).openStream(), "UTF-8", link.get(0));

			// Lay cac element la div trong doc
			Elements elements = doc.getElementsByTag("div");
			for (Element div : elements) {
				if (div.hasAttr("class") && div.attr("class").trim().equals("site-wrap")) {
					Elements siteWraps = div.children();
					for(Element siteWrap : siteWraps){
						if (siteWrap.attr("class").trim().equals("container") && siteWrap.tagName().trim().equals("div")) {
							Elements containers = siteWrap.children();
							for(Element container : containers){
								if (container.attr("id").trim().equals("aspnetForm") && container.tagName().trim().equals("form")) {
									Elements aspnetForms = container.children();
									for(Element aspnetForm : aspnetForms){
										if (aspnetForm.attr("class").trim().equals("other-zones") && aspnetForm.tagName().trim().equals("div")) {
											Elements otherZones = aspnetForm.children();
											for(Element otherZone : otherZones){
												if (otherZone.attr("class").trim().equals("main") && otherZone.tagName().trim().equals("div")) {
													Elements mains = otherZone.children();
													for(Element main : mains){
														if (main.attr("class").trim().equals("main-article clearfix") && main.tagName().trim().equals("section")) {
															Elements clearfixs = main.children();
															for(Element clearfix : clearfixs){
																// Lấy Title và Time
																if (clearfix.tagName().trim().equals("header")) {
																	Elements headers = clearfix.children();
																	for(Element header : headers){
																		// Lấy Title
																		if (header.attr("class").trim().equals("cms-title") && header.tagName().trim().equals("h1")) {
																			row.add(header.text().trim().replace("\u00a0", " "));
																			System.out.println(header.text().trim().replace("\u00a0", " ") + " \r\n");
																			continue;
																		}
																		// Lấy time
																		if (header.attr("class").trim().equals("meta clearfix") && header.tagName().trim().equals("div")) {
																			Elements metaClearfixs = header.children();
																			for(Element metaClearfix : metaClearfixs){
																				if (metaClearfix.attr("class").trim().equals("author-time") && metaClearfix.tagName().trim().equals("div")) {
																					Elements authorTimes = metaClearfix.children();
																					for(Element authorTime : authorTimes){
																						if (authorTime.attr("class").trim().equals("cms-date") && authorTime.tagName().trim().equals("time")) {
																							String[] a = authorTime.text().trim().split("\\s+");
																							System.out.println(a[2].trim().replace("\u00a0", " ") + " \r\n");
																							row.add(a[2].trim().replace("\u00a0", " "));
																							continue;
																						}
																					}
																				}
																			}
																		}
																	}
																}
																// Lấy subTitle, Photo, subPhoto và Content
																if (clearfix.attr("class").trim().equals("col-3-6") && clearfix.tagName().trim().equals("div")) {
																	Elements col36s = clearfix.children();
																	for(Element col36 : col36s){
																		if (col36.attr("class").trim().equals("article-content") && col36.tagName().trim().equals("div")) {
																			Elements articleContents = col36.children();
																			for(Element articleContent : articleContents){
																				if (articleContent.attr("class").trim().equals("content") && articleContent.tagName().trim().equals("div")) {
																					Elements contentClasss = articleContent.children();
																					for(Element contentClass : contentClasss){
																						// Lấy subTitle
																						if (contentClass.attr("class").trim().equals("sapo cms-desc") && contentClass.tagName().trim().equals("p")) {
																							System.out.println(contentClass.text().trim().replace("\u00a0", " ") + " \r\n");
																							row.add(contentClass.text().trim().replace("\u00a0", " "));
																							continue;
																						}
																						// Lấy photo, subPhoto, content
																						if (contentClass.attr("class").trim().equals("cms-body") && contentClass.tagName().trim().equals("div")) {
																							Elements cmsBodys = contentClass.children();
																							//Biến lưu số ảnh trong 1 tin tức
																							int noImg = 0;
																							for(Element cmsBody : cmsBodys){
																								// Lấy photo và subPhoto
																								if(cmsBody.attr("class").trim().equals("contentimg") && cmsBody.tagName().trim().equals("table")){
																									Element tbody = cmsBody.child(0);
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
//																										helper.getImage(img.attr("src").trim(), "laodong", fileName);
																										//Thêm đoạn cho biết số ở vị trí này có ảnh
																										content = content + "img=" + fileName  + " \r\n";
																										/*//Thêm tên ảnh vào danh sách file hình
																										imageList = imageList + fileName + " ";*/
																										//Tăng biến lưu số ảnh trong 1 tin tức
																										noImg++;
													//													System.out.println("img=" + fileName  + " \r\n");
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
//																										helper.getImage(img.attr("src").trim(), "laodong", fileName);
																										//Thêm đoạn cho biết số ở vị trí này có ảnh
																										content = content + "img=" + fileName  + " \r\n";
																										/*//Thêm tên ảnh vào danh sách file hình
																										imageList = imageList + fileName + " ";*/
																										//Tăng biến lưu số ảnh trong 1 tin tức
																										noImg++;
													//													System.out.println("img=" + fileName  + " \r\n");
																										
																										// Lấy subPhoto
																										Element trSubPhoto = trs.get(1);
																										Element tdSubPhoto = trSubPhoto.child(0);
																										content = content + "subPhoto=" + tdSubPhoto.text() + " \r\n";
													//													System.out.println(tdSubPhoto.text().trim());
																										continue;
																									}
																								}
																								if (cmsBody.tagName().trim().equals("div") && cmsBody.attr("style").trim().equals("text-align:center;")) {
																									Element table = cmsBody.child(0);
																									Element tbody = table.child(0);
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
//																										helper.getImage(img.attr("src").trim(), "laodong", fileName);
																										//Thêm đoạn cho biết số ở vị trí này có ảnh
																										content = content + "img=" + fileName  + " \r\n";
																										/*//Thêm tên ảnh vào danh sách file hình
																										imageList = imageList + fileName + " ";*/
																										//Tăng biến lưu số ảnh trong 1 tin tức
																										noImg++;
													//													System.out.println("img=" + fileName  + " \r\n");
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
//																										helper.getImage(img.attr("src").trim(), "laodong", fileName);
																										//Thêm đoạn cho biết số ở vị trí này có ảnh
																										content = content + "img=" + fileName  + " \r\n";
																										/*//Thêm tên ảnh vào danh sách file hình
																										imageList = imageList + fileName + " ";*/
																										//Tăng biến lưu số ảnh trong 1 tin tức
																										noImg++;
													//													System.out.println("img=" + fileName  + " \r\n");
																										
																										// Lấy subPhoto
																										Element trSubPhoto = trs.get(1);
																										Element tdSubPhoto = trSubPhoto.child(0);
																										content = content + "subPhoto=" + tdSubPhoto.text() + " \r\n";
													//													System.out.println(tdSubPhoto.text().trim());
																										continue;
																									}
																								}
																								// Lấy content
																								if (cmsBody.tagName().trim().equals("p")) {
																									if(cmsBody.text().trim().length() > 1){
//																									System.out.println(cmsBody.text());
																									content = content + cmsBody.text().trim().replace("\u00a0", " ") + " \r\n";
																									}
																								}
																								if (cmsBody.tagName().trim().equals("div") && cmsBody.attr("style").trim().equals("text-align: justify;")) {
																									if(cmsBody.text().trim().length() > 1){
//																										System.out.println(cmsBody.text());
																										content = content + cmsBody.text().trim().replace("\u00a0", " ") + " \r\n";
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
						}
					}
				}
			}
		} catch(Exception e){
				e.printStackTrace();
		}
		/*if(content == ""){
			content = "N/A";
		}*/
		System.out.println(content);
		row.add(content.trim());
		row.add("laodong");
		//Thêm Chủ đề
		row.add(link.get(1));
		return row;
	}
}