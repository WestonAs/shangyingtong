package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;

import gnete.card.dao.DrawScopeDAO;

@Repository
public class DrawScopeDAOImpl extends BaseDAOIbatisImpl implements DrawScopeDAO {

    public String getNamespace() {
        return "DrawScope";
    }
}