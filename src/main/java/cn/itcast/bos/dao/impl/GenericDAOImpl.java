package cn.itcast.bos.dao.impl;

import java.io.Serializable;
import java.util.List;



import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.impl.FullTextSessionImpl;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.bos.dao.GenericDAO;
import cn.itcast.bos.page.PageResponseBean;

/**
 * 通用DAO 实现
 * 
 * @author Mr_Jc
 * 
 */
//继承HibernateDaoSupport就不用注入sessionFactory对象了
public class GenericDAOImpl<T> extends HibernateDaoSupport implements GenericDAO<T> {

	private String className;

	public GenericDAOImpl(String className) {
		this.className = className;
	}

	@Override
	public void save(T obj) {
		// this.getSession(); // 使用原始 hibernate编程方式
		// this.getHibernateTemplate(); // 使用 Spring 提供模板工具类
		//两种方法都可以  现在使用第二种方法
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public void update(T obj) {
		this.getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(T obj) {
		this.getHibernateTemplate().delete(obj);
	}

	@Override
	// DbUitls new BeanHandler<User>(User.class);
	public T findById(Serializable id) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return (T) this.getHibernateTemplate().get(clazz, id);
	}

	@Override
	public List<T> findAll() {
		return this.getHibernateTemplate().find("from " + className);// 注意空格
	}

	@Override
	public List<T> findByNamedQuery(String queryName, Object... values) {
		return this.getHibernateTemplate().findByNamedQuery(queryName, values);
	}

	@Override
	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		return this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	@Override
	public long findTotalCount(DetachedCriteria detachedCriteria) {
		// select count(*) from bc_standard
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> list = this.getHibernateTemplate().findByCriteria(detachedCriteria, 0, 1);
		return list.get(0);
	}

	@Override
	public List<T> pageQuery(DetachedCriteria detachedCriteria, int firstResult, int maxResults) {
		return this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
	}

	@Override
	public void saveOrUpdate(T obj) {
		this.getHibernateTemplate().saveOrUpdate(obj);
	}

	@Override
	@SuppressWarnings("all")
	public PageResponseBean queryByLucene(String conditionName, String conditionValue, int page, int rows) {
		// Hibernate Search 编程步骤
		// 1、 获得Session
		Session session = this.getSession();
		// 2、 获得全文检索Session
		FullTextSession fullTextSession = new FullTextSessionImpl(session);
		// 3、编写lucene的Query对象 （词条模糊搜索）
		Query query = new WildcardQuery(new Term(conditionName, "*" + conditionValue + "*"));
		// 4、获得全文检索的Query
		FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query);

		PageResponseBean pageResponseBean = new PageResponseBean();
		// 查询总记录数
		pageResponseBean.setTotal(fullTextQuery.getResultSize());

		// 当前页数据
		int firstResult = (page - 1) * rows;
		int maxResults = rows;
		List list = fullTextQuery.setFirstResult(firstResult).setMaxResults(maxResults).list();
		pageResponseBean.setRows(list);

		return pageResponseBean;
	}

	
	//执行这条数据库语句   得出省名称  和省下面的城市  用于计算比例图
	@Override
	public List<Object> findSubareasGroupByProvince() {
		// TODO Auto-generated method stub
		String hql = "SELECT r.province ,count(*) FROM Subarea s LEFT OUTER JOIN s.region r Group BY r.province";
		return (List<Object>) this.getHibernateTemplate().find(hql);
	}

}
