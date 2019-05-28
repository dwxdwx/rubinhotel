package rubin.servlet;

import rubin.bean.user;
import rubin.db.impl.useroper;
import rubin.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/userservlet")
public class userservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private useroper Useroper;
    	
    public userservlet() {
    	Useroper = new useroper();
    	
        }



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		switch(method){
			case "addUser":
				addUser(request, response);
				break;
			case "delOne":
				delOne(request, response);
				break;
			case "delMore":
				delMore(request, response);
				break;
			case "updateUser":
				updateUser(request, response);
				break;
		default:
			getAll(request,response);
			break;
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
	/**
	 * 显示数据
	 */
	private void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("pageNumber");
		String size = request.getParameter("pageSize");
		String nameKey = request.getParameter("nameKey");
		ArrayList<user> list = Useroper.getAll(Integer.parseInt(page),Integer.parseInt(size),nameKey);
		int total = Useroper.getTotal(nameKey);
		Map<String,Object> map = new HashMap<>();
		map.put("total", total);
		map.put("rows", list);
		response.getWriter().append(JsonUtil.obj2String(map));
	}
	
	/**
	 * 新增商品
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取表单数据
		String uname = request.getParameter("uname");
		String uidnum = request.getParameter("uidnum");
		String ucheck_out = request.getParameter("ucheck_out");
		String utel = request.getParameter("utel");
		
			
		Useroper.adduser(uname, uidnum, ucheck_out, utel);;
		//返回结果到客户端
		response.getWriter().print("true");
			
	}

	/**
	 * 更新商品
	 */
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uid = request.getParameter("editId");
		String uidnum = request.getParameter("uidnum");
		String ucheck_out = request.getParameter("ucheck_out");
		String utel = request.getParameter("utel");
		Useroper.updateuser(Integer.parseInt(uid), uidnum, ucheck_out, utel);
		response.getWriter().print("true");
	}
	/**
	 * 删除一条
	 */
	private void delOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("delId");
		Useroper.delById(Integer.parseInt(uid));
		response.getWriter().print("true");
	}
	
	/**
	 * 删除多条
	 */
	private void delMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ids = request.getParameter("delIds");
		Useroper.delByIds(ids);
		response.getWriter().print("true");;
	}


}
