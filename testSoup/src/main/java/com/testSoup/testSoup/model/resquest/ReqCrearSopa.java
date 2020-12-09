package com.testSoup.testSoup.model.resquest;

public class ReqCrearSopa {
	private Integer w;
	private Integer h;
	private Boolean ltr;
	private Boolean rtl;
	private Boolean ttb;
	private Boolean btt;
	private Boolean d;
	
	public Integer getW() {
		return w;
	}
	public void setW(Integer w) {
		this.w = w;
	}
	public Integer getH() {
		return h;
	}
	public void setH(Integer h) {
		this.h = h;
	}
	public Boolean getLtr() {
		return ltr;
	}
	public void setLtr(Boolean ltr) {
		this.ltr = ltr;
	}
	public Boolean getRtl() {
		return rtl;
	}
	public void setRtl(Boolean rtl) {
		this.rtl = rtl;
	}
	public Boolean getTtb() {
		return ttb;
	}
	public void setTtb(Boolean ttb) {
		this.ttb = ttb;
	}
	public Boolean getBtt() {
		return btt;
	}
	public void setBtt(Boolean btt) {
		this.btt = btt;
	}
	public Boolean getD() {
		return d;
	}
	public void setD(Boolean d) {
		this.d = d;
	}
	@Override
	public String toString() {
		return "ReqCrearSopa [w=" + w + ", h=" + h + ", ltr=" + ltr + ", rtl=" + rtl + ", ttb=" + ttb + ", btt=" + btt
				+ ", d=" + d + "]";
	}
	
}
