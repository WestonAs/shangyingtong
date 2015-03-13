package gnete.card.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import flink.util.Paginater;
import gnete.card.dao.DaySwitchDAO;

@Repository
public class DaySwitchDAOImpl extends BaseDAOIbatisImpl implements DaySwitchDAO {

    public String getNamespace() {
        return "DaySwitch";
    }

	public Paginater findDaySwitch(Map<String, Object> params, int pageNumber,
			int pageSize) {
		return this.queryForPage("findDaySwitch", params, pageNumber, pageSize);
	}
}