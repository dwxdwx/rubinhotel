package rubin.bean;

public class order {
	private int oid,uid,rid,omoney,dealaid;
	private String ostate,uname,aname;
	public order(int oid, int uid, int rid, int omoney, String ostate, int dealaid) {
		super();
		this.oid = oid;
		this.uid = uid;
		this.rid = rid;
		this.omoney = omoney;
		this.ostate = ostate;
		this.dealaid = dealaid;
	}
	
	public order(int oid,String uname,int rid, int omoney, String ostate,  String aname) {
		super();
		this.oid = oid;
		this.omoney = omoney;
		this.rid = rid;
		this.ostate = ostate;
		this.uname = uname;
		this.aname = aname;
	}

	public order(int oid, int rid, int omoney, String ostate) {
		super();
		this.oid = oid;
		this.rid = rid;
		this.omoney = omoney;
		this.ostate = ostate;
	}

	public order() {
		
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getOmoney() {
		return omoney;
	}

	public void setOmoney(int omoney) {
		this.omoney = omoney;
	}

	public int getDealaid() {
		return dealaid;
	}

	public void setDealaid(int dealaid) {
		this.dealaid = dealaid;
	}

	public String getOstate() {
		return ostate;
	}

	public void setOstate(String ostate) {
		this.ostate = ostate;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	@Override
	public String toString() {
		return "order [oid=" + oid + ", uid=" + uid + ", rid=" + rid + ", omoney=" + omoney + ", dealaid=" + dealaid
				+ ", ostate=" + ostate + ", uname=" + uname + ", aname=" + aname + "]";
	}
	
	
	
}
