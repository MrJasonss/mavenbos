package cn.itcast.bos.domain.user;

import java.util.Date;

import cn.itcast.bos.domain.auth.Role;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private String id;// 编号   修改生成策略  为uuid
	private String username;//用户名
	private String password;//密码
	private Double salary;//工资
	private Date birthday;//生日
	private String gender;//性别
	private String station;//单位
	private String telephone;//电话
	private String remark;//电话
	private String email;//邮箱
	private String context;
	private Role role;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String id) {
		this.id = id;
	}

	/** full constructor */
	public User(String id, String username, String password, Double salary, Date birthday, String gender, String station, String telephone,String email, String remark,String context) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.salary = salary;
		this.birthday = birthday;
		this.gender = gender;
		this.station = station;
		this.telephone = telephone;
		this.email = email;
		this.remark = remark;
		this.context=context;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getSalary() {
		return this.salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	

}