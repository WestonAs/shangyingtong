
package gnete.card.workflow.compare.impl;

import gnete.card.workflow.compare.BaseCompare;

public class CharacterCompare extends BaseCompare {

    public CharacterCompare() {
    }

    public boolean isLike(String stringLeft, String stringRight) {
        return this.isEquals(stringLeft, stringRight);
    }

    /**
     * 检察指定的字符是否包含了对应的stringRight字符串
     * @param stringLeft  需要参照的字符串对象
     * @param stringRight  需要被检验的字符串对象.
     * @return  如果指定的字符串stringRight都包含在stringLeft当中就返回true.
     */
    public boolean isEndWith(String stringLeft, String stringRight) {
        return stringLeft.endsWith(stringRight);
    }

    /**
     * 检察指定的字符是否包含了对应的stringRight字符串
     * @param stringLeft  需要参照的字符串对象
     * @param stringRight  需要被检验的字符串对象.
     * @return  如果指定的字符串stringRight都包含在stringLeft当中就返回true.
     */
    public boolean isStartWith(String stringLeft, String stringRight) {
        return stringLeft.startsWith(stringRight);
    }

    public int compare(String stringLeft, String stringRight) {
        final char charLeft = stringLeft.charAt(0);
        final char charRight = stringRight.charAt(0);
        int returnValue = 0;
        if (charLeft == charRight) {
            returnValue = 0;
        }
        if (charLeft > charRight) {
            returnValue = 1;
        }
        if (charLeft < charRight) {
            returnValue = -1;
        }
        return returnValue;
    }
}
