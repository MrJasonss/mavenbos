package cn.itcast.crm.domain;

import java.io.Serializable;

/**
 * 客户信息
 * 
 * @author Mr_Jc
 * 
 */
public class Customer implements Serializable {
	private String id;
	private String name;
	private String telephone;
	private String station;
	private String address;

	private String decidedZoneId;// 关联定区
	

	public Customer() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDecidedZoneId() {
		return decidedZoneId;
	}

	public void setDecidedZoneId(String decidedZoneId) {
		this.decidedZoneId = decidedZoneId;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public Customer(String id, String name, String telephone, String station,
			String address, String decidedZoneId) {
		this.id = id;
		this.name = name;
		this.telephone = telephone;
		this.station = station;
		this.address = address;
		this.decidedZoneId = decidedZoneId;
	}

	@Override
	public String toString() {
		return "用户 [编号=" + id + ", 姓名=" + name + ", 电话="
				+ telephone + ", 单位=" + station + ", 地址=" + address
				+ ", 关联定区=" + decidedZoneId + "]";
	}
	
	

}
