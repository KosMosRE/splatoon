package com.wechat.robot.dao;

import com.wechat.robot.entity.Area;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaDao extends JpaRepository<Area, String> {

	@Override
	@EntityGraph(value = "Area.Graph", type = EntityGraph.EntityGraphType.FETCH)
	List<Area> findAll();
}
