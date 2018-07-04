package cn.itcast.bos.page;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 封装 分页查询 请求参数
 * 
 * @author Mr_Jc
 * 
 */
public class PageRequestBean {
	private int page; // 当前页码
	private int rows; // 每页记录条数

	// 保存查询条件
	private DetachedCriteria detachedCriteria; // 面向对象条件查询

	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		this.detachedCriteria = detachedCriteria;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}

}
