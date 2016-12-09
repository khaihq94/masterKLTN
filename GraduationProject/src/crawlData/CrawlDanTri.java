package crawlData;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import helperMethod.HelperMethod;

public class CrawlDanTri {
	
	HelperMethod helper = new HelperMethod();

	/*
	 * Danh sách các link chủ đề
	 */
	public ArrayList<ArrayList<String>> addUrl() {
		ArrayList<ArrayList<String>> URLs = new ArrayList<ArrayList<String>>();
		ArrayList<String> tmp = new ArrayList<>();
		/*tmp.add("http://dantri.com.vn/the-gioi.htm");
		tmp.add("thegioi");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		
		tmp.add("http://dantri.com.vn/suc-khoe.htm");
		tmp.add("suckhoe");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		
		tmp.add("http://dantri.com.vn/kinh-doanh.htm");
		tmp.add("kinhdoanh");
		URLs.add(tmp);
		tmp = new ArrayList<>();*/
		
		tmp.add("http://dantri.com.vn/the-thao.htm");
		tmp.add("thethao");
		URLs.add(tmp);
		tmp = new ArrayList<>();
		
		tmp.add("http://dantri.com.vn/suc-manh-so/dien-thoai.htm");
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
	public ArrayList<ArrayList<String>> getLinksDanTri() {
		ArrayList<ArrayList<String>> URLs = addUrl();
		ArrayList<ArrayList<String>> link = new ArrayList<ArrayList<String>>();
		ArrayList<String> tmp = new ArrayList<>();
		for (int i = 0; i < URLs.size(); i++) {
			try {
				// Lay het noi dung HTML cua trang co URL = currentURL
				Document doc = Jsoup.connect(URLs.get(i).get(0)).data("query", "Java").userAgent("Mozilla").cookie("auth", "token")
						.timeout(50000).get();

				// Lay cac element la div trong doc
				Elements forms = doc.getElementsByTag("form");
				// Duyệt qua từng element
				for (Element form : forms) {
					if(form.attr("name").trim().equals("aspnetForm")){
						Elements aspnetForms = form.children();
						for(Element aspnetForm : aspnetForms){
							if(aspnetForm.attr("class").trim().equals("wrapper") && aspnetForm.tagName().trim().equals("div")){
								Elements wrappers = aspnetForm.children();
								for(Element wrapper : wrappers){
									if(wrapper.attr("class").trim().equals("container") && wrapper.tagName().trim().equals("div")){
										Elements containers = wrapper.children();
										for(Element container : containers){
											if(container.attr("class").trim().equals("clearfix") && container.tagName().trim().equals("div")){
												Elements clearfixs = container.children();
												for(Element clearfix : clearfixs){
													if(clearfix.attr("class").trim().equals("fl wid470") && clearfix.tagName().trim().equals("div")){
														Elements wid470s = clearfix.children();
														for(Element wid470 : wid470s){
															//Lấy link trong div class mt3 clearfix
															if(wid470.attr("class").trim().equals("mt3 clearfix") && wid470.tagName().equals("div")){
																Elements mt3s = wid470.children();
																for(Element mt3 : mt3s){
																	//Lấy link trong thẻ a
																	if(mt3.tagName().trim().equals("a")){
																		tmp.add("http://dantri.com.vn" + mt3.attr("href"));
																		tmp.add(URLs.get(i).get(1));
																		link.add(tmp);
																		tmp = new ArrayList<>();
																	}
																	//Lấy link trong thẻ mr1
																	if(mt3.attr("class").trim().equals("mr1") && mt3.tagName().trim().equals("div")){
																		Elements mr1s = mt3.children();
																		for(Element mr1 : mr1s){
																			if(mr1.attr("class").trim().equals("fon5 wid255 fl") && mr1.tagName().trim().equals("div")){
																				Elements wid255s = mr1.children();
																				for(Element wid255 : wid255s){
																					if(wid255.tagName().trim().equals("a")){
																						tmp.add("http://dantri.com.vn" + wid255.attr("href"));
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
															//Lấy link trong div class clearfix
															if(wid470.attr("class").trim().equals("clearfix") && wid470.tagName().equals("div")){
																Elements clearfixs1 = wid470.children();
																for(Element clearfix1: clearfixs1){
																	if(clearfix1.attr("class").trim().equals("mt3 clearfix eplcheck") && clearfix1.tagName().trim().equals("div")){
																		Elements mt3s = clearfix1.children();
																		for(Element mt3 : mt3s){
																			//Lấy link trong a
																			if(mt3.tagName().trim().equals("a")){
																				tmp.add("http://dantri.com.vn" + mt3.attr("href"));
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
	public ArrayList<String> getContentDantri(ArrayList<String> link) {
		ArrayList<String> row = new ArrayList<>();
		row.add(link.get(0));
		//Nhập các giá trị vào biến tạm, do khi lấy thì Time sẽ đc add vào phần tử 0 thay vì 1 như mặc định output
		ArrayList<String> tmp = new ArrayList<>();
		// Tạo biến lưu giữ nội dung bài báo
		String content = "";
		/*//Tạo biến lưu tên của hình ảnh trong bài báo
		String imageList = "";*/
		try {
			// Lay het noi dung HTML cua trang co URL = currentURL
//			Document doc = Jsoup.parse(new URL(link.get(0)).openStream(), "UTF-8", link.get(0));
			Document doc = Jsoup.connect(link.get(0)).data("query", "Java").userAgent("Mozilla").cookie("auth", "token")
					.timeout(50000).get();
			// Lay cac element la div trong doc
			Elements elements = doc.getElementsByTag("div");
			for(Element div : elements){
				if(div.attr("class").trim().equals("wrapper") && div.tagName().equals("div")){
					Elements wrappers = div.children();
					for(Element wrapper : wrappers){
						if(wrapper.attr("class").trim().equals("container") && wrapper.tagName().equals("div")){
							Elements containers = wrapper.children();
							for(Element container : containers){
								if(container.attr("class").trim().equals("clearfix") && container.tagName().equals("div")){
									Elements clearfixs = container.children();
									for(Element clearfix : clearfixs){
										if((clearfix.attr("class").trim().equals("fl wid470") && clearfix.tagName().equals("div")) || (clearfix.attr("class").trim().equals("fl wid620") && clearfix.tagName().equals("div"))){
											Elements wid470s = clearfix.children();
											for(Element wid470 : wid470s){
												if(wid470.attr("id").trim().equals("ctl00_IDContent_Tin_Chi_Tiet") && wid470.tagName().equals("div")){
													Elements ct100s = wid470.children();
													for(Element ct100 : ct100s){
														if(ct100.attr("class").trim().equals("clearfix") && ct100.tagName().equals("div")){
															Elements divContents = ct100.children();
															for(Element divContent : divContents){
																//Lấy title
																if(divContent.attr("class").trim().equals("fon31 mgb15") && divContent.tagName().equals("h1")){
																	System.out.println(divContent.text().trim().replace("\u00a0", " ") + " \r\n");
																	tmp.add(divContent.text().trim().replace("\u00a0", " "));
																	continue;
																}
																//Lấy time
																if(divContent.attr("class").trim().equals("box26 clearfix") && divContent.tagName().equals("div")){
																	Elements box26s = divContent.children();
																	for(Element box26 : box26s){
																		if(box26.attr("class").trim().equals("fr fon7 mr2 tt-capitalize") && box26.tagName().equals("span")){
																			String[] a = box26.text().trim().split("\\s+");
																			System.out.println(a[2]);
																			tmp.add(a[2].replace("\u00a0", " "));
																			continue;
																		}
																	}
																}
																//Lấy sub-title
																if(divContent.attr("class").trim().equals("fon33 mt1 sapo") && divContent.tagName().equals("h2")){
																	System.out.println(divContent.text().trim().replace("\u00a0", " ") + " \r\n");
																	tmp.add(divContent.ownText().trim().replace("\u00a0", " "));
																	continue;
																}
																//Lấy content
																if(divContent.attr("id").trim().equals("divNewsContent") && divContent.tagName().equals("div")){
																	Elements divNewsContents = divContent.children();
																	//Biến lưu số ảnh trong 1 tin tức
																	int noImg = 0;
																	for(Element divNewsContent : divNewsContents){
																		//Lấy hình ảnh và phụ đề hình ảnh
																		if(divNewsContent.attr("type").trim().equals("Photo") && divNewsContent.tagName().equals("div")){
																			//Lấy hình ảnh
																			Element photo = divNewsContent.child(0);
																			Element img = photo.child(0);
																			//Lấy tên cho file ảnh
																			String fileName = link.get(0).replace(":", ".").replace("/", ".") + "_" + noImg + ".jpg";
																			//Lưu file ảnh vào folder dantri
																			helper.getImage(img.attr("src").trim(), "dantri", fileName);
																			//Thêm đoạn cho biết số ở vị trí này có ảnh
																			content = content + "img=" + fileName  + " \r\n";
																			/*//Thêm tên ảnh vào danh sách file hình
																			imageList = imageList + fileName + " ";*/
																			//Tăng biến lưu số ảnh trong 1 tin tức
																			noImg++;
//																			System.out.println("img=" + fileName  + " \r\n");
																			//Lấy phụ đề hình ảnh
																			Element subPhoto = divNewsContent.child(1);
																			content = content + "subPhoto=" + subPhoto.text() + " \r\n";
																			continue;
																		}
																		//Lấy nội dung báo
																		if(divNewsContent.tagName().equals("p")){
																			if(divNewsContent.text().trim().length() > 1){
//																				System.out.println(divNewsContent.text().trim().replace("\u00a0", " ") + " \r\n");
																				content = content + divNewsContent.text().replace("\u00a0", " ") + " \r\n";
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
		/*if (content == "") {
			content = "N/A";
		}*/
		System.out.println(content);
		//Thêm Title vào row. title đc lấy từ fần tử thứ nhất của biến tmp
		row.add(tmp.get(1).trim());
		//Thêm Time vào row. title đc lấy từ fần tử thứ 0 của biến tmp
		row.add(tmp.get(0).trim());
		//Thêm Sub-title vào row. title đc lấy từ fần tử thứ 2 của biến tmp
		row.add(tmp.get(2).trim());
		//Thêm content
		row.add(content.trim());
/*		//Thêm danh sách image
		row.add(imageList.trim());*/
		//Thêm tên báo
		row.add("dantri");
		//Thêm Chủ đề
		row.add(link.get(1));
		return row;
	}
}
