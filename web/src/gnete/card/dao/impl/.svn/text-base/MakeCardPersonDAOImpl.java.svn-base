package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;

import gnete.card.dao.MakeCardPersonDAO;
import gnete.card.entity.MakeCardPerson;

/**
 * 
 * @Project: MyCard
 * @File: MakeCardPersonDAOImpl.java
 * @See:
 * @description：<li>获得制卡厂商联系人的信息</li>
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-21
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
@Repository("makeCardPersonDAO")
public class MakeCardPersonDAOImpl extends BaseDAOIbatisImpl implements MakeCardPersonDAO {

	@Override
	protected String getNamespace() {
		return "MakeCardPerson";
	}

	public Paginater findMakeCardPerson(Map params, int pageNumber, int pageSize) {
		Paginater page =  this.queryForPage("findMakeCardPerson", params, pageNumber, pageSize);
		
		return page;
	}

	public List<MakeCardPerson> findMakeCardPersonList(
			Map<String, Object> params) {
		return super.queryForList("findMakeCardPerson", params);
	}
}
