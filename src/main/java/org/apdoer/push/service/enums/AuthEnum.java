package org.apdoer.push.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author apdoer
 */

@AllArgsConstructor
@Getter
public enum AuthEnum {
	/**
	 *
	 */
	AUTH(0, "需鉴权"),
	NOTAUTH(1, "不需鉴权");

	private Integer code;
	private String title;

	public Integer getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public static AuthEnum getByCode(Integer code) {
		for (AuthEnum e : AuthEnum.values()) {
			if (e.getCode().equals(code)) {
				return e;
			}
		}
		return null;
	}

	public static String getTitle(Integer code) {
		AuthEnum e = getByCode(code);
		return e == null ? null : e.getTitle();
	}

	public static Boolean vaild(Integer field) {
		if (null == getByCode(field)) {
			return false;
		}
		return true;
	}
}
