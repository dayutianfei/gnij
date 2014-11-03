package cn.ac.iie.s3.util;

import net.sf.json.JSONObject;

public class JsonUtil {

	/**
	 * 将bean对象转换为JsonObject
	 * 
	 * @param bean
	 * @return
	 */
	public static JSONObject beadToJSON(Object bean) {
		return JSONObject.fromObject(bean);

	}

	/**
	 * 将jsonStr转换为简单Bean
	 * @param jsonStr
	 * @param beanClass
	 * @return
	 */
	public static Object jsonStrToBean(String jsonStr,  Class<?> beanClass) {
		return JSONObject.toBean(JSONObject.fromObject(jsonStr), beanClass);
	}

	public static void print(Object o) {
		System.out.println(o.toString());
	}

}
