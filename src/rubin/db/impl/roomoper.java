package rubin.db.impl;

import rubin.bean.room;
import rubin.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class roomoper {
	private Connection conn;
	private String sql;
	public roomoper(){
		this.conn = DBConnection.getInstance().getConn();	
	}
	/**
	 * 获取所有房间，分页
	 */
	public ArrayList<room> getAll(int page, int pagesize, String nameKey) {

		ArrayList<room> list = new ArrayList<>();
		if(nameKey == null || nameKey.trim().length() == 0){
			sql = "select * from room limit ?,?";
		}else{
			sql ="select * from room where rtype like ? limit ?,?";
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
				room p = new room(rs.getInt(1), rs.getString(2), rs.getInt(3),rs.getString(4),rs.getString(5));
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
	 * 查询房间类型
	 */ 
	public int getTotal(String nameKey){
		if(nameKey == null || nameKey.trim().length() == 0){
			sql = "select count(*) from room";
		} else{
			sql = "select count(*) from room where rtype like ?";
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
	 * 更新房间信息，入住状态
	 */
	public void updateroom(int rid,String rstate){
		sql = "update room set rstate=? where rid=?";
		try {
			PreparedStatement psta = conn.prepareStatement(sql);
			psta.setString(1, rstate);
			psta.setInt(2,rid);
			psta.executeUpdate();
			psta.close();
			psta =null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
