package rubin.db.impl;

import rubin.bean.order;
import rubin.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class orderoper {

	private Connection conn;
	private String sql;
	
	public orderoper() {
		this.conn = DBConnection.getInstance().getConn();
	}
	
	/**
	 * 获取所有商品信息,分页
	 * @param page -- 当前显示页码，pageSize -- 每页记录个数
	 * @return
	 */
	public ArrayList<order> getAll(int page, int pageSize, String nameKey){
		ArrayList<order> list = new ArrayList<>();
		if(nameKey==null||nameKey.trim().length()==0) {
			sql = "select oid,uname,rid,omoney,ostate,aname from orders,custom,admin where custom.uid=orders.uid and admin.aid=orders.dealaid limit ?,?";
		}else {
			sql="select oid,uname,rid,omoney,ostate,aname from orders,custom,admin where custom.uid=orders.uid and admin.aid=orders.dealaid and uname like ? limit ?,?";
		}
		
		 
		try {
			PreparedStatement psta = conn.prepareStatement(sql);
			if(nameKey==null||nameKey.trim().length()==0) {
				psta.setInt(1, (page-1)*pageSize);
				psta.setInt(2, pageSize);
			}else {
				psta.setString(1, "%"+nameKey+"%");
				psta.setInt(2, (page-1)*pageSize);
				psta.setInt(3, pageSize);
			}
			
			ResultSet rs = psta.executeQuery();
			while(rs.next()) {
				order o = new order(rs.getInt(1), rs.getString(2),rs.getInt(3), rs.getInt(4), rs.getString(5),rs.getString(6));
				list.add(o);
				
			}
			rs.close();
			psta.close();
			psta=null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	public int getTotal(String nameKey){
		
		if(nameKey==null||nameKey.trim().length()==0) {
			sql = "select count(*) from orders";
		}else {
			sql = "select count(*) from orders,custom where custom.uid=orders.uid and uname like ?";
		}
		int count = 0;
		try {
			PreparedStatement psta = conn.prepareStatement(sql);
			if(!(nameKey==null||nameKey.trim().length()==0)) {
				psta.setString(1, "%"+nameKey+"%");
			}
			ResultSet rs = psta.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			rs.close();
			psta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/*
	 * 更新商品信息：价格和数量
	 */
	public void updateorder(int omoney,String ostate,int oid) {
		sql="update orders set omoney=? ,ostate=? where oid=?";
		
		try {
			PreparedStatement psta = conn.prepareStatement(sql);
			psta.setInt(1, omoney);
			psta.setString(2, ostate);
			psta.setInt(3, oid);
			psta.executeUpdate();
			psta.clearBatch();
			psta.close();
			psta=null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/*
	 * 删除单行数据
	 */
	public void delById(int oid) {
		sql = "delete from orders where oid=?";
		
		try {
			PreparedStatement psta = conn.prepareStatement(sql);
			psta.setInt(1, oid);
			psta.executeUpdate();
			psta.clearBatch();
			psta.close();
			psta=null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 删除多行数据
	 * @param ids:使用逗号分隔的若干的id值
	 */
	public void delByIds(String ids) {
		sql = "delete from orders where oid in("+ids+")";
		
		try {
			PreparedStatement psta = conn.prepareStatement(sql);
			
			psta.executeUpdate();
			psta.clearBatch();
			psta.close();
			psta=null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 添加商品信息
	 */
	public void addorder(int uid,int rid,int omoney,String ostate,int dealaid) {
		sql="insert into orders values(null,?,?,?,?,?)";
		
		try {
			PreparedStatement psta = conn.prepareStatement(sql);
			psta.setInt(1, uid);
			psta.setInt(2, rid);
			psta.setInt(3, omoney);
			psta.setString(4, ostate);
			psta.setInt(5, dealaid);
			psta.executeUpdate();
			psta.clearBatch();
			psta.close();
			psta=null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
