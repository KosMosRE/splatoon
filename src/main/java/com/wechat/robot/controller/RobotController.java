package com.wechat.robot.controller;

import com.wechat.robot.http.RobotHttp;
import com.wechat.robot.http.WeatherHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author QianMY
 * @date 2020-12-18 14:57
 */
@RestController
@RequestMapping("/robot")
public class RobotController {

	@Autowired
	private RobotHttp robotHttp;

	@GetMapping("/get")
	public void test(){
		robotHttp.sendRobotMessage(WeatherHttp.CODE_WUXI);
	}
}
