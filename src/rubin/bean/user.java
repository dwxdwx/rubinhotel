package rubin.bean;

import java.util.Date;

public class user {
	private int uid;
	private String uname,uidnum,utel,ucheck_out;
	private Date ucheck_in;
	public user(int uid, String uname, String uidnum, Date ucheck_in, String ucheck_out,String utel) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.uidnum = uidnum;
		this.utel = utel;
		this.ucheck_in = ucheck_in;
		this.ucheck_out = ucheck_out;
	}
	
	public user(String uname) {
		super();
		this.uname = uname;
	}

	public user(){
		
	}
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUidnum() {
		return uidnum;
	}
	public void setUidnum(String uidnum) {
		this.uidnum = uidnum;
	}
	public String getUtel() {
		return utel;
	}
	public void setUtel(String utel) {
		this.utel = utel;
	}
	public String getUcheck_out() {
		return ucheck_out;
	}
	public void setUcheck_out(String ucheck_out) {
		this.ucheck_out = ucheck_out;
	}
	public Date getUcheck_in() {
		return ucheck_in;
	}
	public void setUcheck_in(Date ucheck_in) {
		this.ucheck_in = ucheck_in;
	}
	@Override
	public String toString() {
		return "user [uid=" + uid + ", uname=" + uname + ", uidnum=" + uidnum + ", utel=" + utel + ", ucheck_in="
				+ ucheck_in + ", ucheck_out=" + ucheck_out + "]";
	}
	
}
