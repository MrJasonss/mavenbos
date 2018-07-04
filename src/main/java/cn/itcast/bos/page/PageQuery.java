package cn.itcast.bos.page;

/**
 * 分页查询接口
 * 
 * @author Mr_Jc
 * 
 */
public interface PageQuery {
	/**
	 * 分页查询
	 * 
	 * @param pageRequestBean
	 * @return
	 */
	public PageResponseBean pageQuery(PageRequestBean pageRequestBean);
}
