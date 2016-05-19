package com.boyuanitsm.zhetengba.utils;


import com.boyuanitsm.zhetengba.bean.PhoneInfo;

import java.util.Comparator;

/**
 * 
 * @author wangbin
 *
 */
public class PinyinComparator implements Comparator<PhoneInfo> {

	public int compare(PhoneInfo o1, PhoneInfo o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
