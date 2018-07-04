package cn.itcast.bos.service.impl.storehouse;

import java.util.List;

import cn.itcast.bos.domain.storehouse.Shipvin;
import cn.itcast.bos.service.base.BaseService;
import cn.itcast.bos.service.storehouse.ShipvinServicve;

public class ShipvinServiceImpl extends BaseService implements ShipvinServicve {

	@Override
	public List<Shipvin> ajaxlist() {
		// TODO Auto-generated method stub
		return shipVinDAO.findAll();
	}

}
