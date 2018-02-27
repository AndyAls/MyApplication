package cn.evun.javebean;


import cn.evun.util.PinyinUtil;

/**
 * Created by ccy on 2016/9/12.
 */
//让当前类实现Comparable,这样这个类就具有可排序性
public class ContactInfo implements Comparable<ContactInfo>{
	private String name;
	private String firstLetter;

	public ContactInfo(String name) {
		this.name = name;
		//截取转化拼音中的首字母
		this.firstLetter= PinyinUtil.chineseWordToPinyin(name).substring(0,1);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	@Override
	public String toString() {
		return "ContactInfo{" +
				"name='" + name + '\'' +
				", firstLetter='" + firstLetter + '\'' +
				'}';
	}

	@Override
	public int compareTo(ContactInfo another) {
		//实际比较的是首字母
		return this.firstLetter.compareTo(another.firstLetter);
	}
}
