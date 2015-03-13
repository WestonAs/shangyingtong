package gnete.card.dao.impl;

import gnete.card.dao.TerminalAddiDAO;

import org.springframework.stereotype.Repository;

@Repository
public class TerminalAddiDAOImpl extends BaseDAOIbatisImpl implements TerminalAddiDAO {

    public String getNamespace() {
        return "TerminalAddi";
    }
}