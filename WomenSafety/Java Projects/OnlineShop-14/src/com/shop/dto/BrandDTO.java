package com.shop.dto;

public class BrandDTO {
private int pid;
private int bid;
private String name;
public int getPid() {
	return pid;
}
public void setPid(int pid) {
	this.pid = pid;
}
public int getBid() {
	return bid;
}
public void setBid(int bid) {
	this.bid = bid;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
@Override
public String toString() {
	return "BrandDTO [pid=" + pid + ", bid=" + bid + ", name=" + name + "]";
}

}
