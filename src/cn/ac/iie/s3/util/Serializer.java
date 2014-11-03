package cn.ac.iie.s3.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * 序列化、反序列化辅助类，用于提供针对通用对象的序列化、反序列化
 * 
 * @author dayutianfei
 * @since s3-0.0.1
 */
public final class Serializer {
	private static final SerializableSerializer ser = new SerializableSerializer();

	/**
	 * *推荐使用*
	 * @param obj
	 * @return
	 */
	public static final byte[] serializeZK(Object obj) {
		return ser.serialize(obj);
	}

	/**
	 * *推荐使用*
	 * @param bs
	 * @return
	 */
	public static final Object deserializeZK(byte[] bs) {
		return ser.deserialize(bs);
	}

	/**
	 * @see {serializeZK}
	 * @param obj
	 * @return
	 * @deprecated
	 */
	public final static byte[] serialize(Object obj) {
		ByteArrayOutputStream byteArrayOS;
		try {
			byteArrayOS = new ByteArrayOutputStream();
			ObjectOutputStream stream = new ObjectOutputStream(byteArrayOS);
			stream.writeObject(obj);
			stream.close();
			return byteArrayOS.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * @see {deserializeZK}
	 * @param bs
	 * @return
	 * @deprecated
	 */
	public final static Object deserialize(byte[] bs) {
		try {
			ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
			ObjectOutputStream stream = new ObjectOutputStream(byteArrayOS);
			stream.writeObject(bs);
			stream.close();
			return byteArrayOS.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}