package com.bigdata.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * request工具类
 * */
public class RequestUtil {

	/**
	 * 表单元素与实体类完全对应
	 * */
	public static <T> T get(Class<T> clazz, HttpServletRequest request){
		
		T instance = null;//返回对象
		
		Map<String, String[]> map = request.getParameterMap();//获取一个name与value的map
		
		if(map != null && map.size() > 0){//如果map不为空并且元素个数>0
			try{
				instance = clazz.newInstance();
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
                    field.setAccessible(true);//设置可访问
                    String fieldName = field.getName();//获取字段名称(与表单name相同)

                    String fieldValue = arratToString(map.get(fieldName));//从map中获取数据
                    
                    if("".equals(fieldValue))
                    	continue;

                    //获取字段set方法名
                    String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                    Method method = clazz.getMethod(methodName, field.getType());
                    
                    //如果不是String类型的
                    String typeName = field.getType().getName();
                    if(!fieldValue.getClass().getName().equals(typeName)){

                        if(typeName.equals(Integer.class.getName())){//如果是Integer类型
                            int value = Integer.parseInt(fieldValue);
                            method.invoke(instance, value);//设置值
                        }else if(typeName.equals(Double.class.getName())){//如果是double类型的
                            double value = Double.parseDouble(fieldValue);
                            method.invoke(instance, value);//设置值
                        }else if(typeName.equals(Float.class.getName())){//如果是float类型的
                            float value = Float.parseFloat(fieldValue);
                            method.invoke(instance, value);//设置值
                        }else if(typeName.equals(Byte.class.getName())){//如果是Byte类型的
                            Byte value = Byte.parseByte(fieldValue);
                            method.invoke(instance, value);//设置值
                        }else if(typeName.equals(Character.class.getName())){//如果是Character类型的
                            Character value = fieldValue.toCharArray()[0];
                            method.invoke(instance, value);//设置值
                        }else if(typeName.equals(Short.class.getName())){//如果是Short类型的
                            Short value = Short.parseShort(fieldValue);
                            method.invoke(instance, value);//设置值
                        }else if(typeName.equals(Long.class.getName())){//如果是Long类型的
                            Long value = Long.parseLong(fieldValue);
                            method.invoke(instance, value);//设置值
                        }else if(typeName.equals(Boolean.class.getName())){//如果是Boolean类型的
                            Boolean value = Boolean.parseBoolean(fieldValue);
                            method.invoke(instance, value);//设置值
                        }
                    }else{
                        method.invoke(instance, fieldValue);//设置值
                    }
				}
			}catch(Exception e){
				e.printStackTrace();
				instance = null;
			}
		}
		return instance;
	}
	
	/**
	 * 数组转字符串
	 * */
	private static String arratToString(String[] array){
		String str = "";
		if(array != null && array.length > 0){
			for (String string : array) {
				if(!"".equals(str)){
					str += ",";
				}
				str += string;
			}
		}
		return str;
	}
	
}
