package gnete.card.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import flink.util.Paginater;
import gnete.card.dao.CardMerchFeeDAO;

@Repository
public class CardMerchFeeDAOImpl extends BaseDAOIbatisImpl implements CardMerchFeeDAO {

    public String getNamespace() {
        return "CardMerchFee";
    }

	public Paginater findCardMerchFee(Map<String, Object> parma, int pageNumber, int pageSize) {
		return this.queryForPage("findCardMerchFee", parma, pageNumber, pageSize);
	}
  
}