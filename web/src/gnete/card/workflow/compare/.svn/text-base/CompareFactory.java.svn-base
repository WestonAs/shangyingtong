package gnete.card.workflow.compare;

/**
 * 利用当前的工厂类来创建指定的比较对象.
 * @author aps-lih
 */
import gnete.card.workflow.compare.impl.BigDecimalCompare;
import gnete.card.workflow.compare.impl.CharacterCompare;
import gnete.card.workflow.compare.impl.DateCompare;
import gnete.card.workflow.compare.impl.DoubleCompare;
import gnete.card.workflow.compare.impl.FloatCompare;
import gnete.card.workflow.compare.impl.IntegerCompare;
import gnete.card.workflow.compare.impl.LongCompare;
import gnete.card.workflow.compare.impl.ShortCompare;
import gnete.card.workflow.compare.impl.StringCompare;

import java.util.HashMap;

public final class CompareFactory {
    private static HashMap compareMap = new HashMap();
    static {
        BaseCompare baseCompare = null;
        //------------------------------------------------------------------------------------
        compareMap.put("java.math.BigDecimal", new BigDecimalCompare());
        //------------------------------------------------------------------------------------
        baseCompare = new CharacterCompare();
        compareMap.put("java.lang.Character", baseCompare);
        compareMap.put("char", baseCompare);
        //------------------------------------------------------------------------------------
        compareMap.put("java.sql.Date", new DateCompare());
        //------------------------------------------------------------------------------------
        baseCompare = new DoubleCompare();
        compareMap.put("java.lang.Double", baseCompare);
        compareMap.put("double", baseCompare);
        //------------------------------------------------------------------------------------
        baseCompare = new FloatCompare();
        compareMap.put("java.lang.Float", baseCompare);
        compareMap.put("float", baseCompare);
        //------------------------------------------------------------------------------------
        baseCompare = new IntegerCompare();
        compareMap.put("java.lang.Integer", baseCompare);
        compareMap.put("int", baseCompare);
        //------------------------------------------------------------------------------------
        baseCompare = new LongCompare();
        compareMap.put("java.lang.Long", baseCompare);
        compareMap.put("long", baseCompare);
        //------------------------------------------------------------------------------------
        baseCompare = new ShortCompare();
        compareMap.put("java.lang.Short", baseCompare);
        compareMap.put("short", baseCompare);
        //------------------------------------------------------------------------------------
        compareMap.put("java.lang.String", new StringCompare());
    }

    private CompareFactory() {
    }

    /**
     * 根据实例类型来创建具体的实例类对象。
     * @param type  指定实例类完整包路径
     * @return 如果能够创建成功，就返回一个BaseCompare对象。
     */
    public static BaseCompare getInstance(String type) {
        if (type == null) {
            throw new IllegalArgumentException("当前指定的实例类型为NULL，请检验比较类型是否有效!");
        }
        return (BaseCompare) compareMap.get(type);
    }
}