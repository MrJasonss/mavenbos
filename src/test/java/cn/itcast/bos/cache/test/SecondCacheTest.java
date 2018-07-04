package cn.itcast.bos.cache.test;

import javax.swing.Spring;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.utils.MD5Utils;

/**
 * 
 * 二级缓存问题
 * @author Mr_Jc
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")

public class SecondCacheTest {

	@Autowired
	
	private SessionFactory sessionFactory;
	
	@Test
	public void demo1(){
		
		Session session1 = sessionFactory.openSession();
		User user1 = (User) session1.getNamedQuery("User.login").setParameter(0, "aaa").setParameter(1, MD5Utils.md5("aaa")).uniqueResult();
		System.out.println(user1.getRole().getFunctions().size());
		session1.close();
		System.out.println("=====================");
		Session session2 = sessionFactory.openSession();
		User user2 = (User) session2.getNamedQuery("User.login").setParameter(0, "bbb").setParameter(1, MD5Utils.md5("bbb")).uniqueResult();
		System.out.println(user2.getRole().getFunctions().size());
		session2.close();
	}
}
