package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;

import gnete.card.dao.PosManageSharesHisDAO;
@Repository
public class PosManageSharesHisDAOImpl extends BaseDAOIbatisImpl implements PosManageSharesHisDAO {

    public String getNamespace() {
        return "PosManageSharesHis";
    }
}