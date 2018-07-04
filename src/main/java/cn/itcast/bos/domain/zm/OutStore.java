package cn.itcast.bos.domain.zm;

import java.util.Date;

/**
 * 出库
 * @author Mr_Jc
 *
 */
public class OutStore {
	private Long id;
	private String info;
	private Date updateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "[编号id=" + id + ", 出库信息=" + info + ", 出库时间="
				+ updateTime + "]";
	}
	
	
	
}
