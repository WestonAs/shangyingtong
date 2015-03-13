package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;

import gnete.card.dao.PayBindBankAcountDAO;
@Repository
public class PayBindBankAcountDAOImpl extends BaseDAOIbatisImpl implements PayBindBankAcountDAO {

    public String getNamespace() {
        return "PayBindBankAcount";
    }
}