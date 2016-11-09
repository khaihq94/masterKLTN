package crawlData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tokenize.Tokenize;


public class CrawlData {
	
	CrawlTuoiTre crawlTuoiTre = new CrawlTuoiTre();
	Tokenize tokenize = new Tokenize();

	/*public ArrayList<String> getLinkItemsTuoiTre(String url){
		ArrayList<String> link = new ArrayList<String>();
		try {
			// Lay het noi dung HTML cua trang co URL = currentURL
			Document doc = Jsoup.connect(url).data("query","Java").userAgent("Mozilla").cookie("auth", "token").timeout(50000).get();
			
			//Lay cac element la div trong doc
			Elements elements = doc.getElementsByTag("div");
			//Duyet qua tung element
			for(Element e : elements){
				//Kiem tra neu element do co Attribute la "Class"
				if(e.hasAttr("class")){
					//Kiem tra noi dung Attribute class cua element do co phai la "left-side" khong
					if(e.attr("class").trim().equals("left-side")){
						//Lay ra cac element con cua element truoc do
						Elements div_leftside = e.children();
						
						for(Element div_leftside_children : div_leftside){
							
							TODO
							 * Xu ly lay du lieu cua the co class la "block-feature"
							 * 
							 * 
							if(div_leftside_children.attr("class").trim().equals("block-feature")){
								Elements div_children = div_leftside_children.children();
								
								for(Element a : div_children){
									if(a.tagName().trim().equals("a")){
//										System.out.println(a.attr("href") + "\n");
										//Nếu link tìm thấy có bao gồm link gốc thì thêm link đó vào mảng
										//VD: link gốc là "http://tuoitre.vn/tin/chinh-tri-xa-hoi". Nếu link tìm thấy
										// có chứa "http://tuoitre.vn/tin/chinh-tri-xa-hoi" mới thêm vào mảng
										if(a.attr("href").contains(url)){
											link.add(a.attr("href"));
										}
									}
								}
							}
							
							 TODO
							 * Xu ly lay du lieu cua the co class la "list-news"
							 * 
							 * 
							if(div_leftside_children.attr("class").trim().equals("list-news")){
								
								//System.out.println(div_leftside_children.toString());
								Elements div_children = div_leftside_children.children();
								for(Element li : div_children){
									Elements li_children = li.children();
									for(Element a : li_children){
										if(a.tagName().trim().equals("a")){
//											System.out.println(a.attr("href"));
											//Nếu link tìm thấy có bao gồm link gốc thì thêm link đó vào mảng
											//VD: link gốc là "http://tuoitre.vn/tin/chinh-tri-xa-hoi". Nếu link tìm thấy
											// có chứa "http://tuoitre.vn/tin/chinh-tri-xa-hoi" mới thêm vào mảng
											if(a.attr("href").contains(url)){
												link.add(a.attr("href"));
											}
										}
									}
								}
							}
							
							
							 * Xu ly lay du lieu cua the co class la "highlight highlight-2"
							 * 
							 * 
							if(div_leftside_children.attr("class").trim().equals("highlight highlight-2")){
								Elements div_highlight = div_leftside_children.children();
								for(Element div_newhot_most_content : div_highlight){
									if(div_newhot_most_content.attr("id").trim().equals("newhot_most_content")){
										Elements div_newhot_most_content_children = div_newhot_most_content.children();
										for(Element div : div_newhot_most_content_children){
											Elements a = div.children();
											for(Element _a : a){
												if(_a.tagName().trim().equals("a") && _a.attr("class").trim().contains("img")){
//													System.out.println(_a.attr("href"));
													//Nếu link tìm thấy có bao gồm link gốc thì thêm link đó vào mảng
													//VD: link gốc là "http://tuoitre.vn/tin/chinh-tri-xa-hoi". Nếu link tìm thấy
													// có chứa "http://tuoitre.vn/tin/chinh-tri-xa-hoi" mới thêm vào mảng
													if(_a.attr("href").contains(url)){
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
					//Kiem tra noi dung Attribute class cua element do co phai la "right-side" khong
					if(e.attr("class").trim().equals("right-side")){
						//Lay ra cac element con cua element truoc do
						Elements rightside_childrens = e.children();
						
						for(Element rightside_children : rightside_childrens){
							
							 * TODO
							 * Xử lý thẻ div có class là block-1 
							 * 
							 * 
							if(rightside_children.attr("class").trim().equals("block-1") && rightside_children.tagName().trim().equals("div")){
								Elements block1_childrens  = rightside_children.children();
								
								for(Element block1_children : block1_childrens){
									
									if(block1_children.tagName().trim().equals("h4")){
										Element h4_children = block1_children.child(0);
//										System.out.println(h4_children.attr("href")+ "\n" + h4_children.ownText());
										//Nếu link tìm thấy có bao gồm link gốc thì thêm link đó vào mảng
										//VD: link gốc là "http://tuoitre.vn/tin/chinh-tri-xa-hoi". Nếu link tìm thấy
										// có chứa "http://tuoitre.vn/tin/chinh-tri-xa-hoi" mới thêm vào mảng
										if(h4_children.attr("href").contains(url)){
											link.add(h4_children.attr("href"));
										}
									}
								}
							}
							
							 * TODO
							 * Xử lý thẻ div có class là scroll-pane scroll-pane-4 jspScrollable
							 * 
							 * 
							if(rightside_children.attr("class").trim().equals("scroll-pane scroll-pane-4") && rightside_children.tagName().trim().equals("div")){
								Element ul = rightside_children.child(0);
								Elements lis = ul.children();
								for(Element li : lis){
									if(li.tagName().trim().equals("li")){
										Element a = li.child(0);
//										System.out.println(a.attr("href"));
										//Nếu link tìm thấy có bao gồm link gốc thì thêm link đó vào mảng
										//VD: link gốc là "http://tuoitre.vn/tin/chinh-tri-xa-hoi". Nếu link tìm thấy
										// có chứa "http://tuoitre.vn/tin/chinh-tri-xa-hoi" mới thêm vào mảng
										if(a.attr("href").contains(url)){
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
		return link;
	}*/
	
