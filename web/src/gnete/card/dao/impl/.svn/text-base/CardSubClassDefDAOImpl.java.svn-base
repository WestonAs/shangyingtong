package gnete.card.dao.impl;

import flink.util.Paginater;
import gnete.card.dao.CardSubClassDefDAO;
import gnete.card.entity.CardSubClassDef;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CardSubClassDefDAOImpl extends BaseDAOIbatisImpl implements CardSubClassDefDAO {

    public String getNamespace() {
        return "CardSubClassDef";
    }
    
    public Paginater findCardSubClassDef(Map<String, Object> params,
			int pageNumber, int pageSize) {
		return this.queryForPage("findCardSubClassDef", params, pageNumber, pageSize);
	}

	public boolean isExistCardSubClass(String cardSubClass) {
		return this.isExist(cardSubClass);
	}

	public List<CardSubClassDef> findCardSubClassDefByBranNo(String cardIssuer) {
		return this.queryForList("findCardSubClassDefByBranNo", cardIssuer);
	}

	public List<CardSubClassDef> findCardSubClass(Map<String, Object> params) {
		return this.queryForList("findCardSubClass", params);
	}
	
	public boolean isExsitCardSubClassName(String cardSubClassName) {
		return (Long) this.queryForObject("isExsitCardSubClassName", cardSubClassName) > 0;
	}
}