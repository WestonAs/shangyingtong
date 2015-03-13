package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;

import gnete.card.dao.PosProvSharesHisDAO;

@Repository
public class PosProvSharesHisDAOImpl extends BaseDAOIbatisImpl implements PosProvSharesHisDAO {

    public String getNamespace() {
        return "PosProvSharesHis";
    }
}