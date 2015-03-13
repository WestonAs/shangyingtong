package gnete.card.web.dict;

import flink.etc.MatchMode;
import flink.util.NameValuePair;
import flink.util.Paginater;
import gnete.card.dao.AreaDAO;
import gnete.card.entity.Area;
import gnete.card.web.BaseAction;
import gnete.etc.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * AreaAction.java.
 * 
 * @author Henry
 * @since JDK1.5
 * @history 2010-7-7
 */
public class AreaAction extends BaseAction {

	@Autowired
	private AreaDAO areaDAO;

	private Paginater page;

	private Area area;

	private List<NameValuePair> parentList;
	private List<NameValuePair> cityList;
	// 界面选择时是否单选
	private boolean radio;
	
	private String areaSelect;

	@Override
	// 默认方法显示列表页面
	public String execute() throws Exception {
		this.parentList = this.areaDAO.findParent();
		Map<String, Object> params = new HashMap<String, Object>();
		if (area != null) {
			params.put("areaCode", area.getAreaCode());
			params.put("areaName", MatchMode.ANYWHERE.toMatchString(area.getAreaName()));
			params.put("parent", area.getParent());
		}
		this.page = this.areaDAO.find(params, this.getPageNumber(), this.getPageSize());
		return LIST;
	}

	// 明细页面
	public String detail() throws Exception {
		this.area = (Area) this.areaDAO.findByPk(this.area.getAreaCode());
		return DETAIL;
	}

	public String showSelect() throws Exception {
		this.parentList = this.areaDAO.findParent();
		this.areaSelect = "5810";
		this.cityList = this.areaDAO.findCityByParent("5810");
		return "select";
	}

	/**
	 * 地区选择器
	 */
	public String select() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (area != null) {
			params.put("cityCode", area.getCityCode());
			params.put("areaName", MatchMode.ANYWHERE.toMatchString(area.getAreaName()));
			params.put("parent", area.getParent());
		}
		page = areaDAO.find(params, getPageNumber(), Constants.DEFAULT_SELECT_PAGE_SIZE);
		return "data";
	}
	
	/**
	 * 根据省份加载城市列表
	 * @throws Exception
	 */
	public void loadCity() throws Exception {
		String parent = request.getParameter("provinceName");
		this.cityList = this.areaDAO.findCityByParent(parent);
		StringBuffer sb = new StringBuffer(128);
		sb.append("<option value=\"\">--请选择--</option>");
		for (NameValuePair nameValuePair : cityList) {
			sb.append("<option value=\"")
				.append(nameValuePair.getValue()).append("\">")
				.append(nameValuePair.getName()).append("</option>");
		}
		this.respond(sb.toString());
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Paginater getPage() {
		return page;
	}

	public void setPage(Paginater page) {
		this.page = page;
	}

	public List<NameValuePair> getParentList() {
		return parentList;
	}

	public void setParentList(List<NameValuePair> parentList) {
		this.parentList = parentList;
	}

	public boolean isRadio() {
		return radio;
	}

	public void setRadio(boolean radio) {
		this.radio = radio;
	}

	public String getAreaSelect() {
		return areaSelect;
	}

	public void setAreaSelect(String areaSelect) {
		this.areaSelect = areaSelect;
	}

	public List<NameValuePair> getCityList() {
		return cityList;
	}

	public void setCityList(List<NameValuePair> cityList) {
		this.cityList = cityList;
	}

}
