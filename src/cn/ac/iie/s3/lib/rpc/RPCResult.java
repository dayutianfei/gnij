package cn.ac.iie.s3.lib.rpc;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Writable;

/**
 * HadoopRPC传输的对象需要实现Writable，或为基本数据类型
 * 
 * @author egret
 * @since s3-0.0.1
 */
public class RPCResult implements Serializable,Writable{

	private static final long serialVersionUID = 1L;
	
	private String message = "";
	private List<String> params = new ArrayList<String>();
	
	public String getMessage() {
		return message.toString();
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		int len = in.readInt();
		if (len != 0) {
			message = in.readUTF();
		}
		params = new ArrayList<String>();
		int size = in.readInt();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				params.add(in.readUTF());
			}
		}
	}
	@Override
	public void write(DataOutput out) throws IOException {
		if (null == message) {
			out.writeInt(0);
		} else {
			out.writeInt(message.length());
			out.writeUTF(message);
		}
		if (null  != params && !params.isEmpty()) {
			out.writeInt(params.size());
			for (String k : params) {
				out.writeUTF(k);
			}
		} else{
			out.writeInt(0);
		}
	}
}
