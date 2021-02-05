package com.wechat.robot.entity;

import lombok.*;

/**
 * @author QianMY
 * @date 2020-12-18 16:44
 */
@Getter
@Setter
@AllArgsConstructor
public class TextMessage extends RobotMessage {
	public TextMessage() {
		super.setMsgtype("text");
	}

	private Text text;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Text {
		private String content;
		private String mentioned_list;
		private String mentioned_mobile_list;
	}

}
