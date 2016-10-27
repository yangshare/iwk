package com.iweike.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.iweike.dao.IwkDao;
import com.iweike.hibernate.HibernateSessionFactory;

public class IwkDaoImpl implements IwkDao {
	/**
	 * 数据层通用类 方法： 1.通过id删除（id,对象），返回boolean； 2.通过id查询（id,对象），返回object；
	 * 3.保存（对象），返回boolean； 4.修改（对象），返回boolean； 5.取等条件查询（对象类，字段，值），返回集合；
	 * 6.获取所有记录，返回集合；7.降序查询（对象类,数据库字段）;8.升序查询（对象类,数据库字段）
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

	// 通过name查询

	public int query(String name, Object object) {
		String rows = null;
		try {
			s = HibernateSessionFactory.getSession();
			Criteria c = s.createCriteria(object.getClass());
			c.add(Restrictions.eq("name", name));
			// Projections.rowCount() 来取得总记录数
			rows = c.setProjection(Projections.rowCount()).uniqueResult()
					.toString();
			return Integer.parseInt(rows);
		} catch (Exception e) {
			System.out.println("query方法异常：" + e.getMessage());
			return 0;
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
			e.printStackTrace();
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
			s.update(object);
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
	public List<Object> queryOrderDesc(Object object, String keys,
			int maxResults) {

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
	public List<Object> queryOrderAsc(Object object, String keys, int maxResults) {

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

	// 9.获取最后一条记录的id（对象类）
	public Object queryLastRecordId(Object object) {

		try {
			s = HibernateSessionFactory.getSession();
			Criteria c = s.createCriteria(object.getClass());
			c.addOrder(Order.desc("id"));
			c.setMaxResults(1);

			return c.uniqueResult();
		} catch (Exception e) {
			System.out.println("queryLastRecordId方法异常：" + e.getMessage());
			return null;
		} finally {
			s.close();
		}

	}

	// 10.分页查询记录，返回集合；
	@SuppressWarnings("unchecked")
	/*
	 * query
	 * Object object数据表
	 * int curPage,当前页数
	 *  int max,每页条数
	 *  String keys,字段
	 *  String values,字段值
	 * 
	 */
	public List<Object> query(Object object, int curPage, int max,String keys,String values) {

		try {

			s = HibernateSessionFactory.getSession();

			Criteria c = s.createCriteria(object.getClass());
			if (!values.equals("全部"))
				c.add(Restrictions.eq(keys, values));
			c.setFirstResult(curPage * max);
			c.setMaxResults(max);

			return c.list();
		} catch (Exception e) {
			System.out.println("query方法异常：" + e.getMessage());
			return null;
		} finally {
			s.close();
		}

	}


	// 11.获取该表某类型记录总条数{object表，keys字段，values字段对应值}
	public double queryRecordNum(Object object, String keys, Object values) {
		String rows = null;
		try {
			s = HibernateSessionFactory.getSession();
			Criteria c = s.createCriteria(object.getClass());
			if (!values.equals("全部"))
				c.add(Restrictions.eq(keys, values));
			// Projections.rowCount() 来取得总记录数
			rows = c.setProjection(Projections.rowCount()).uniqueResult()
					.toString();
			return Double.parseDouble(rows);
		} catch (Exception e) {
			System.out.println("query方法异常：" + e.getMessage());
			return 0;
		} finally {
			s.close();
		}

	}
	
	

}
