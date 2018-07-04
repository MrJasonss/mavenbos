package cn.itcast.bos.qbc.test;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.bos.domain.bc.Subarea;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DetachedCriteriaTest {
	@Autowired
	private SessionFactory sessionFactory;

	@Test
	public void demo1() {
		HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.createAlias("region", "r");
		detachedCriteria.add(Restrictions.like("r.district", "%闵行%"));

		detachedCriteria.setProjection(Projections.property("Subarea.*"));
		List list = hibernateTemplate.findByCriteria(detachedCriteria);
		System.out.println(list);
	}
}
