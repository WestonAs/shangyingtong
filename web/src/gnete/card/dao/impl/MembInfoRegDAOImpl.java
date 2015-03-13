package gnete.card.dao.impl;

import flink.util.Paginater;
import flink.util.WebResource;
import gnete.card.dao.MembInfoRegDAO;
import gnete.card.entity.MembInfoReg;
import gnete.card.entity.Privilege;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class MembInfoRegDAOImpl extends BaseDAOIbatisImpl implements MembInfoRegDAO {

	public String getNamespace() {
		return "MembInfoReg";
	}

	public Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize) {
		return this.queryForPage("findMembInfoReg", params, pageNumber, pageSize);
	}

	@Override
	public List<MembInfoReg> findList(Map<String, Object> params) {
		return this.queryForList("findMembInfoReg", params);
	}
	
	@Override
	public Long selectMembInfoRegSEQ() {
		return (Long)this.queryForObject("selectMembInfoRegSEQ");
	}
	
	@Override
	public List<MembInfoReg> findMembInfoIdList(Map<String, Object> params) {
		List list =  this.queryForList("findMembInfoIdList", params);
        List membInfoRegs = new ArrayList();
		
		for (Iterator i = list.iterator(); i.hasNext();) {
			Map<String, Object> element = (Map<String, Object>) i.next();
			MembInfoReg membInfoReg = new MembInfoReg();
			membInfoReg.setMembInfoId(Long.valueOf(element.get("MEMB_INFO_ID").toString()));
			membInfoRegs.add(membInfoReg);
		}
		return membInfoRegs;
	}

}