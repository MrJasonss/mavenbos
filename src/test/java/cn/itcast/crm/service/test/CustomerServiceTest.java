package cn.itcast.crm.service.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;

/**
 * 测试 Hessian 远程服务的访问
 * 
 * @author Mr_Jc
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerServiceTest {
	@Autowired
	private CustomerService customerService;

	@Test
	public void demo1() {
		// 查询未指定定区的客户
		List<Customer> customers = customerService.findNoAssociationCustomers();
		System.out.println(customers.size());
		System.out.println(customers);
	}

	@Test
	public void demo2() {
		// 查询 指定定区关联的客户
		List<Customer> customers = customerService.findHasAssociationCustomers("DQ001");
		System.out.println(customers.size());
		System.out.println(customers);
	}

	@Test
	public void demo3() {
		// 将客户关联到定区上
		String[] customerIds = { "sadfasdf" };
		String decidedZoneId = "DQ002";

		customerService.assignedCustomerToDecidedZone(customerIds, decidedZoneId);
	}
	@Test
	public void demo4(){
		//根据地址查询定区编码
		String decidecZoneId = customerService.findDecidedZoneIdByCustomerAddress("内蒙古");
		System.out.println(decidecZoneId);
	}
}
