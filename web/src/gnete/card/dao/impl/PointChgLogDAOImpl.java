package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;

import gnete.card.dao.PointChgLogDAO;

@Repository
public class PointChgLogDAOImpl extends BaseDAOIbatisImpl implements PointChgLogDAO {

    public String getNamespace() {
        return "PointChgLog";
    }
}