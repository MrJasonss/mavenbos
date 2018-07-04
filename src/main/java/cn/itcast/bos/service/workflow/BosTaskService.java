package cn.itcast.bos.service.workflow;

import cn.itcast.bos.domain.zm.InStore;
import cn.itcast.bos.domain.zm.OutStore;
import cn.itcast.bos.domain.zm.ReceiveGoodsInfo;
import cn.itcast.bos.domain.zm.TransferInfo;

public interface BosTaskService {


	/**
	 * 办理中转环节的任务
	 * @param transferInfo
	 */
	 public void completeTransferInfo(TransferInfo transferInfo, String taskId);

	 /**
	  * 办理入库业务
	  * @param inStore
	  * @param taskId
	  */
	public void completeInStoreTask(InStore inStore, String taskId);

	/**
	 * 办理出库任务
	 * @param outStore
	 * @param taskId
	 */
	public void completeOutStoreTask(OutStore outStore, String taskId);

	/**
	 * 办理配送任务
	 * @param receiveGoodsInfo
	 * @param taskId
	 */
	public void completeReceiveGoodsInfoTask(ReceiveGoodsInfo receiveGoodsInfo,String taskId);


}
