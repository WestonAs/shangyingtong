package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;

import gnete.card.dao.IcCardExtroInfoDAO;

@Repository
public class IcCardExtroInfoDAOImpl extends BaseDAOIbatisImpl implements IcCardExtroInfoDAO {

    public String getNamespace() {
        return "IcCardExtroInfo";
    }
}