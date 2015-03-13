package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;

import gnete.card.dao.ReleaseCardFeeHisDAO;
@Repository
public class ReleaseCardFeeHisDAOImpl extends BaseDAOIbatisImpl implements ReleaseCardFeeHisDAO {

    public String getNamespace() {
        return "ReleaseCardFeeHis";
    }
}