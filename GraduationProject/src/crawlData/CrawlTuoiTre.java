package crawlData;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlTuoiTre {

	/*
	 * Danh sách các link chủ đề
	 */
	public ArrayList<String> addUrl() {
		ArrayList<String> URLs = new ArrayList<String>();
		URLs.add("http://tuoitre.vn/tin/chinh-tri-xa-hoi");
		return URLs;
	}

	/*
	 * 
	 * Lấy tất cả các link tin tức trong link chủ đề
	 * 
	 * Output: biến trả ra danh sách các link tin tức thuộc chủ đề
	 * 
	 */
	public ArrayList<String> getLinkItemsTuoiTre() {
		ArrayList<String> URLs = addUrl();
		ArrayList<String> link = new ArrayList<String>();
		for (String url : URLs) {
			try {
				// Lay het noi dung HTML cua trang co URL = currentURL
				Document doc = Jsoup.connect(url).data("query", "Java").userAgent("Mozilla").cookie("auth", "token")
						.timeout(50000).get();

				// Lay cac element la div trong doc
				Elements elements = doc.getElementsByTag("div");
				// Duyet qua tung element
				for (Element e : elements) {
					// Kiem tra neu element do co Attribute la "Class"
					if (e.hasAttr("class")) {
						// Kiem tra noi dung Attribute class cua element do co
						// phai
						// la "left-side" khong
						if (e.attr("class").trim().equals("left-side")) {
							// Lay ra cac element con cua element truoc do
							Elements div_leftside = e.children();

							for (Element div_leftside_children : div_leftside) {

								//Xu ly lay du lieu cua the co class la"block-feature"
								if (div_leftside_children.attr("class").trim().equals("block-feature")) {
									Elements div_children = div_leftside_children.children();

									for (Element a : div_children) {
										if (a.tagName().trim().equals("a")) {
											// System.out.println(a.attr("href")
											// +
											// "\n");
											// Nếu link tìm thấy có bao gồm link
											// gốc
											// thì thêm link đó vào mảng
											// VD: link gốc là
											// "http://tuoitre.vn/tin/chinh-tri-xa-hoi".
											// Nếu link tìm thấy
											// có chứa
											// "http://tuoitre.vn/tin/chinh-tri-xa-hoi"
											// mới thêm vào mảng
											if (a.attr("href").contains(url)) {
												link.add(a.attr("href"));
											}
										}
									}
								}

								/*
								 * TODO Xu ly lay du lieu cua the co class la
								 * "list-news"
								 * 
								 */
								if (div_leftside_children.attr("class").trim().equals("list-news")) {

									// System.out.println(div_leftside_children.toString());
									Elements div_children = div_leftside_children.children();
									for (Element li : div_children) {
										Elements li_children = li.children();
										for (Element a : li_children) {
											if (a.tagName().trim().equals("a")) {
												// System.out.println(a.attr("href"));
												// Nếu link tìm thấy có bao gồm
												// link
												// gốc thì thêm link đó vào mảng
												// VD: link gốc là
												// "http://tuoitre.vn/tin/chinh-tri-xa-hoi".
												// Nếu link tìm thấy
												// có chứa
												// "http://tuoitre.vn/tin/chinh-tri-xa-hoi"
												// mới thêm vào mảng
												if (a.attr("href").contains(url)) {
													link.add(a.attr("href"));
												}
											}
										}
									}
								}

								/*
								 * Xu ly lay du lieu cua the co class la
								 * "highlight highlight-2"
								 * 
								 */
								if (div_leftside_children.attr("class").trim().equals("highlight highlight-2")) {
									Elements div_highlight = div_leftside_children.children();
									for (Element div_newhot_most_content : div_highlight) {
										if (div_newhot_most_content.attr("id").trim().equals("newhot_most_content")) {
											Elements div_newhot_most_content_children = div_newhot_most_content
													.children();
											for (Element div : div_newhot_most_content_children) {
												Elements a = div.children();
												for (Element _a : a) {
													if (_a.tagName().trim().equals("a")
															&& _a.attr("class").trim().contains("img")) {
														// System.out.println(_a.attr("href"));
														// Nếu link tìm thấy có
														// bao
														// gồm link gốc thì thêm
														// link đó vào mảng
														// VD: link gốc là
														// "http://tuoitre.vn/tin/chinh-tri-xa-hoi".
														// Nếu link tìm thấy
														// có chứa
														// "http://tuoitre.vn/tin/chinh-tri-xa-hoi"
														// mới thêm vào mảng
														if (_a.attr("href").contains(url)) {
															link.add(_a.attr("href"));
														}
													}
												}
											}
										}

									}
								}
							}
						}
						// Kiem tra noi dung Attribute class cua element do co
						// phai
						// la "right-side" khong
						if (e.attr("class").trim().equals("right-side")) {
							// Lay ra cac element con cua element truoc do
							Elements rightside_childrens = e.children();

							for (Element rightside_children : rightside_childrens) {
								/*
								 * TODO Xử lý thẻ div có class là block-1
								 * 
								 */
								if (rightside_children.attr("class").trim().equals("block-1")
										&& rightside_children.tagName().trim().equals("div")) {
									Elements block1_childrens = rightside_children.children();

									for (Element block1_children : block1_childrens) {

										if (block1_children.tagName().trim().equals("h4")) {
											Element h4_children = block1_children.child(0);
											// System.out.println(h4_children.attr("href")+
											// "\n" + h4_children.ownText());
											// Nếu link tìm thấy có bao gồm link
											// gốc
											// thì thêm link đó vào mảng
											// VD: link gốc là
											// "http://tuoitre.vn/tin/chinh-tri-xa-hoi".
											// Nếu link tìm thấy
											// có chứa
											// "http://tuoitre.vn/tin/chinh-tri-xa-hoi"
											// mới thêm vào mảng
											if (h4_children.attr("href").contains(url)) {
												link.add(h4_children.attr("href"));
											}
										}
									}
								}
								/*
								 * TODO Xử lý thẻ div có class là scroll-pane
								 * scroll-pane-4 jspScrollable
								 * 
								 */
								if (rightside_children.attr("class").trim().equals("scroll-pane scroll-pane-4")
										&& rightside_children.tagName().trim().equals("div")) {
									Element ul = rightside_children.child(0);
									Elements lis = ul.children();
									for (Element li : lis) {
										if (li.tagName().trim().equals("li")) {
											Element a = li.child(0);
											// System.out.println(a.attr("href"));
											// Nếu link tìm thấy có bao gồm link
											// gốc
											// thì thêm link đó vào mảng
											// VD: link gốc là
											// "http://tuoitre.vn/tin/chinh-tri-xa-hoi".
											// Nếu link tìm thấy
											// có chứa
											// "http://tuoitre.vn/tin/chinh-tri-xa-hoi"
											// mới thêm vào mảng
											if (a.attr("href").contains(url)) {
												link.add(a.attr("href"));
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
		}
		return link;
	}

	public String getContentTuoitreOnline(String link) {
		// Tạo biến lưu giữ nội dung bài báo
		String content = "";
		try {
			// Lay het noi dung HTML cua trang co URL = currentURL
			Document doc = Jsoup.connect(link).data("query", "Java").userAgent("Mozilla").cookie("auth", "token")
					.timeout(50000).get();

			// Lay cac element la div trong doc
			Elements elements = doc.getElementsByTag("div");
			for (Element div : elements) {
				/*
				 * 
				 * Lấy tiêu đề của bài báo
				 * 
				 */
				if (div.attr("class").trim().equals("block-feature block-feature-1")
						&& div.tagName().trim().equals("div")) {
					Elements div_children = div.children();
					for (Element e : div_children) {
						if (e.attr("class").trim().equals("title-2") && e.tagName().trim().equals("h1")) {
							Element a = e.child(0);
							// System.out.println(a.ownText()+ "\n");
							// Thêm đoạn text vào biến content, đồng thời
							// thay
							// toàn bộ các ký tự "&nbsp;" thành " "
							content = content + a.text().replace("\u00a0", " ") + " \r\n";

						}
						/*
						 * if(e.attr("class").trim().equals("tool-bar") &&
						 * div.tagName().trim().equals("div")){ Element a =
						 * e.child(0); // System.out.println(a.ownText()+ "\n");
						 * content = content + a.text().replace("\u00a0", " ") +
						 * " \r\n"; }
						 */
						if (e.attr("class").trim().equals("txt-head") && e.tagName().trim().equals("p")) {
							// System.out.println(e.ownText());
							// Thêm đoạn text vào biến content, đồng thời
							// thay
							// toàn bộ các ký tự "&nbsp;" thành " "
							content = content + e.text().replace("\u00a0", " ") + " \r\n";
						}
					}
				}

				/*
				 * 
				 * Lấy nội dung bài báo
				 * 
				 */
				if (div.attr("class").trim().equals("fck") && div.tagName().trim().equals("div")) {
					Elements div_children = div.children();
					for (Element p : div_children) {
						// System.out.println(p.ownText());
						// Thêm đoạn text vào biến content, đồng thời thay
						// toàn
						// bộ các ký tự "&nbsp;" thành " "
						content = content + p.text().replace("\u00a0", " ") + " \r\n";
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return content;
	}
}
