package Server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class CORSFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {
	    
	    HttpServletResponse httpResponse = (HttpServletResponse) response;
	    HttpServletRequest httpRequest = (HttpServletRequest) request;

	    // Log the request method and URL to confirm the filter is triggered
	    System.out.println("Request Method: " + httpRequest.getMethod() + ", URL: " + httpRequest.getRequestURI());

	    // Allow cross-origin requests from the React frontend (localhost:3000)
	    httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
	    httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
	    httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

	    // Handle OPTIONS preflight requests
	    if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
	        httpResponse.setStatus(HttpServletResponse.SC_OK);
	        return;
	    }

	    // Continue the filter chain
	    chain.doFilter(request, response);
	}

}