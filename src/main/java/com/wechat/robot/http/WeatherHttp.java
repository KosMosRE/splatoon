package com.wechat.robot.http;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wechat.robot.entity.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * 天气预报调用
 *
 * @author QianMY
 * @date 2020-12-18 14:28
 */
@Component
public class WeatherHttp {

	public static final String CODE_WUXI = "320200";
	public static final String CODE_SUZHOU = "320500";
	public static final String WEATHER_ADDRESS = "https://restapi.amap.com/v3/weather/weatherInfo?city={0}&key={1}";

	@Value("${ali.gd.key}")
	private String key;

	public String getWeather(String cityCode) {
		final String address = MessageFormat.format(WEATHER_ADDRESS, cityCode, key);
		final String info = HttpUtil.get(address);
		final Weather weather = JSONUtil.toBean(info, Weather.class);
		return convert(weather);
	}

	private String convert(Weather weather) {
		final Weather.Lives lives = weather.getLives().get(0);
		String template =
				"这里是帅气的{6}天气播报员阿钱：" +
						"> 今天天气:  {0} " +
						"> 实时气温:  {1}摄氏度" +
						"> 风向:  {2}" +
						"> 风力级别:  {3}级" +
						"> 空气湿度:  {4}" +
						"> ps:天气数据发布的时间--{5}";
		return MessageFormat.format(template, lives.getWeather(), lives.getTemperature(), lives.getWinddirection(),
				lives.getWindpower(), lives.getHumidity(), lives.getReporttime(), lives.getCity());
	}
}
