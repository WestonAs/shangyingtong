package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;

import gnete.card.dao.AccountFreezeBillDAO;

@Repository
public class AccountFreezeBillDAOImpl extends BaseDAOIbatisImpl implements AccountFreezeBillDAO {

    public String getNamespace() {
        return "AccountFreezeBill";
    }
}