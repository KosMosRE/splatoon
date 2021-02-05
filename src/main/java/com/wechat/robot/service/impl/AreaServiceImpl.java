package com.wechat.robot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wechat.robot.dao.AreaDao;
import com.wechat.robot.entity.Area;
import com.wechat.robot.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author QianMY
 * @date 2020-12-30 10:11
 */
@Service
@Transactional(rollbackFor = Exception.class)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao areaDao;

	@Override
	public void addAreaInfo() {
		final Area js = new Area("江苏省", null);
		areaDao.save(js);

		final Area nj = new Area("南京", js);
		final Area sz = new Area("苏州", js);
		areaDao.save(nj);
		areaDao.save(sz);
	}

	@Override
	public void showAreaInfo() {
		final List<Area> all = areaDao.findAll();
		final Area area = all.get(0);
		System.out.println(JSONObject.toJSONString(area));
	}
}
