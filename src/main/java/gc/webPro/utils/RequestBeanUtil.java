package gc.webPro.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author: GC
 * @Description: 反射注入Bean工具类(目前只能应对Request请求的Map)
 * @Version: 2.0
 */
public class RequestBeanUtil {

    private static final Map<String, List<FieldSetter>> map;//键为类名

    private static final Logger logger;

    static {
        map = new HashMap<>();
        logger = LoggerFactory.getLogger(RequestBeanUtil.class);
    }

    private static class FieldSetter {
        private String name;//属性名
        private Class<?> fieldType;
        private Method method;//set方法

        public FieldSetter(String name, Class<?> fieldType, Method method) {
            this.name = name;
            this.fieldType = fieldType;
            this.method = method;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Class<?> getFieldType() {
            return fieldType;
        }

        public void setFieldType(Class<?> fieldType) {
            this.fieldType = fieldType;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }
    }

    private static <C> boolean initialize(final C bean) {//对传入的bean的对象进行反射解析 获取set方法以及对应参数类型

        Class<?> beanClass = bean.getClass();
        String className = beanClass.getName();

        List<FieldSetter> list = new ArrayList<>();

        for (Method method : beanClass.getMethods()) {//一个类里面可能有多个set方法 所以需要list存储

            String methodName = method.getName();//获取方法名

            if (methodName.startsWith("set")) {//找到set开头的方法

                String fieldName = methodName.substring(3);//截取后面的字段名

                fieldName = downCaseFirstName(fieldName);//字段名首字母变成小写

                try {
                    Field field = beanClass.getDeclaredField(fieldName);//获取对应字段名

                    Class<?> fieldType = field.getType();//获取字段类型

                    FieldSetter setter = new FieldSetter(fieldName, fieldType, method);//创建内部类实例并填入字段名 类型 set方法

                    list.add(setter);//放入list

                } catch (NoSuchFieldException e) {
                    logger.info("找不到对应set方法");
                }

            }
        }

        if (!list.isEmpty()) {//如果list不为空 说明至少有一个set方法满足 就放入map 如果一个的都没有那么不用放
            map.put(className, list);
            return true;
        } else
            return false;
    }

    public static <C> C SetBeanProperties(final C bean, final Map<String, String[]> properties) {

        Class<?> beanClass = bean.getClass();
        String className = beanClass.getName();

        if (!map.containsKey(className))//如果map中没有对应类
            if (!initialize(bean))//初始化对应类
                throw new RuntimeException("该实例没有任何set方法");

        List<FieldSetter> list = map.get(className);//根据类名获取对应set方法集合

        for (FieldSetter fieldSetter : list) {//如果有就迭代list中的set方法类实例

            String fieldName = fieldSetter.getName();//获取字段名

            String[] values = properties.get(fieldName);//根据字段名来找对应RequestMap中是否有这个键

            if (values == null)//如果值不存在 跳过
                continue;

            Class<?> fieldType = fieldSetter.getFieldType();//获取字段类型

            Object newValue = null;

            newValue = getValue(fieldType, values);//转换参数值 复杂的判断 目前还不是很完善

            if (newValue == null)//没有符合条件的值跳过
                continue;

            Method method = fieldSetter.getMethod();//获取方法
            try {
                method.invoke(bean, newValue);//调用方法 参数在反射时会自动转为原始类型(自动强转)
            } catch (Exception e) {
                logger.info("{}方法调用出错", method);
            }

        }

        return bean;
    }

    private static Object getValue(Class<?> fieldType, String[] value) {
        Object newValue = null;

        if (!fieldType.isArray()) {//如果属性不是数组且参数是字符串数组 暂时先处理基本的数据类型 int double

            if(value[0] == null)
                return null;

            if (fieldType == String.class) {
                newValue = value[0];//将字符串第一个元素赋给属性
            } else if (CommonUtil.isInteger(value[0]) && (fieldType == int.class || fieldType == Integer.class)) {
                newValue = Integer.parseInt(value[0]);//将字符串第一个元素赋给属性
            } else if (CommonUtil.isDouble(value[0]) && (fieldType == double.class || fieldType == Double.class)) {
                newValue = Double.parseDouble(value[0]);//将字符串第一个元素赋给属性
            }

        } else {

            int[] intArray = null;
            double[] doubleArray = null;

            newValue = getFieldTypeArray(fieldType, value);

            if (fieldType == int[].class) {
                intArray = Arrays.stream((Integer[]) newValue).mapToInt(Integer::valueOf).toArray();//将Integer[] 转成int[]
                newValue = intArray;//Object可以接受数组引用
            } else if (fieldType == double[].class) {
                doubleArray = Arrays.stream((Double[]) newValue).mapToDouble(Double::valueOf).toArray();
                newValue = doubleArray;
            } else if (fieldType == String[].class) {
                newValue = value;
            }

        }
        return newValue;
    }

    private static <T> T[] getFieldTypeArray(Class<T> fieldType, String[] value) {

        Object newValue = null;
        if (fieldType == Integer[].class || fieldType == int[].class) {

            Integer[] intArrays = new Integer[value.length];
            for (int i = 0; i < value.length; i++) {
                intArrays[i] = Integer.parseInt(value[i]);
            }
            newValue = intArrays;
        } else if (fieldType == Double[].class || fieldType == double[].class) {

            Double[] DoubleArray = new Double[value.length];
            for (int i = 0; i < value.length; i++) {
                DoubleArray[i] = Double.parseDouble(value[i]);
            }

            newValue = DoubleArray;
        }


        return (T[]) newValue;
    }

    private static String downCaseFirstName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs = str.toCharArray();
        cs[0] += 32;
        return String.valueOf(cs);
    }
}
