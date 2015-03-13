package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;

import gnete.card.dao.IcCardReversalDAO;

@Repository
public class IcCardReversalDAOImpl extends BaseDAOIbatisImpl implements IcCardReversalDAO {

    public String getNamespace() {
        return "IcCardReversal";
    }
}