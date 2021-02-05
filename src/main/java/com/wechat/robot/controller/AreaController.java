package com.wechat.robot.controller;

import com.wechat.robot.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author QianMY
 * @date 2020-12-30 10:07
 */
@RestController
@RequestMapping("/area")
public class AreaController {

	@Autowired
	private AreaService areaService;

	@GetMapping("")
	public void test(){
		areaService.showAreaInfo();
	}

	@PostMapping("")
	public void add(){
		areaService.addAreaInfo();
	}
}
