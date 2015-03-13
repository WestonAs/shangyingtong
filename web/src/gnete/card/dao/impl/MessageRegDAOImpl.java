package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;
import gnete.card.dao.MessageRegDAO;

@Repository
public class MessageRegDAOImpl extends BaseDAOIbatisImpl implements MessageRegDAO {

    public String getNamespace() {
        return "MessageReg";
    }
}