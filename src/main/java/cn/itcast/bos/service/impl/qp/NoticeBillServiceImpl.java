package cn.itcast.bos.service.impl.qp;

import java.sql.Timestamp;
import java.util.List;

import cn.itcast.bos.domain.bc.DecidedZone;
import cn.itcast.bos.domain.bc.Subarea;
import cn.itcast.bos.domain.qp.NoticeBill;
import cn.itcast.bos.domain.qp.WorkBill;
import cn.itcast.bos.service.base.BaseService;
import cn.itcast.bos.service.qp.NoticeBillService;

public class NoticeBillServiceImpl extends BaseService implements NoticeBillService {

	@Override
	public void saveNoticeBill(NoticeBill noticeBill) {
		//将业务通知单数据保存到数据库
		noticeBillDAO.save(noticeBill);
		//自动分单
		//1.使用当前的取件地址  在crm客户端系统中查询定区编号
		String decidedZoneId = customerService.findDecidedZoneIdByCustomerAddress(noticeBill.getPickaddress());
		//判断   如果为空 未查到
		if (decidedZoneId==null) {
			//未查到
			//进入方案二  匹配分区关键字
			String[] addressArray = noticeBill.getPickaddress().split(" ");//北京市  海定区  xxx路
			if (addressArray.length>=2) {
				String addresskey = addressArray[1];//取出第二个元素作为关键字
				List<Subarea> list = subareaDAO.findByNamedQuery("Subarea.findByAddressKey",addresskey);	
				//只匹配到唯一的一个分区   而且这个分区已经关联到定区
				if (list.size()==1 && list.get(0).getDecidedZone()!=null) {
					//自动分单成功
					
					//查到了  (自动分单成功)
					DecidedZone decidedZone = list.get(0).getDecidedZone();//查到了定区  定区关联取派员
					//通知单
					noticeBill.setStaff(decidedZone.getStaff());
					noticeBill.setOrdertype("自动");
					//工单信息
					WorkBill workBill = new WorkBill();
					workBill.setNoticeBill(noticeBill);
					workBill.setStaff(decidedZone.getStaff());
					workBill.setType("新");
					workBill.setPickstate("新单");
					workBill.setBuildtime(new Timestamp(System.currentTimeMillis()));
					workBill.setAttachbilltimes(0);
					workBill.setRemark(noticeBill.getRemark());
					workBillDAO.save(workBill);
				}else{
					//人工分单	
					noticeBill.setOrdertype("人工");
				}
			}else {
				//人工调度
				noticeBill.setOrdertype("人工");	
			}
			
		}else{
			//查到了  (自动分单成功)
			DecidedZone decidedZone = decidedZoneDAO.findById(decidedZoneId);//查到了定区  定区关联取派员
			//通知单
			noticeBill.setStaff(decidedZone.getStaff());
			noticeBill.setOrdertype("自动");
			//工单信息
			WorkBill workBill = new WorkBill();
			workBill.setNoticeBill(noticeBill);
			workBill.setStaff(decidedZone.getStaff());
			workBill.setType("新");
			workBill.setPickstate("新单");
			workBill.setBuildtime(new Timestamp(System.currentTimeMillis()));
			workBill.setAttachbilltimes(0);
			workBill.setRemark(noticeBill.getRemark());
			workBillDAO.save(workBill);
		}
	}

	@Override
	public List<NoticeBill> findnoassociations() {
		String ordertype = "人工";
		return noticeBillDAO.findByNamedQuery("NoticeBill.findnoassociations", ordertype);
	}

	@Override
	public void saveOrUpdate(NoticeBill noticeBill) {
		// TODO Auto-generated method stub
		noticeBill.setOrdertype("自动");
		noticeBill.setUser(noticeBill.getUser());
		//工单信息
		WorkBill workBill = new WorkBill();
		workBill.setNoticeBill(noticeBill);
		workBill.setStaff(noticeBill.getStaff());
		workBill.setType("新");
		workBill.setPickstate("新单");
		workBill.setBuildtime(new Timestamp(System.currentTimeMillis()));
		workBill.setAttachbilltimes(0);
		workBill.setRemark(noticeBill.getRemark());
		workBillDAO.saveOrUpdate(workBill);
		
		noticeBillDAO.saveOrUpdate(noticeBill);
		
	
	}

	@Override
	public List<NoticeBill> findassociations() {
		// TODO Auto-generated method stub
		String ordertype = "自动";
		return noticeBillDAO.findByNamedQuery("NoticeBill.findnoassociations", ordertype);
	}


}
