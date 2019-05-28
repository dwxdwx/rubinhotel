package rubin.servlet;

import rubin.bean.room;
import rubin.db.impl.roomoper;
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

@WebServlet("/roomservlet")
public class roomservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private roomoper Roomoper;
	
    public roomservlet() {
    	Roomoper = new roomoper();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String method = request.getParameter("method");
		switch(method){
		case "updateRoom":
			updateRoom(request, response);
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
		ArrayList<room> list = Roomoper.getAll(Integer.parseInt(page),Integer.parseInt(size),nameKey);
		int total = Roomoper.getTotal(nameKey);
		
		Map<String,Object> map = new HashMap<>();
		map.put("total", total);
		map.put("rows", list);
		response.getWriter().append(JsonUtil.obj2String(map));
	}
	/**
	 * 更新数据
	 */
	private void updateRoom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			String rid = request.getParameter("rid");
			String rstste = request.getParameter("rstate");

			Roomoper.updateroom(Integer.parseInt(rid),rstste);
			response.getWriter().print("true");
		}

}
