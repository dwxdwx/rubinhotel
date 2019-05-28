package rubin.servlet;

import rubin.bean.order;
import rubin.db.impl.orderoper;
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


@WebServlet("/orderservlet")
public class orderservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private orderoper orderOper;
	public orderservlet() {
		orderOper = new orderoper();
	}
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");//null和""不一样
		switch(method) {
		case "updateOrder":
			updateOrder(request,response);
			break;
		case "delOne":
			delOne(request,response);
			break;
		case "delMore":
			delMore(request,response);
			break;
		case "addOrder":
			addOrder(request,response);
			break;
		default:
			getAll(request,response);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/*
	 * 显示数据
	 */
	private void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String page = request.getParameter("pageNumber");
		String size = request.getParameter("pageSize");
		String nameKey = request.getParameter("nameKey");
		ArrayList<order> list = orderOper.getAll(Integer.parseInt(page), Integer.parseInt(size),nameKey);
		//System.out.println(JsonUtil.obj2String(list));
		//当bootstrap-table分页时，服务器端返回到页面的json数据需要包含2个属性：total与rows
		int total = orderOper.getTotal(nameKey);
		Map<String,Object> map = new HashMap<>();
		map.put("total", total);
		map.put("rows", list);
		response.getWriter().append(JsonUtil.obj2String(map));
	}
	
	/*
	 * 更新数据
	 */
	private void updateOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("oid");
		String ostate = request.getParameter("ostate");
		String omoney = request.getParameter("omoney");
		orderOper.updateorder(Integer.parseInt(omoney),ostate,Integer.parseInt(id));
		
		response.getWriter().print("true");
	}

	/*
	 * 删除一条数据
	 */
	private void delOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("delId");
		orderOper.delById(Integer.parseInt(id));
		
		response.getWriter().print("true");
	}
	/*
	 * 删除多条数据
	 */
	private void delMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ids = request.getParameter("delIds");
		orderOper.delByIds(ids);
		
		response.getWriter().print("true");
	}
	/*
	 * 添加数据
	 */
	private void addOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("uid");
		String rid = request.getParameter("rid");
		String omoney = request.getParameter("omoney");
		String ostate = request.getParameter("ostate");
		String aid = request.getParameter("aid");
		orderOper.addorder(Integer.parseInt(uid),Integer.parseInt(rid),Integer.parseInt(omoney),ostate,Integer.parseInt(aid));
		
		//返回结果到客户端
		response.getWriter().append("true");
		//response.getWriter().print("true");
	}
}
