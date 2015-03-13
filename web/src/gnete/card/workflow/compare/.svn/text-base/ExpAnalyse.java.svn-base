package gnete.card.workflow.compare;

import gnete.card.workflow.entity.WorkflowNodeCondition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpAnalyse {
    private static HashMap nodeMap = new HashMap();
    static {
        nodeMap.put("=", "isEquals");
        nodeMap.put("!=", "isNotEquals");
        nodeMap.put(">", "isGreaterThan");
        nodeMap.put("<", "isLessThan");
        nodeMap.put(">=", "isGreaterEqual");
        nodeMap.put("<=", "isLessEqual");
        nodeMap.put("like", "isLike");
        nodeMap.put("startWith", "isStartWith");
        nodeMap.put("endWith", "isEndWith");
    }

    private ExpAnalyse() {
    }

    public static boolean analysisExpress(Object object, List nodeConditions) {
        if (nodeConditions == null || object == null) {
            return false;
        }
        List alConditions = nodeConditions;
        //-----------------------------------------------------------------------
        boolean analysisValue = true;
        String operator = null; //保存每次使用的关系字符串变量参数.
        //-----------------------------------------------------------------------
        WorkflowNodeCondition condition = null;
        BaseCompare baseCompare = null;
        //----------------------------------------------------------------------
        String nodeMethodName = null;
        Method nodeMethod = null;
        //----------------------------------------------------------------------
        String methodName = null;
        Method method = null;
        //-----------------------------------------------------------------------
        for (int i = 0, j = alConditions.size(); i < j; i++) {
            //首先我们需要根据对应的条件来调用制定的基本方法并返回有效的数据.
            condition = (WorkflowNodeCondition) alConditions.get(i);
            baseCompare = null;
            String strOperator = condition.getOperator().trim();
            // 操作符号可能会存在空格的情况，因此，需要将操作符号的空格给截取。
            nodeMethodName = (String) nodeMap.get(strOperator);
            methodName = condition.getField();
            try {
                if (object instanceof Map) {
                    Map objMap = (Map) object;
                    String classType = objMap.get(methodName).getClass().getName();
                    baseCompare = CompareFactory.getInstance(classType);
                    // 根据操作符得到其在CompareFactory中指定的方法名
                    nodeMethod = baseCompare.getClass().getMethod(nodeMethodName,
                            new Class[] { String.class, String.class });
                } else {
                    // 得到待执行的方法对象
                    method = object.getClass().getMethod(methodName, new Class[0]);
                    // 得到方法对象的返回值，通过CompareFactory得到其包装后的对象
                    String methodReturnType = method.getReturnType().getName();
                    baseCompare = CompareFactory.getInstance(methodReturnType);
                    // 根据操作符得到其在CompareFactory中指定的方法名
                    nodeMethod = baseCompare.getClass().getMethod(nodeMethodName,
                            new Class[] { String.class, String.class });
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            //然后我们利用对应的函数来获取期待比较的对象数据.
            Object tempObjectValue = null;
            if (object instanceof Map) {
                Map objMap = (Map) object;
                tempObjectValue = objMap.get(methodName);
            } else {
                tempObjectValue = invokeMethod(method, object, new Object[0]);
            }
            boolean tempLogic = ((Boolean) invokeMethod(nodeMethod, baseCompare, new String[] {
                    tempObjectValue.toString(), condition.getValue() })).booleanValue();

            if (i == 0) {
                analysisValue = analysisValue && tempLogic;
                operator = condition.getConnector();
            } else {
                if (operator.equalsIgnoreCase("and")) {
                    analysisValue = analysisValue && tempLogic;
                } else {
                    analysisValue = analysisValue || tempLogic;
                }
                //将操作属性保存起来以方便下一条数据使用进行比较过程.
                operator = condition.getConnector();
            }
        }
        //-----------------------------------------------------------------------
        return analysisValue;
    }

    public static boolean analysisExpress(Object object, String methodName, String compareValue) {
        if (object == null || methodName == null || compareValue == null) {
            return false;
        }
        try {
            Method method = object.getClass().getMethod(methodName, new Class[0]);
            String methodReturnType = method.getReturnType().getName();
            BaseCompare baseCompare = CompareFactory.getInstance(methodReturnType);
            Object tempObjectValue = invokeMethod(method, object, new Object[0]);
            return baseCompare.isEquals(tempObjectValue.toString(), compareValue);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Object invokeMethod(Method methodClass, Object object, Object[] objectArrays) {
        if (object == null || objectArrays == null || methodClass == null) {
            throw new IllegalArgumentException("传递过来的参照值无效，请检查参数对象是否为空值!");
        }
        if (!Modifier.isPublic(methodClass.getModifiers())) {
            //new IllegalArgumentException("指定的方法不是Public的方法，请核对后重新检查,您设置的条件也许不正确!");
        	throw new IllegalArgumentException("指定的方法不是Public的方法，请核对后重新检查,您设置的条件也许不正确!");
        }
        Object methodReturnValue = null;
        try {
            methodReturnValue = methodClass.invoke(object, objectArrays);
        } catch (InvocationTargetException e) {
            e.fillInStackTrace();
        } catch (IllegalAccessException e) {
            e.fillInStackTrace();
        }
        return methodReturnValue;
    }
}
