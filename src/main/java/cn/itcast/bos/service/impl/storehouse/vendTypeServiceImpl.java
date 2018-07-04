package cn.itcast.bos.service.impl.storehouse;

import java.util.List;

import cn.itcast.bos.domain.storehouse.Vendtype;
import cn.itcast.bos.service.base.BaseService;
import cn.itcast.bos.service.storehouse.vendTypeService;

public class vendTypeServiceImpl extends BaseService implements vendTypeService {

	@Override
	public List<Vendtype> ajaxlist() {
		// TODO Auto-generated method stub
		return venderDAO.findAll();
	}

}
