package com.bigdata.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBC 工具类
 * */
public class DBUtils {

	//连接
	private Connection conn = null;
	
	//操作对象
	private PreparedStatement ps = null;
	
	//结果对象
	private ResultSet rs = null;
	
	DataSource dataSourse = null;

    private DBUtils() {
        open();
    }

    private static DBUtils dbUtils;

    public static synchronized DBUtils getDBUtils(){
        if(dbUtils == null){
            dbUtils = new DBUtils();
        }
        return dbUtils;
    }

    private static ComboPooledDataSource cpds = new ComboPooledDataSource();
    /**
     * 通过配置文件的方式打开连接
     */
    public void open() {
        try {
            // 2.打开数据库连接
            conn = cpds.getConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	/**
     * 查询
     * @param sql 查询语句
     * @param clazz 需要封装的实体类，该实体类的字段名与数据库名一致(忽略大小写)
     * @param objs 可变数据
     * */
    public <T> List<T> query(String sql, Class<T> clazz, Object... objs){
        List<T> list = null;
        try{
            ps = conn.prepareStatement(sql);
            boolean flag = setPrepareStatement(sql, objs);
            if(!flag)//如果设置不成功
                return list;

            rs = ps.executeQuery();
            list = new ArrayList<>();
            while (rs.next()){
                T instance = clazz.newInstance();//获取实例
                instance = setInstance(clazz, instance, rs);
                //放入list中
                list.add(instance);
            }
        }catch (Exception e){
            e.printStackTrace();
            list = null;
        }finally {
            return list;
        }
    }
    
    /**
     * 查询单个对象
     * @param sql 查询语句
     * @param clazz 需要封装的实体类，该实体类的字段名与数据库名一致(忽略大小写)
     * @param objs 可变数据
     * */
    public <T> T queryObject(String sql, Class<T> clazz, Object... objs){
    	T instance = null;
        try{
            ps = conn.prepareStatement(sql);
            boolean flag = setPrepareStatement(sql, objs);
            if(!flag)//如果设置不成功
                return instance;

            rs = ps.executeQuery();
            if (rs.next()){
                instance = clazz.newInstance();//获取实例
                instance = setInstance(clazz, instance, rs);
            }
        }catch (Exception e){
            e.printStackTrace();
            instance = null;
        }finally {
            return instance;
        }
    }
    
    /**
     * 设置instance
     * */
    private <T> T setInstance(Class<T> clazz, T instance, ResultSet rs){
    	try{
    		Field[] fields = clazz.getDeclaredFields();//获取所有字段

            for (Field field : fields) {
                field.setAccessible(true);//设置可访问
                String fieldName = field.getName();//获取字段名称(与数据库中相同)

                String fieldValue = rs.getString(fieldName);//获取数据库中的值
   				
   				if(fieldValue == null)
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
    		instance = null;
    		e.printStackTrace();
    	}
    	return instance;
    }
 
    
    /**
     * 用于查询返回类型为整数的sql
     * @param sql 查询语句
     * @param objs 可变数据
     * @return 查询的结果
     * */
    public int queryInt(String sql, Object... objs){
    	int result = -1;
        try{
            ps = conn.prepareStatement(sql);
            boolean flag = setPrepareStatement(sql, objs);
            if(!flag)//如果设置不成功
                return result;

            rs = ps.executeQuery();
            rs.next();
            result = rs.getInt(1);
        }catch (Exception e){
            e.printStackTrace();
            result = -1;
        }finally {
            return result;
        }
    }
    
    /**
     * 用于查询多表数据
     * @param sql 查询语句
     * @param objs 可变数据
     * @return 查询的结果
     * */
    public ResultSet queryResult(String sql, Object... objs){
    	ResultSet rs = null;
        try{
            ps = conn.prepareStatement(sql);
            boolean flag = setPrepareStatement(sql, objs);
            if(!flag)//如果设置不成功
                return rs;
            rs = ps.executeQuery();
        }catch(Exception e){
        	e.printStackTrace();
        	rs = null;
        }finally{
        	return rs;
        }
    }

    /**
     * 更新
     * @param sql 更新的sql语句
     * @param objs 可变长度数据
     * */
    public boolean update(String sql, Object... objs){
        boolean flag = false;
        try {
            ps = conn.prepareStatement(sql);
            flag = setPrepareStatement(sql, objs);
            if(!flag)//如果设置不成功
                return flag;
            int result = ps.executeUpdate();
            flag = result == -1 ? false : true;
        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }finally {
            return flag;
        }
    }
    
    /**
     * 更新
     * @param sql 更新的sql语句
     * @param objs 可变长度数据
     * */
    public boolean updateBatch(String sql, Map<String, String> map){
        boolean flag = false;
        try {
            ps = conn.prepareStatement(sql);
            int count = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
            	count++;
				ps.setString(1, entry.getValue());
				ps.setInt(2, Integer.parseInt(entry.getKey()));
				ps.addBatch();
			}
            
            int[] result = ps.executeBatch();
            flag = result != null && result.length == count ? true : false;
        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }finally {
            return flag;
        }
    }
    
    /**
     * 设置参数，如果参数个数不匹配返回false
     * */
    private boolean setPrepareStatement(String sql, Object... objs) throws SQLException{
        boolean flag = true;
        if(getQuestionMarkSize(sql) == objs.length){
            for(int i = 0;i < objs.length;i++){
                ps.setObject(i + 1, objs[i]);
            }
        }else{
            flag = false;
        }
        return flag;
    }
    
    /**
     * 获取占位符?的个数
     * */
    private int getQuestionMarkSize(String sql){
        int count = 0;
        if(sql != null && sql.length() > 0){
           int fromIndex = 0;
           while(true){
               int index = sql.indexOf("?", fromIndex);
               if(index != -1){
                   count++;
                   if(index != sql.length() - 1)
                       fromIndex = index + 1;
                   else
                       break;
               }else{
                   break;
               }
           }
        }
        return count;
    }

    /**
     * 关闭
     * */
    public void close(){
        /*try {
            if(rs != null)
                rs.close();
            if(ps != null)
                ps.close();
            if(conn != null)
                conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }
}
