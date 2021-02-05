package com.wechat.robot.http;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wechat.robot.entity.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author QianMY
 * @date 2020-12-18 16:32
 */
@Component
public class RobotHttp {

	@Value("${wechat.robot.address.qian}")
	private String robotAddress;

	@Autowired
	private WeatherHttp weatherClient;

	public void sendRobotMessage(String cityCode) {
		//获取天气信息
		final String message = weatherClient.getWeather(cityCode);

		//封装消息
		final TextMessage textMessage = new TextMessage();
		final TextMessage.Text text = new TextMessage.Text();
		text.setContent(message);
		textMessage.setText(text);
		final String infoJson = JSONUtil.toJsonStr(textMessage);
		System.err.println(infoJson);

		//发送消息
		HttpUtil.post(robotAddress, infoJson);
	}
}
