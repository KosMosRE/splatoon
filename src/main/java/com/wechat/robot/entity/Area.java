package com.wechat.robot.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author QianMY
 * @date 2020-12-30 09:52
 */
@Entity
@Table(name = "t_area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "Area.Graph", attributeNodes = {
		@NamedAttributeNode("child")
})
public class Area implements Serializable {

	public Area(String name, Area parent) {
		this.name = name;
		this.parent = parent;
	}

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	@Column(name = "name")
	private String name;

	@JoinColumn(name = "parent_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JSONField(serialize = false)
	private Area parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@JSONField(serialize = false)
	private List<Area> child;


	public static void main(String[] args) {
		final Area ceshi = new Area("ceshi", null);
		ceshi.setId("12");
		System.out.println(JSONObject.toJSONString(ceshi));
	}
}
