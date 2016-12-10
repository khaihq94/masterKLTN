package helperMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.Tblnews;
import model.Tblsubject;
import util.HibernateUtil;

public class DataHelper {
	Session session = null;
	List<Tblnews> newsList;
	DataHelper helper;

	public DataHelper() {
		// TODO Auto-generated constructor stub
		// this.session= HibernateUtil.getSessionFactory().getCurrentSession();
		this.session = HibernateUtil.getSessionFactory().openSession();
	}

	// Ham lay danh sach tin tức
	@SuppressWarnings("unchecked")
	public List<Tblnews> getNewsList() {
		newsList = new ArrayList<Tblnews>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query q = session.createQuery("From Tblnews");
			newsList = (List<Tblnews>) q.list();
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			newsList = null;
			tx.rollback();
			e.printStackTrace();
		}
		return newsList;
	}

	// Ham lay danh sach tin tức theo chủ đề
	@SuppressWarnings("unchecked")
	public List<Tblnews> getNewsListBySubjectId(String subjectID) {
		newsList = new ArrayList<Tblnews>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query q = session.createQuery("From Tblnews WHERE `SubjectID` = '" + subjectID + "'");
			newsList = (List<Tblnews>) q.list();
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			newsList = null;
			tx.rollback();
			e.printStackTrace();
		}
		return newsList;
	}

	// Ham lay danh sach tin tức từ trước ngày hiện tại 3 ngày đến nay
	@SuppressWarnings("unchecked")
	public List<Tblnews> getNewsListFromLast3DaysBySubjectId(String subjectId) {
		newsList = new ArrayList<Tblnews>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query q = session.createQuery("From Tblnews WHERE SubjectID = '" + subjectId
					+ "' AND PostTime >= curdate() - 3 AND PostTime <= curdate()");
			newsList = (List<Tblnews>) q.list();
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			newsList = null;
			tx.rollback();
			e.printStackTrace();
		}
		return newsList;
	}

	// Hàm lấy danh sách tin tức theo link
	@SuppressWarnings("unchecked")
	public List<Tblnews> getNewsByLink(String link) {
		List<Tblnews> news = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query q = session.createQuery("from Tblnews where Link='" + link + "'");
			news = (List<Tblnews>) q.list();
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			tx.rollback();
			e.printStackTrace();
		}
		return news;
	}

	// Ham lay danh sach theo NewsID
	public Tblnews getNewsByID(int NewsId) {
		Tblnews news = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query q = session.createQuery("from Tblnews as news where Tblnews.newsId=" + NewsId);
			news = (Tblnews) q.uniqueResult();
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			tx.rollback();
			e.printStackTrace();
		}
		return news;
	}

	// Hàm insert vào tblNews
	public void insertTblNews(String link, String title, Date postTime, String subTitle, String content,
			String newsName, String subjectId) {
		SessionFactory sessionfactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionfactory.openSession();
		Transaction tx = session.beginTransaction();
		Tblsubject sj = new Tblsubject();
		sj.setSubjectId(subjectId);
		// sj.setSubjectName("Thế Giới");
		Tblnews tt = new Tblnews();
		tt.setLink(link);
		tt.setPostTime(postTime);
		tt.setTitle(title);
		tt.setSubTitle(subTitle);
		tt.setContent(content);
		tt.setNewsName(newsName);
		tt.setTblsubject(sj);
		session.save(tt);
		tx.commit();
		session.close();
	}

}
