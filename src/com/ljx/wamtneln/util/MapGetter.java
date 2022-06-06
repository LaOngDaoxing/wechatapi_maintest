package com.ljx.wamtneln.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* @Description: 提供从Map中获取指定类型数据的功能 
* @FR功能需求：
* @ImportJar: 
* @ApiGrammer规则：
* @Remark: 
* @CodeBug解决:
* @date 2021年3月15日 下午5:16:37 
* @author  ljx 
*
 */
public class MapGetter {
	public static void main(String[] args) {

	}
	/**
	 * 	
	 * @Title: newHashMapWithExpectedSize 
	 * @Description: 根据预期要创建HashMap的大小,创建HashMap
	 * @param expectedSize 预期要创建HashMap的大小
	 * @return HashMap &lt;K, V&gt; 返回HashMap对像
	 */
	public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
	    return new HashMap<K, V>(initialCapacity (expectedSize));
	}
	
	/**
	 * 
	 * @Title: initialCapacity 
	 * @Description: 根据预期要创建HashMap的大小，计算实际初始化容量值
	 * @param expectedSize 预期要创建HashMap的大小
	 * @return int 返回类型
	 */
	public static int initialCapacity (int expectedSize) {
	      return (int) (expectedSize / 0.75F + 1.0F);
	}
	
	/**
	 * 
	 * @Title: defaultInitialCapacity 
	 * @Description: 获取默认值(16)，计算实际初始化容量值
	 * @return int 返回类型
	 */
	public static int defaultInitialCapacity () {
		return (int) (16 / 0.75F + 1.0F);
	}
	
	/**
	 * 
	 * @Title: getInt 
	 * @Description: 获取整型值
	 * @param map 指定Map对象
	 * @param key 指定Key值<br>
	 * @return int 返回类型<br>
	 */
	public static int getInt(Map<String, Object> map, String key) {
		try {
			return Integer.parseInt(map.get(key).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 
	 * @Title: getInt 
	 * @Description: 获取整型值
	 * @param map 指定Map对象
	 * @param key 指定Key值
	 * @param defaultValue 默认返回值 
	 * @return int 返回类型
	 */
	public static int getInt(Map<String, Object> map, String key,int defaultValue) {
		try {
			return (Integer) map.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	/**
	 * 
	 * @Title: getLong
	 * @Description: 获取整型值
	 * @param map 指定Map对象
	 * @param key 指定Key值<br>
	 * @return int 返回类型<br>
	 */
	public static long getLong(Map<String, Object> map, String key) {
		try {
			return Long.parseLong(map.get(key).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @Title: getString 
	 * @Description: 获取字符串
	 * @param map 指定Map对象
	 * @param key 指定Key值
	 * @return String 返回类型
	 */
	public static String getString(Map<String, Object> map, String key) {
		try {
			String reValue = "";
			Object objValue = map.get(key);
			if (null == objValue) {
				return reValue;
			}

			if (objValue instanceof String) {
				// reValue = ((String) objValue).replaceAll(".*([';]+|(--)+).*","");
				reValue = ((String) objValue).replaceAll("'", "‘");
				// reValue = ((String) objValue);
			} else if (objValue instanceof Integer) {
				reValue = String.valueOf(((Integer) objValue).intValue());
			} else {
				reValue = objValue.toString();
			}

			return reValue;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
	/**
	 * 
	 * @Title: getBoolean
	 * @Description: 获取布尔值
	 * @param map 指定Map对象
	 * @param key 指定Key值
	 * @return int 返回类型
	 */
	public static Boolean getBoolean(Map<String, Object> map, String key) {
		Boolean bool=false;
		try {
			return (Boolean) map.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}
	/**
	 * 
	 * @Title: getDate 
	 * @Description: 获取日期类型
	 * @param map 指定Map对象
	 * @param key 指定Key值
	 * @return Date 返回类型
	 */
	public static Date getDate(Map<String, Object> map, String key) {
		try {
			return (Date) map.get(key);
		} catch (Exception e) {
			 e.printStackTrace();
		}

		return null;
	}
	/**
	 * 
	 * @Title: getDouble 
	 * @Description: 获取double型
	 * @param map 指定Map对象
	 * @param key 指定Key值
	 * @return double 返回类型
	 */
	public static double getDouble(Map<String, Object> map, String key) {
		try {
			return Double.parseDouble(getString(map, key));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0.0;
	}

	/**
	 * 
	 * @Title: getBigDecimal 
	 * @Description: 获取BigDecimal型 
	 * @param map 指定Map对象
	 * @param key 指定Key值
	 * @return BigDecimal 返回类型
	 */
	public static BigDecimal getBigDecimal(Map<String, Object> map, String key) {
		try {
			BigDecimal value = (BigDecimal) map.get(key);
			if (value == null) {
				return BigDecimal.ZERO ;
			}
			return (BigDecimal) map.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BigDecimal.ZERO;
	}
	/**
	 * 
	* @Title: getStringArr 
	* @Description: 根据key，返回String[]格式数据
	* @param map
	* @param key
	* @return    设定文件 
	* @return String[]    返回类型 
	* @throws
	 */
	public static String[] getStringArr(Map<String,Object> map, String key) {
		try {
			return (String[]) map.get(key);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	* @Title: getReturnMap 
	* @Description: 根据key，返回Map<String,Object>格式数据
	* @param paramsMap
	* @param key
	* @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public static Map<String,Object> getReturnMap(Map<String,Object> paramsMap, String key) {
		try {
			/*
				 使用强制类型转换：Object——>转Map，但paramsMap的类型本质依旧为JSONObject (一个Map的实现类)。
	    	 	backMap类型为JSONObject
    	 	*/
			Map backMap=(Map<String,Object>) paramsMap.get(key);
			return backMap;
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	* @Title: getListMap 
	* @Description: 根据key，返回List<Map<String,Object>>格式数据
	* @param map
	* @param key
	* @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public static List<Map<String,Object>> getListMap(Map<String,Object> map, String key) {
		try {
			return (List<Map<String,Object>>) map.get(key);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return null;
	}
}
