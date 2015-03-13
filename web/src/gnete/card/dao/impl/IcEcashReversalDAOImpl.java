package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;

import gnete.card.dao.IcEcashReversalDAO;

@Repository
public class IcEcashReversalDAOImpl extends BaseDAOIbatisImpl implements IcEcashReversalDAO {

    public String getNamespace() {
        return "IcEcashReversal";
    }
}