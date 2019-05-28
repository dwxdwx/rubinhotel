package rubin.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


@WebFilter( "/*")
public class EncodingFilter implements Filter {

	private String encoding ;
    public EncodingFilter() {
        
    }

    /**
     * 销毁资源
     */
	public void destroy() {
	}

	/**
	 * 对要过滤的请求进行过滤处理，会被反复调用
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		// pass the request along the filter chain
		chain.doFilter(request, response);   // 向下传递过滤请求，并最终传递到所请求的页面
	}
	/**
	 * 获取initParams中的参数
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getInitParameter("encode") ;
		if(null == encoding || encoding.trim().length() == 0) {
			encoding = "utf-8" ;
		}
	}

}
