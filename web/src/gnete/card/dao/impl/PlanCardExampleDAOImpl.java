package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;

import gnete.card.dao.PlanCardExampleDAO;

@Repository
public class PlanCardExampleDAOImpl extends BaseDAOIbatisImpl implements PlanCardExampleDAO {

    public String getNamespace() {
        return "PlanCardExample";
    }
}