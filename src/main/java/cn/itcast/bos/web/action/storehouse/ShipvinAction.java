package cn.itcast.bos.web.action.storehouse;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.storehouse.Shipvin;
import cn.itcast.bos.domain.storehouse.Vendtype;
import cn.itcast.bos.web.action.base.BaseAction;

public class ShipvinAction extends BaseAction implements ModelDriven<Shipvin>{

	
	private Shipvin shipvin = new Shipvin();
	@Override
	public Shipvin getModel() {
		// TODO Auto-generated method stub
		return shipvin;
	}
	

	public String ajaxlist(){
		
		// 调用业务层 将列表查出
		List<Shipvin> shipvins = shipVinService.ajaxlist();

		// 将处理结果 转换json返回
		ActionContext.getContext().put("shipvins", shipvins);

		return "ajaxlistSUCCESS";
	}

}
