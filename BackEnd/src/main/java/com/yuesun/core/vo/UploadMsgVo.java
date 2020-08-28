package com.yuesun.core.vo;

public class UploadMsgVo {

	private int errno = 0;
	private int success = 1;
	private Object data;

	public UploadMsgVo(Object data) {
		this.data = data;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getErrno() {
		return errno;
	}

	public void setErrno(int errno) {
		this.errno = errno;
	}

	@Override
	public String toString() {
		return "UploadMsgVo [errno=" + errno + ", success=" + success + ", data=" + data + "]";
	}
	
}
