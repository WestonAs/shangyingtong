package gnete.card.entity.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flink.util.AbstractType;
import gnete.etc.RuntimeBizException;

public class DayOfMonthType extends AbstractType{

	public static final Map ALL = new HashMap();
	
	public static final DayOfMonthType ONE = new DayOfMonthType("1号","01");
	public static final DayOfMonthType TWO = new DayOfMonthType("2号","02");
	public static final DayOfMonthType THREE = new DayOfMonthType("3号","03");
	public static final DayOfMonthType FOUR = new DayOfMonthType("4号","04");
	public static final DayOfMonthType FIVE = new DayOfMonthType("5号","05");
	public static final DayOfMonthType SIX = new DayOfMonthType("6号","06");
	public static final DayOfMonthType SEVEN = new DayOfMonthType("7号","07");
	public static final DayOfMonthType EIGHT = new DayOfMonthType("8号","08");
	public static final DayOfMonthType NIGHE = new DayOfMonthType("9号","09");
	public static final DayOfMonthType TEN = new DayOfMonthType("10号","10");
	public static final DayOfMonthType ELEVEN = new DayOfMonthType("11号","11");
	public static final DayOfMonthType TWELVE = new DayOfMonthType("12号","12");
	public static final DayOfMonthType THIRTEEN = new DayOfMonthType("13号","13");
	public static final DayOfMonthType FOURTEEN = new DayOfMonthType("14号","14");
	public static final DayOfMonthType FIFTHTEEN = new DayOfMonthType("15号","15");
	public static final DayOfMonthType SIXTEEN = new DayOfMonthType("16号","16");
	public static final DayOfMonthType SEVENTEEN = new DayOfMonthType("17号","17");
	public static final DayOfMonthType EIGHTEEN = new DayOfMonthType("18号","18");
	public static final DayOfMonthType NINETEEN = new DayOfMonthType("19号","19");
	public static final DayOfMonthType TWENTY = new DayOfMonthType("20号","20");
	public static final DayOfMonthType TWENTY_ONE = new DayOfMonthType("21号","21");
	public static final DayOfMonthType TWENTY_TWO = new DayOfMonthType("22号","22");
	public static final DayOfMonthType TWENTY_THREE = new DayOfMonthType("23号","23");
	public static final DayOfMonthType TWEETY_FOUR = new DayOfMonthType("24号","24");
	public static final DayOfMonthType TWENTY_FIVE = new DayOfMonthType("25号","25");
	public static final DayOfMonthType TWNETY_SIX = new DayOfMonthType("26号","26");
	public static final DayOfMonthType TWENTY_SEVEN = new DayOfMonthType("27号","27");
	public static final DayOfMonthType TWENTY_EIGHT = new DayOfMonthType("28号","28");
	public static final DayOfMonthType EOM = new DayOfMonthType("月底","M");
	public static final DayOfMonthType EVERY_DAY = new DayOfMonthType("每天","V");
	
	protected DayOfMonthType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static DayOfMonthType valueOf(String value) {
		DayOfMonthType type = (DayOfMonthType) ALL.get(value);
		
		if (type == null) {
			throw new RuntimeBizException("不正确的类型");
		}
		return type;
	}
	
	public static List getAllWithOutEveryDay(){
		Map map = new HashMap();
		
		map.put(ONE.getValue(), ONE);
		map.put(TWO.getValue(), TWO);
		map.put(THREE.getValue(), THREE);
		map.put(FOUR.getValue(), FOUR);
		map.put(FIVE.getValue(), FIVE);
		map.put(SIX.getValue(), SIX);
		map.put(SEVEN.getValue(), SEVEN);
		map.put(EIGHT.getValue(), EIGHT);
		map.put(NIGHE.getValue(), NIGHE);
		map.put(TEN.getValue(), TEN);
		map.put(ELEVEN.getValue(), ELEVEN);
		map.put(TWELVE.getValue(), TWELVE);
		map.put(THIRTEEN.getValue(), THIRTEEN);
		map.put(FOURTEEN.getValue(), FOURTEEN);
		map.put(FIFTHTEEN.getValue(), FIFTHTEEN);
		map.put(SIXTEEN.getValue(), SIXTEEN);
		map.put(SEVENTEEN.getValue(), SEVENTEEN);
		map.put(EIGHTEEN.getValue(), EIGHTEEN);
		map.put(NINETEEN.getValue(), NINETEEN);
		map.put(TWENTY.getValue(), TWENTY);
		map.put(TWENTY_ONE.getValue(), TWENTY_ONE);
		map.put(TWENTY_TWO.getValue(), TWENTY_TWO);
		map.put(TWENTY_THREE.getValue(), TWENTY_THREE);
		map.put(TWEETY_FOUR.getValue(), TWEETY_FOUR);
		map.put(TWENTY_FIVE.getValue(), TWENTY_FIVE);
		map.put(TWNETY_SIX.getValue(), TWNETY_SIX);
		map.put(TWENTY_SEVEN.getValue(), TWENTY_SEVEN);
		map.put(TWENTY_EIGHT.getValue(), TWENTY_EIGHT);
		map.put(EOM.getValue(), EOM);
		
		return getValueOrderedList(map);
	}
	
	public static List getAll(){
		return getValueOrderedList(DayOfMonthType.ALL);
	}

}
