package rubin.bean;

public class admin {
	private int aid;
	private String aname,apwd,atel;
	public admin(int aid, String aname, String apwd, String atel) {
		super();
		this.aid = aid;
		this.aname = aname;
		this.apwd = apwd;
		this.atel = atel;
	}
	
	public admin(String aname) {
		super();
		this.aname = aname;
	}

	public admin (){
		
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getApwd() {
		return apwd;
	}
	public void setApwd(String apwd) {
		this.apwd = apwd;
	}
	public String getAtel() {
		return atel;
	}
	public void setAtel(String atel) {
		this.atel = atel;
	}
	@Override
	public String toString() {
		return "admin [aid=" + aid + ", aname=" + aname + ", apwd=" + apwd + ", atel=" + atel + "]";
	}
	
}
