package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;

import gnete.card.dao.BindCardBillDAO;
@Repository
public class BindCardBillDAOImpl extends BaseDAOIbatisImpl implements BindCardBillDAO {

    public String getNamespace() {
        return "BindCardBill";
    }
}