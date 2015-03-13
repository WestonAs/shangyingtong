package gnete.card.workflow.compare;

/**
 * 提供一个比较对象的公用接口,然后利用委托代理的方式来动态的调用当前的基本对象.
 * @author aps-lih
 */
public abstract class BaseCompare {
    public BaseCompare() {
    }

    public boolean isEquals(String stringLeft, String stringRight) {
        return (this.compare(stringLeft, stringRight) == 0 ? true : false);
    }

    public boolean isNotEquals(String stringLeft, String stringRight) {
        return !this.isEquals(stringLeft, stringRight);
    }

    public boolean isGreaterThan(String stringLeft, String stringRight) {
        return (this.compare(stringLeft, stringRight) == 1 ? true : false);
    }

    public boolean isLessThan(String stringLeft, String stringRight) {
        return (this.compare(stringLeft, stringRight) == -1 ? true : false);
    }

    public boolean isGreaterEqual(String stringLeft, String stringRight) {
        int returnValue = this.compare(stringLeft, stringRight);
        return ((returnValue == 1 || returnValue == 0) ? true : false);
    }

    public boolean isLessEqual(String stringLeft, String stringRight) {
        int returnValue = this.compare(stringLeft, stringRight);
        return ((returnValue == -1 || returnValue == 0) ? true : false);
    }

    /**
     * 对两个字符串的子集合进行比较，看是否stringLeft包含了stringRight
     * @param stringLeft  需要参照的字符串对象
     * @param stringRight  需要被检验的字符串对象.
     * @return  如果指定的字符串stringRight都包含在stringLeft当中就返回true.
     */
    public abstract boolean isLike(String stringLeft, String stringRight);

    /**
     * 检察指定的字符是否包含了对应的stringRight字符串
     * @param stringLeft  需要参照的字符串对象
     * @param stringRight  需要被检验的字符串对象.
     * @return  如果指定的字符串stringRight都包含在stringLeft当中就返回true.
     */
    public abstract boolean isEndWith(String stringLeft, String stringRight);

    /**
     * 检察指定的字符是否包含了对应的stringRight字符串
     * @param stringLeft  需要参照的字符串对象
     * @param stringRight  需要被检验的字符串对象.
     * @return  如果指定的字符串stringRight都包含在stringLeft当中就返回true.
     */
    public abstract boolean isStartWith(String stringLeft, String stringRight);

    /**
     * 将参考值和希望值进行比较，得出的具体结论由后代来进行实现过程,
     * 目前支持的比较数据类型如下所示:
     * (1).java.lang.Character
     * (2).java.lang.Integer
     * (3).java.lang.Short
     * (4).java.sql.Date
     * (5).java.math.BigDecimal
     * (6).java.lang.Byte
     * (7).java.lang.Double
     * (8).java.lang.Float
     * (9).java.lang.Long
     * @param stringLeft  定义的需要比较的字符串.
     * @param stringRight 定义的需要比较的另外一个字符串.
     * @return 首先将指定的字符串转换为对应的类型类格式,如果转换过后进行比较,具体的关系如下:
     * consultValue > expectValue  Return:1
     * consuleValue = expectValue  Return:0
     * consuleValue < expectValue  Return:-1
     */
    public abstract int compare(String stringLeft, String stringRight);
}
