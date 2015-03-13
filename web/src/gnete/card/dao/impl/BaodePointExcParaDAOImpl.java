package gnete.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.BaodePointExcParaDAO;
import gnete.card.entity.BaodePointExcPara;

@Repository("baodePointExcParaDAO")
public class BaodePointExcParaDAOImpl extends BaseDAOIbatisImpl implements BaodePointExcParaDAO {

    public String getNamespace() {
        return "BaodePointExcPara";
    }

	public Paginater findBaodePointExcPara(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findBaodePointExcPara", params, pageNumber, pageSize);
	}

	public List<BaodePointExcPara> findBaodePointExcParaList(
			Map<String, Object> params) {
		return this.queryForList("findBaodePointExcParaList", params);
	}
}