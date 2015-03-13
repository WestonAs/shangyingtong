package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;

import gnete.card.dao.BranchSharesHisDAO;

@Repository
public class BranchSharesHisDAOImpl extends BaseDAOIbatisImpl implements BranchSharesHisDAO {

    public String getNamespace() {
        return "BranchSharesHis";
    }
}