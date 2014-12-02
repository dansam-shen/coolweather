package net.itaem.util;

/**
 * @author Administrator
 * 处理从天气接口返回的数据的接口
 */
public interface HttpCallbackListener {
	void onFinish(String string);

	void onError();
}