	/*public String getContentTuoitreOnline(String link){
			
			//Tạo biến lưu giữ nội dung bài báo
//			String content = link + "\r\n";
			String content = "";
			try {
//				System.out.println(link);
				// Lay het noi dung HTML cua trang co URL = currentURL
				Document doc = Jsoup.connect(link).data("query","Java").userAgent("Mozilla").cookie("auth", "token").timeout(50000).get();
				
				//Lay cac element la div trong doc
				Elements elements = doc.getElementsByTag("div");
				for(Element div : elements){
					
					 * 
					 * Lấy tiêu đề của bài báo
					 * 
					 * 
					if(div.attr("class").trim().equals("block-feature block-feature-1") && div.tagName().trim().equals("div")){
						Elements div_children = div.children();
						for(Element e : div_children){
							if(e.attr("class").trim().equals("title-2") && e.tagName().trim().equals("h1")){
								Element a = e.child(0);
//								System.out.println(a.ownText()+ "\n");
								//Thêm đoạn text vào biến content, đồng thời thay toàn bộ các ký tự "&nbsp;" thành " "
								content = content + a.text().replace("\u00a0", " ") + " \r\n";
								
							}
							if(e.attr("class").trim().equals("tool-bar") && div.tagName().trim().equals("div")){
								Element a = e.child(0);
//								System.out.println(a.ownText()+ "\n");
								content = content + a.text().replace("\u00a0", " ") + " \r\n";
							}
							if(e.attr("class").trim().equals("txt-head") && e.tagName().trim().equals("p")){
//								System.out.println(e.ownText());
								//Thêm đoạn text vào biến content, đồng thời thay toàn bộ các ký tự "&nbsp;" thành " "
								content = content + e.text().replace("\u00a0", " ") + " \r\n";
							}
						}
					}
					
					
					 * 
					 * Lấy nội dung bài báo
					 * 
					 * 
					if(div.attr("class").trim().equals("fck") && div.tagName().trim().equals("div")){
						Elements div_children = div.children();
						for(Element p : div_children){
//							System.out.println(p.ownText());
							//Thêm đoạn text vào biến content, đồng thời thay toàn bộ các ký tự "&nbsp;" thành " "
							content = content + p.text().replace("\u00a0", " ") + " \r\n";
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		return content;
	}*/

	public void getContent() {
		// TODO Auto-generated method stub
		ArrayList<String> contents = crawlTuoiTre.getContentTuoitreOnline();
		tokenize.tokenize(contents);
		
		System.out.println("Done!!!");
	}
	
	/*
	 * TODO
	 * Tạo ra file chứa nội dung bài báo để chuẩn bị tokenizer
	 * 
	 * */
	public File createInputFile(String fileName){
		//lấy đường dẫn vào thư mục input
		String path = System.getProperty("user.dir") + "//vnTokenizer//input//" + fileName + ".txt";
		File file = new File(path);
		try {
			//Tạo file text
			if(file.createNewFile()){
				
			}else{
				//Nếu file đã tồn tại thì xóa file đó
				deleteInputFile(file);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	
	/*
	 * TODO
	 * Ghi nội dung bài báo vào file
	 * */
	public void writeContentIntoInputFile(File file, String content){
		//Write Content
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * TODO
	 * Xóa các file input sau khi tokenize để tiết kiệm dung lượng
	 * 
	 * */
	public void deleteInputFile(File file){
		try {
			if(file.delete()){
				System.out.println("Success!!! " + file.getName() + " is deleted");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/*
	 * TODO
	 * Xóa hết tất cả các file trong folder input
	 * 
	 * */
	public void deleteInput(){
		try {
			String path = System.getProperty("user.dir") + "\\vnTokenizer\\input";
			File file = new File(path);
			FileUtils.cleanDirectory(file);
			System.out.println("Clean up Done");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	

}
