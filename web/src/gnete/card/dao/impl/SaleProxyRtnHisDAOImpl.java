package gnete.card.dao.impl;

import org.springframework.stereotype.Repository;

import gnete.card.dao.SaleProxyRtnHisDAO;
@Repository
public class SaleProxyRtnHisDAOImpl extends BaseDAOIbatisImpl implements SaleProxyRtnHisDAO {

    public String getNamespace() {
        return "SaleProxyRtnHis";
    }
}