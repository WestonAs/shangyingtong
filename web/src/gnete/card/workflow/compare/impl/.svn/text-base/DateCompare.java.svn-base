package gnete.card.workflow.compare.impl;

import gnete.card.workflow.compare.BaseCompare;

import java.sql.Date;
import java.util.Calendar;

import org.apache.commons.lang.math.NumberUtils;

public class DateCompare extends BaseCompare {

    public DateCompare() {
    }

    public boolean isLike(String stringLeft, String stringRight) {
        throw new UnsupportedOperationException("不能够对两个日期值进行Like的大小进行比较过程!");
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
        return NumberUtils.compare(parseDateToInt(stringLeft), parseDateToInt(stringRight));
    }

    private long parseDateToInt(String string) {
        if (string == null) {
            throw new IllegalArgumentException("传递过来的参照值无效，请检查参照值!");
        }
        Date parseDate = Date.valueOf(string);
        Calendar parseCalendar = Calendar.getInstance();
        parseCalendar.setTime(parseDate);
        return (parseCalendar.get(Calendar.YEAR) * 10000 + (parseCalendar.get(Calendar.MONTH) + 1)
                * 100 + parseCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
