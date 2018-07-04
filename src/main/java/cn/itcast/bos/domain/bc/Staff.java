package cn.itcast.bos.domain.bc;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * 取派员信息 Staff entity. @author MyEclipse Persistence Tools
 */

public class Staff implements java.io.Serializable {

	// Fields

	private String id; // hbm映射默认使用 assigned策略 （如果用户自己输入，使用assigned策略）
	private Standard standard; // 关联标准对象
	private String name; // 姓名
	private String telephone; // 电话
	private String haspda; // 是否有移动设备（快递员跟踪定位）
	private String deltag = "0"; // 删除标记 0 未作废 1 已经作废
	private String station; // 单位
	private Set decidedZones = new HashSet(0); // 负责定区

	// Constructors

	/** default constructor */
	public Staff() {
	}

	/** minimal constructor */
	public Staff(String id) {
		this.id = id;
	}

	/** full constructor */
	public Staff(String id, Standard standard, String name, String telephone, String haspda, String deltag, String station, Set decidedZones) {
		this.id = id;
		this.standard = standard;
		this.name = name;
		this.telephone = telephone;
		this.haspda = haspda;
		this.deltag = deltag;
		this.station = station;
		this.decidedZones = decidedZones;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Standard getStandard() {
		return this.standard;
	}

	public void setStandard(Standard standard) {
		this.standard = standard;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getHaspda() {
		return this.haspda;
	}

	public void setHaspda(String haspda) {
		this.haspda = haspda;
	}

	public String getDeltag() {
		return this.deltag;
	}

	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}

	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	@JSON(serialize = false)
	public Set getDecidedZones() {
		return this.decidedZones;
	}

	public void setDecidedZones(Set decidedZones) {
		this.decidedZones = decidedZones;
	}

}