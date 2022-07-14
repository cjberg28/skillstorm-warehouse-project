package warehouse.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns="/*")//Apply to every page
public class CORSFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("Access-Control-Allow-Origin", "*"); // Allows any domain to make a request
		res.addHeader("Access-Control-Allow-Methods", "*"); // Allows all HTTP methods
		res.addHeader("Access-Control-Allows-Credentials", "true");
		res.addHeader("Access-Control-Allow-Headers", "*"); // Allows all types of headers
		HttpServletRequest req = (HttpServletRequest) request;
		if (req.getMethod().equals("OPTIONS")) {
			res.setStatus(202);
		}
		chain.doFilter(req, res);
		
	}

}
