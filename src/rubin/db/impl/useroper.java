package rubin.db.impl;

import rubin.bean.user;
import rubin.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class useroper {
	private Connection conn;
	private String sql;
	public useroper(){
		this.conn = DBConnection.getInstance().getConn();
	}
	/**
	 * 查询所有用户，分页
	 */
	public ArrayList<user> getAll(int page, int pagesize, String nameKey) {

		ArrayList<user> list = new ArrayList<>();
		if(nameKey == null || nameKey.trim().length() == 0){
			sql = "select * from custom limit ?,?";
		}else{
			sql ="select * from custom where uname like ? limit ?,?";
		}
		
		try {
			PreparedStatement psta = conn.prepareStatement(sql);
			if(nameKey == null || nameKey.trim().length() == 0){
				psta.setInt(1, (page-1)*pagesize);
				psta.setInt(2, pagesize);
			} else{
				psta.setString(1, "%"+ nameKey + "%");
				psta.setInt(2, (page-1)*pagesize);
				psta.setInt(3, pagesize);
			}
			
			ResultSet rs = psta.executeQuery();
			while(rs.next()){
				user p = new user(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getDate(4),rs.getString(5), rs.getString(6));
				list.add(p);
//				System.out.println(p);
			}
			
			rs.close();
			psta.close();
			psta = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	/**
	 * 查询用户
	 */ 
	public int getTotal(String nameKey){
		if(nameKey == null || nameKey.trim().length() == 0){
			sql = "select count(*) from custom";
		} else{
			sql = "select count(*) from custom where uname like ?";
		}
		
		int count = 0;
		try {
		PreparedStatement psta = conn.prepareStatement(sql);
		if(!(nameKey == null || nameKey.trim().length() == 0)){
			psta.setString(1,  "%"+ nameKey + "%");
		}
		ResultSet rs = psta.executeQuery();
		if(rs.next()){
			count = rs.getInt(1);
		}
		
		rs.close();
		psta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return count;
	}
	
	/**
	 * 新增用户
	 */
	public void adduser(String uname,String uidnum,String ucheck_out,String utel){
		
		try {
		sql = "insert into custom values(null,?,?,?,?,?)";
		PreparedStatement psta = conn.prepareStatement(sql);
		//?赋值
	
		psta.setString(1,uname);
		psta.setString(2,uidnum);
		psta.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
		psta.setString(4,ucheck_out);
		psta.setString(5,utel);
		//执行sql
		psta.executeUpdate();
		//关闭
		psta.close();
//		psta = null;
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	/**
	 * 更新用户信息，身份证号，离店时间，电话号码
	 */
	public void updateuser(int uid,String uidnum,String ucheck_out,String utel){
		
		try {
			sql = "update custom set uidnum=?,ucheck_out=?,utel=? where uid=?";
			PreparedStatement psta = conn.prepareStatement(sql);
			psta.setString(1, uidnum);
			psta.setString(2, ucheck_out);
			psta.setString(3, utel);
			psta.setInt(4,uid);
			psta.executeUpdate();
			psta.close();
			psta =null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 删除单个用户
	 */
	public void delById(int uid){
		sql = "delete from custom where uid=?";
		try {
			PreparedStatement psta = conn.prepareStatement(sql);
			psta.setInt(1, uid);
			psta.executeUpdate();
			psta.close();
			psta =null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 删除多个用户
	 * @param id
	 */
	public void delByIds(String uids){
	sql = "delete from custom where uid in (" + uids + ")";
	try {
		PreparedStatement psta = conn.prepareStatement(sql);
		psta.executeUpdate();
		psta.close();
		psta =null;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}

	}
}
