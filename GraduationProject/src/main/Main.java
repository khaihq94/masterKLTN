package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import crawlData.*;
import helperMethod.DataHelper;
import helperMethod.HelperMethod;
import model.Tblnews;
import model.Tblsubject;
import plagiarism.CheckPlagiarism;
import tokenize.Tokenize;

public class Main {
	public static void main(String[] args) {
		CrawlData crawlData = new CrawlData();
		crawlData.crawlData();
		
		/*Tokenize tokenize = new Tokenize();
		CheckPlagiarism checkPlagiarism = new CheckPlagiarism();
		String content1 = tokenize.tokenizeToString("Bộ Ngoại giao Trung Quốc hôm nay 23/11 cho biết nước này sẽ đóng vai trò nhất định trong việc thúc đẩy hội nhập kinh tế ở khu vực châu Á - Thái Bình Dương sau khi Tổng thống đắc cử Donald Trump tuyên bố Mỹ có thể rút khỏi Hiệp định Đối tác xuyên Thái Bình Dương (TPP) ngay trong ngày đầu tiên ông nhận nhiệm sở. Trong cuộc họp báo thường kỳ diễn ra tại Bắc Kinh hôm nay 23/11, khi được hỏi liệu Trung Quốc có phải là bên được hưởng lợi sau khi Mỹ rút khỏi TPP hay không, người phát ngôn Bộ Ngoại giao Trung Quốc Geng Shuang cho biết Bắc Kinh giữ thái độ cởi mở đối với bất kỳ thỏa thuận nào nhằm thúc đẩy tự do thương mại trong khu vực. Theo ông Shuang, Trung Quốc sẵn sàng hợp tác cùng các bên trong việc thúc đẩy tiến trình hội nhập kinh tế ở châu Á - Thái Bình Dương vì lợi ích của mọi quốc gia trong khu vực. “Tôi nghĩ trong tiến trình này, Trung Quốc sẽ có những đóng góp riêng và có vai trò riêng”, người phát ngôn Bộ Ngoại giao Trung Quốc nói thêm, song từ chối bình luận chi tiết. Vốn không phải là nước tham gia TPP, Trung Quốc lâu nay vẫn xem hiệp định này như một cách để Mỹ kiềm chế sức mạnh ngày càng tăng của Bắc Kinh ở châu Á - Thái Bình Dương cũng như loại nước này ra khỏi cuộc chơi toàn cầu và tập hợp các nước chống lại nền kinh tế hàng đầu khu vực. Ông Shuang cho rằng tất cả các nước ở châu Á - Thái Bình Dương nên có tiếng nói trong các vấn đề khu vực, hơn là để cho một nước đứng ra xây dựng lịch trình, đồng thời nhắc lại rằng không nên “chính trị hóa” vấn đề tự do thương mại. “Chúng tôi hy vọng tất cả các bên không nên xem xét hoặc diễn giải các thỏa thuận thương mại tự do dưới góc độ chính trị. Trong các thỏa thuận thương mại tự do không tồn tại mối quan hệ “một mất một còn”. Do vậy, các nước tham gia thỏa thuận cũng không nên bài trừ, mà hãy thúc đẩy nhau phát triển”, đại diện Bộ Ngoại giao Trung Quốc nhấn mạnh. Theo Reuters, tuyên bố rút khỏi TPP của Tổng thống đắc cử Donald Trump dường như sẽ “dọn đường” cho Trung Quốc để nước này lấp đầy khoảng trống về ảnh hưởng kinh tế và chính trị do Mỹ để lại ở châu Á. Trước đó, ông Trump từng tuyên bố TPP là “thảm hoạ tiềm tàng đối với Mỹ”. Trung Quốc, Nhật Bản và Hàn Quốc đang ở trong giai đoạn đầu của tiến trình thảo luận về một thỏa thuận thương mại 3 bên và Bắc Kinh cũng đang thúc đẩy một hiệp định thương mại khu vực châu Á, vốn không có sự tham gia của Mỹ, trong suốt 5 năm qua.");
		String content2 = tokenize.tokenizeToString("Trung Quốc sẽ “đóng vai trò nhất định” thúc đẩy hội nhập kinh tế ở châu Á – Thái Bình Dương, Bộ Ngoại giao Trung Quốc cho biết hôm 23/11. Theo Fortune, đây có lẽ là tuyên bố chính thức đầu tiên từ phía Trung Quốc, kể từ sau khi Tổng thống đắc cử Mỹ Donald Trump cho biết ông sẽ rút khỏi Hiệp định Đối tác xuyên Thái Bình Dương (TPP) ngay sau khi tiến hành nhậm chức. Khi được hỏi liệu Trung Quốc có phải là bên hưởng lợi sau khi Mỹ rút khỏi TPP hay không, người phát ngôn Bộ Ngoại giao Trung Quốc Geng Shuang cho biết Bắc Kinh giữ thái độ cởi mở với bất kỳ thỏa thuận nào nhằm thúc đẩy tự do thương mại trong khu vực. Theo người phát ngôn Geng Shuang, Trung Quốc sẵn sàng hợp tác cùng các bên trong việc thúc đẩy tiến trình hội nhập kinh tế ở khu vực châu Á - Thái Bình Dương vì lợi ích của mọi người dân trong khu vực. “Tôi nghĩ rằng, trong tiến trình này, Trung Quốc sẽ có những đóng góp riêng và có vai trò riêng”, ông nói thêm, song từ chối bình luận chi tiết. Ông Shuang cho rằng tất cả các nước ở châu Á - Thái Bình Dương nên có tiếng nói trong các vấn đề khu vực, hơn là để cho một nước đứng ra xây dựng chương trình nghị sự. Ông cũng nhắc lại quan điểm không nên “chính trị hóa” vấn đề tự do thương mại. “Chúng tôi mong tất cả các bên không xem xét hoặc diễn giải các thỏa thuận thương mại tự do dưới góc độ chính trị”, ông Shuang nói. “Không có quan hệ một mất một còn trong các thỏa thuận thương mại tự do. Các nước tham gia thỏa thuận không nên bài trừ, mà hãy thúc đẩy nhau phát triển”. Tạp chí Fortune bình luận, tuyên bố rút khỏi TPP của ông Donald Trump rõ ràng đã mở đường cho Trung Quốc thay Mỹ gánh vác lấy vai trò lãnh đạo thương mại và ngoại giao ở khu vực châu Á. Ông Trump còn coi TPP giống như một thảm họa tiềm tàng với Mỹ. Về bản chất, Hiệp định Đối tác xuyên Thái Bình Dương là một trong những yếu tố cốt lõi trong chiến lược xoay trục của chính quyền Obama về phía châu Á. Bắc Kinh luôn nói rằng chiến lược này chỉ nhằm kiềm chế sự trỗi dậy của họ. Hiện tại, Trung Quốc, Nhật Bản và Hàn Quốc đã có những bước đi đầu tiên thảo luận về một thỏa thuận thương mại ba bên. Bắc Kinh cũng đang thúc đẩy thỏa thuận thương mại khu vực với châu Á mà không bao gồm Washington. Ngoài ra, Trung Quốc cũng đã khởi xướng Hiệp định Đối tác Kinh tế Toàn diện Khu vực (RCEP). Hiệp định RCEP đã được đàm phán từ năm 2013 đến nay giữa 10 nước thành viên ASEAN với Trung Quốc, Ấn Độ, Nhật Bản, Hàn Quốc, Australia và New Zealand. Vài giờ trước tuyên bố chấn động của ông Trump, Thủ tướng Nhật Bản Shinzo Abe nói TPP \"sẽ vô nghĩa nếu thiếu đi Mỹ\". Trước đó, ông Abe từng cho biết nếu TPP không còn Mỹ, Tokyo sẽ không đàm phán lại với các nước khác, thay vào đó quay sang RCEP.");
		System.out.println(checkPlagiarism.getSimSO(content1, content2));*/
		
		/*DataHelper dataHelper = new DataHelper();
		List<Tblnews> list = dataHelper.getNewsList();
		for(Tblnews item : list){
			System.out.print(item.getNewsId() + "\t");
			System.out.print(item.getLink() + "\t");
			System.out.print(item.getPostTime() + "\t");
			System.out.print(item.getTitle() + "\t");
			System.out.print(item.getSubTitle() + "\t");
			System.out.print(item.getContent() + "\t");
			System.out.println(item.getNewsName());
			
		}*/
		
		/*String postTime = "05/12/2016";
		HelperMethod helper = new HelperMethod();
		System.out.println(helper.stringToDate(postTime));*/
	}
}
