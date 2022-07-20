package warehouse.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns="/*")//Apply to every page
public class CORSFilter implements Filter {

	//For some reason, not overriding init() and destroy() will cause every page to give a 404.
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("CORS Filter running");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("Access-Control-Allow-Origin", "*"); //Allows all domains.
		res.addHeader("Access-Control-Allow-Methods", "*"); //Allows all HTTP methods.
		res.addHeader("Access-Control-Allows-Credentials", "true");
		res.addHeader("Access-Control-Allow-Headers", "*"); //Allows all headers.
		HttpServletRequest req = (HttpServletRequest) request;
		if (req.getMethod().equals("OPTIONS")) {
			res.setStatus(202);//202 = Accepted
		}
		chain.doFilter(req, res);
		
	}

	@Override
	public void destroy() {
		
	}
	
}
