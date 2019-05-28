package rubin.servlet;

import rubin.bean.admin;
import rubin.db.DBConnection;
import rubin.db.impl.adminoper;
import rubin.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/adminservlet")
public class adminservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private adminoper adminOper;
	public adminservlet(){
		adminOper = new adminoper();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String method = request.getParameter("method");
		switch(method){
		case "getAll":
			getAll(request,response);
			break;
		case "loginCheck":
			loginCheck(request,response);
			break;
		case "updateAdmin":
			updateAdmin(request,response);
			break;
		case "addAdmin":
			addAdmin(request,response);
			break;
		case "namecheck":
			namecheck(request,response);
			break;
		}
		
		
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
	
	private void loginCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		//数据库中进行校验
			//sql语句
		String sql = "select * from admin where aname=? and apwd=?";
			//连接
		Connection conn = DBConnection.getInstance().getConn();
		
		try {
			//准备执行sql语句
			PreparedStatement psta = conn.prepareStatement(sql);
			//?赋值
			psta.setString(1,name);
			psta.setString(2,pass);
			//执行sql
			ResultSet rs = psta.executeQuery();
			//结果处理
			admin admin = null;
			if(rs.next()){
				int aid = rs.getInt(1);
				String aname = rs.getString(2);
				String apwd = rs.getString(3);
				String atel = rs.getString(4);
				admin = new admin(aid, aname, apwd, atel);
			}
			//关闭
			rs.close();
			psta.close();
			//返回结果到客户端
			if(null == admin){
				response.getWriter().append("null");
			}else{
				response.getWriter().append(JsonUtil.obj2String(admin));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示数据
	 */
	private void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("pageNumber");
		String size = request.getParameter("pageSize");
		String nameKey = request.getParameter("nameKey");
		ArrayList<admin> list = adminOper.getAll(Integer.parseInt(page), Integer.parseInt(size), nameKey);
		int total = adminOper.getTotal(nameKey);
		Map<String,Object> map = new HashMap<>();
		map.put("total", total);
		map.put("rows", list);
		response.getWriter().append(JsonUtil.obj2String(map));
	}

	/**
	 * 更新数据
	 */
	private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			String aid = request.getParameter("aid");
			String atel = request.getParameter("atel");

			adminOper.updateadmin(Integer.parseInt(aid),atel);
			response.getWriter().print("true");
	}
	
	/**
	 * 新增管理员
	 */
	private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取表单数据

		String name = request.getParameter("aname");
		String pass = request.getParameter("apwd");
		String atel = request.getParameter("atel");
	
		adminOper.addadmin(name, pass, atel);
		//返回结果到客户端
		response.getWriter().print("true");
			
	}
	/**
	 * 姓名检测
	 */
	private void namecheck(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
		String name = request.getParameter("aname");
		boolean isexist = adminOper.isexist(name);
		Map<String,Boolean> map = new HashMap<>();
		map.put("valid", !isexist);
		response.getWriter().append(JsonUtil.obj2String(map));
	}
	
}
