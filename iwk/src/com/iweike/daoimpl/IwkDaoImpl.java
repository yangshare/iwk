package com.iweike.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.iweike.dao.IwkDao;
import com.iweike.hibernate.HibernateSessionFactory;

public class IwkDaoImpl implements IwkDao {
	/**
	 * 数据层通用类 方法： 1.通过id删除（id,对象），返回boolean； 2.通过id查询（id,对象），返回object；
	 * 3.保存（对象），返回boolean； 4.修改（对象），返回boolean； 5.取等条件查询（对象类，字段，值），返回集合；
	 * 6.获取所有记录，返回集合；
	 * 
	 */
	// private static Configuration configuration = new Configuration();
	// private static org.hibernate.SessionFactory sessionFactory;
	Session s = null;// 定义Session
	Transaction t = null;// 定义事物活动

	// 1.删除

	public boolean delete(int id, Object object) {
		try {
			s = HibernateSessionFactory.getSession();
			t = s.beginTransaction();
			s.delete(s.get(object.getClass(), id));
			t.commit();
			return true;
		} catch (Exception e) {
			System.out.println("delete方法异常：" + e.getMessage());
			return false;
		} finally {
			s.close();
		}

	}

	// 2.通过id查询

	public Object query(int id, Object object) {

		try {
			s = HibernateSessionFactory.getSession();
			Criteria c = s.createCriteria(object.getClass());
			c.add(Restrictions.eq("id", id));
			return c.uniqueResult();
		} catch (Exception e) {
			System.out.println("query方法异常：" + e.getMessage());
			return null;
		} finally {
			s.close();
		}

	}

	// 3.保存

	public boolean save(Object object) {

		try {
			s = HibernateSessionFactory.getSession();
			t = s.beginTransaction();
			s.save(object);
			t.commit();

			return true;
		} catch (Exception e) {
			System.out.println("save方法异常：" + e.getMessage());
			return false;
		} finally {
			s.close();
		}

	}

	// 4.修改

	public boolean update(Object object) {
		try {
			s = HibernateSessionFactory.getSession();
			t = s.beginTransaction();
			s.save(object);
			t.commit();

			return true;
		} catch (Exception e) {
			System.out.println("update方法异常：" + e.getMessage());
			return false;
		} finally {
			s.close();
		}

	}

	// 5.取等条件查询（对象类,数据库字段，条件值）

	@SuppressWarnings("unchecked")
	public List<Object> query(Object object, String keys, Object values) {

		try {

			s = HibernateSessionFactory.getSession();

			Criteria c = s.createCriteria(object.getClass());
			c.add(Restrictions.eq(keys, values));

			return c.list();
		} catch (Exception e) {
			System.out.println("query方法异常：" + e.getMessage());
			return null;
		} finally {
			s.close();
		}

	}

	// 6.获取所有记录，返回集合；
	@SuppressWarnings("unchecked")
	public List<Object> query(Object object) {

		try {

			s = HibernateSessionFactory.getSession();

			Criteria c = s.createCriteria(object.getClass());

			return c.list();
		} catch (Exception e) {
			System.out.println("query方法异常：" + e.getMessage());
			return null;
		} finally {
			s.close();
		}

	}
	
	// 7.降序查询（对象类,数据库字段）

	@SuppressWarnings("unchecked")
	public List<Object> queryOrderDesc(Object object, String keys,int maxResults) {

		try {

			s = HibernateSessionFactory.getSession();

			Criteria c = s.createCriteria(object.getClass());
			c.addOrder(Order.desc(keys));
			c.setMaxResults(maxResults);
			return c.list();
		} catch (Exception e) {
			System.out.println("query方法异常：" + e.getMessage());
			return null;
		} finally {
			s.close();
		}

	}
	
	// 8.升序查询（对象类,数据库字段）

	@SuppressWarnings("unchecked")
	public List<Object> queryOrderAsc(Object object, String keys,int maxResults) {

		try {

			s = HibernateSessionFactory.getSession();

			Criteria c = s.createCriteria(object.getClass());
			c.addOrder(Order.asc(keys));
			c.setMaxResults(maxResults);

			return c.list();
		} catch (Exception e) {
			System.out.println("query方法异常：" + e.getMessage());
			return null;
		} finally {
			s.close();
		}

	}

}
