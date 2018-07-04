package cn.itcast.bos.web.action.storehouse;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.bc.Standard;
import cn.itcast.bos.domain.storehouse.Vendtype;
import cn.itcast.bos.web.action.base.BaseAction;

public class vendTypeAction extends BaseAction implements ModelDriven<Vendtype> {

	
	private Vendtype vendtype= new Vendtype();
	@Override
	public Vendtype getModel() {
		// TODO Auto-generated method stub
		return vendtype;
	}
	
	
	public String ajaxlist(){
		
		// 调用业务层 将列表查出
		List<Vendtype> vendtypes = vendTypeService.ajaxlist();

		// 将处理结果 转换json返回
		ActionContext.getContext().put("vendtypes", vendtypes);

		return "ajaxlistSUCCESS";
	}

}
