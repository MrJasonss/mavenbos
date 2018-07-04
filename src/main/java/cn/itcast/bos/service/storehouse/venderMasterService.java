package cn.itcast.bos.service.storehouse;

import cn.itcast.bos.domain.storehouse.Vendermaster;
import cn.itcast.bos.page.PageQuery;

public interface venderMasterService extends PageQuery{

	public void savevendermaster(Vendermaster vendermaster);

}
