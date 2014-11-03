package cn.ac.iie.s3.util;

import org.I0Itec.zkclient.serialize.SerializableSerializer;

public final class Serializer
{
  private static final SerializableSerializer ser = new SerializableSerializer();

  public static final byte[] serialize(Object obj) { return ser.serialize(obj); }

  public static final Object deserialize(byte[] bs)
  {
    return ser.deserialize(bs);
  }
  
//	public final static byte[] serialize(Object obj) {
//	ByteArrayOutputStream byteArrayOS;
//	try {
//		byteArrayOS = new ByteArrayOutputStream();
//		ObjectOutputStream stream = new ObjectOutputStream(byteArrayOS);
//		stream.writeObject(obj);
//		stream.close();
//		return byteArrayOS.toByteArray();
//	} catch (IOException e) {
//		throw new RuntimeException(e);
//	}
//
//}
//
//public final static Object deserialize(byte[] bs) {
//	try {
//		ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
//		ObjectOutputStream stream = new ObjectOutputStream(byteArrayOS);
//		stream.writeObject(bs);
//		stream.close();
//		return byteArrayOS.toByteArray();
//	} catch (IOException e) {
//		throw new RuntimeException(e);
//	}
//}
}