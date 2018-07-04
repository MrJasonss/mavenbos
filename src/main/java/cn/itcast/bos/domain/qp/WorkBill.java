package cn.itcast.bos.domain.qp;

import java.sql.Timestamp;

import cn.itcast.bos.domain.bc.Staff;

/**
 * 工单信息 WorkBill entity. @author MyEclipse Persistence Tools
 */

public class WorkBill implements java.io.Serializable {

	// Fields

	private String id; // 编号 ，改为 uuid
	private NoticeBill noticeBill; // 关联通知单
	private Staff staff; // 关联取派员
	private String type; // 工单类型
	private String pickstate; // 取件状态
	private Timestamp buildtime; // 修改时间
	private Integer attachbilltimes; // 追单次数
	private String remark; // 备注

	// Constructors

	/** default constructor */
	public WorkBill() {
	}

	/** minimal constructor */
	public WorkBill(String id, Timestamp buildtime) {
		this.id = id;
		this.buildtime = buildtime;
	}

	/** full constructor */
	public WorkBill(String id, NoticeBill noticeBill, Staff staff, String type, String pickstate, Timestamp buildtime, Integer attachbilltimes, String remark) {
		this.id = id;
		this.noticeBill = noticeBill;
		this.staff = staff;
		this.type = type;
		this.pickstate = pickstate;
		this.buildtime = buildtime;
		this.attachbilltimes = attachbilltimes;
		this.remark = remark;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public NoticeBill getNoticeBill() {
		return this.noticeBill;
	}

	public void setNoticeBill(NoticeBill noticeBill) {
		this.noticeBill = noticeBill;
	}

	public Staff getStaff() {
		return this.staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPickstate() {
		return this.pickstate;
	}

	public void setPickstate(String pickstate) {
		this.pickstate = pickstate;
	}

	public Timestamp getBuildtime() {
		return this.buildtime;
	}

	public void setBuildtime(Timestamp buildtime) {
		this.buildtime = buildtime;
	}

	public Integer getAttachbilltimes() {
		return this.attachbilltimes;
	}

	public void setAttachbilltimes(Integer attachbilltimes) {
		this.attachbilltimes = attachbilltimes;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "工作单 [编号=" + id + ", 通知单=" + noticeBill + ", 取派员="
				+ staff + ", 工单类型=" + type + ", 取件状态=" + pickstate
				+ ", 修改时间=" + buildtime + ",  追单次数="
				+ attachbilltimes + ", 备注=" + remark + "]";
	}
	

}