package gnete.card.workflow.compare.impl;

import gnete.card.workflow.compare.BaseCompare;

public class StringCompare extends BaseCompare {

    public StringCompare() {
    }

    public boolean isEquals(String stringLeft, String stringRight) {
        return stringLeft.equals(stringRight);
    }

    public boolean isNotEquals(String stringLeft, String stringRight) {
        return !this.isEquals(stringLeft, stringRight);
    }

    public int compare(String stringLeft, String stringRight) {
        throw new UnsupportedOperationException("不能够对两个字符串的大小进行比较过程!");
    }

    public boolean isLike(String stringLeft, String stringRight) {
        if (stringLeft == null || "".equals(stringLeft)) {
            return false;
        }
        return stringLeft.startsWith(stringRight);
    }

    /**
     * 检察指定的字符是否包含了对应的stringRight字符串
     * @param stringLeft  需要参照的字符串对象
     * @param stringRight  需要被检验的字符串对象.
     * @return  如果指定的字符串stringRight都包含在stringLeft当中就返回true.
     */
    public boolean isEndWith(String stringLeft, String stringRight) {
        if (stringLeft == null || "".equals(stringLeft)) {
            return false;
        }
        return stringLeft.endsWith(stringRight);
    }

    /**
     * 检察指定的字符是否包含了对应的stringRight字符串
     * @param stringLeft  需要参照的字符串对象
     * @param stringRight  需要被检验的字符串对象.
     * @return  如果指定的字符串stringRight都包含在stringLeft当中就返回true.
     */
    public boolean isStartWith(String stringLeft, String stringRight) {
        if (stringLeft == null || "".equals(stringLeft)) {
            return false;
        }
        return stringLeft.startsWith(stringRight);
    }
}
