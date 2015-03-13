package gnete.card.workflow.compare.impl;

import gnete.card.workflow.compare.BaseCompare;

import org.apache.commons.lang.math.NumberUtils;

public class IntegerCompare extends BaseCompare {

    public IntegerCompare() {
    }

    public boolean isLike(String stringLeft, String stringRight) {
        throw new UnsupportedOperationException("不能够对两个整数值进行Like的大小进行比较过程!");
    }

    /**
     * 检察指定的字符是否包含了对应的stringRight字符串
     * @param stringLeft  需要参照的字符串对象
     * @param stringRight  需要被检验的字符串对象.
     * @return  如果指定的字符串stringRight都包含在stringLeft当中就返回true.
     */
    public boolean isEndWith(String stringLeft, String stringRight) {
        throw new UnsupportedOperationException("不能够对两个整数值进行isEndWith的大小进行比较过程!");
    }

    /**
     * 检察指定的字符是否包含了对应的stringRight字符串
     * @param stringLeft  需要参照的字符串对象
     * @param stringRight  需要被检验的字符串对象.
     * @return  如果指定的字符串stringRight都包含在stringLeft当中就返回true.
     */
    public boolean isStartWith(String stringLeft, String stringRight) {
        throw new UnsupportedOperationException("不能够对两个整数值进行isEndWith的大小进行比较过程!");
    }

    public int compare(String stringLeft, String stringRight) {
        final int intLeft = NumberUtils.createInteger(stringLeft).intValue();
        final int intRight = NumberUtils.createInteger(stringRight).intValue();
        return NumberUtils.compare(intLeft, intRight);
    }
}
