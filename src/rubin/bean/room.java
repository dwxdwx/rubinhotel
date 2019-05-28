package rubin.bean;

public class room {
	private int rid,rmoney;
	private String rtype,rbreak,rstate;
	public room(int rid, String rtype, int rmoney, String rbreak, String rstate) {
		super();
		this.rid = rid;
		this.rmoney = rmoney;
		this.rtype = rtype;
		this.rbreak = rbreak;
		this.rstate = rstate;
	}
	public room(){
		
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getRmoney() {
		return rmoney;
	}
	public void setRmoney(int rmoney) {
		this.rmoney = rmoney;
	}
	public String getRtype() {
		return rtype;
	}
	public void setRtype(String rtype) {
		this.rtype = rtype;
	}
	public String getRbreak() {
		return rbreak;
	}
	public void setRbreak(String rbreak) {
		this.rbreak = rbreak;
	}
	public String getRstate() {
		return rstate;
	}
	public void setRstate(String rstate) {
		this.rstate = rstate;
	}
	@Override
	public String toString() {
		return "room [rid=" + rid + ", rmoney=" + rmoney + ", rtype=" + rtype + ", rbreak=" + rbreak + ", rstate="
				+ rstate + "]";
	}
	
}
