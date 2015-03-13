package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;

import gnete.card.dao.SaleDepositCheckConfigDAO;

@Repository
public class SaleDepositCheckConfigDAOImpl extends BaseDAOIbatisImpl implements SaleDepositCheckConfigDAO {

    public String getNamespace() {
        return "SaleDepositCheckConfig";
    }
}