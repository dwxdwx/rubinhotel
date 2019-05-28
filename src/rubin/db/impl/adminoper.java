package rubin.db.impl;

import rubin.bean.admin;
import rubin.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class adminoper {
	private Connection conn;
	private String sql;
	public adminoper(){
		conn = DBConnection.getInstance().getConn();
	}
	/**
	 * 用户登录校验
	 * @param name
	 * @param pass
	 * @return 登陆成功返回user对象，登录失败，返回null
	 */
	public admin logincheck(String aname, String apwd){
		
		admin admin = null;
		//准备执行sql语句
		try {
			sql = "select * from admin where aname=? and apwd=?";
			PreparedStatement psta = conn.prepareStatement(sql);
			//?赋值
			psta.setString(1,aname);
			psta.setString(2,apwd);
			//执行sql
			ResultSet rs = psta.executeQuery();
			//结果处理
			if(rs.next()){
				int aid = rs.getInt(1);
				String name = rs.getString(2);
				String apass = rs.getString(3);
				String atel = rs.getString(4);
				admin = new admin(aid, name, apass, atel);
			}
			rs.close();
			psta.close();
			psta = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return admin;
	}
	/**
	 * 获取所有房间，分页
	 */
	public ArrayList<admin> getAll(int page, int pagesize, String nameKey) {

		ArrayList<admin> list = new ArrayList<>();
		if(nameKey == null || nameKey.trim().length() == 0){
			sql = "select * from admin limit ?,?";
		}else{
			sql ="select * from admin where aname like ? limit ?,?";
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
				admin p = new admin(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4));
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
	 * 查询管理员信息
	 */ 
	public int getTotal(String nameKey){
		if(nameKey == null || nameKey.trim().length() == 0){
			sql = "select count(*) from admin";
		} else{
			sql = "select count(*) from admin where aname like ?";
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
	 * 修改管理员信息
	 */
	public void updateadmin(int aid,String atel){
		sql = "update admin set atel=?  where aid=?";
		try {
			PreparedStatement psta = conn.prepareStatement(sql);
			psta.setString(1, atel);
			psta.setInt(2,aid);
			psta.executeUpdate();
			psta.close();
			psta =null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 新增管理员，void没有返回值
	 */

	public void addadmin(String name, String pass,String atel){
		
		try {
			sql = "insert into admin values(null,?,?,?)";
			PreparedStatement psta = conn.prepareStatement(sql);
			//?赋值
			psta.setString(1,name);
			psta.setString(2,pass);
			psta.setString(3, atel);
			//执行sql
			psta.executeUpdate();
			//关闭
			psta.close();
			psta = null;
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 检测用户是否存在
	 * @param name
	 * @return true存在，false不存在
	 */
	public boolean isexist(String name){
		boolean flag = false;
		sql = "select * from admin where aname=?";
		try {
			//准备执行sql语句
			PreparedStatement psta = conn.prepareStatement(sql);
			//?赋值
			psta.setString(1,name);
			//执行sql
			ResultSet rs = psta.executeQuery();
			//结果处理
			if(rs.next()){
				flag = true;
			}
			//关闭
			rs.close();
			psta.close();
			psta = null;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	

}
