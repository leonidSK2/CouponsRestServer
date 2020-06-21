package rest;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import coupons.server.mockup.CouponsMockup;

public class SessionLogInFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(true);

		boolean isLoggedIn = (session != null && CouponsMockup.isLoggedIn(session.getId()));

		String loginURI = httpRequest.getContextPath() + "/coupons/login";

		boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);

		boolean IfSessionNotexpired = verifySession(session);
			
		if (isLoggedIn && isLoginRequest &&  IfSessionNotexpired ) {

			 chain.doFilter(request, response);
		} else {
			//RequestDispatcher dispatcher = request.getRequestDispatcher(loginURI);
			//dispatcher.forward(request, response);
			 chain.doFilter(request, response);
		}

	}
	
    public static boolean verifySession(HttpSession session) {
		if (session == null)
			return false;
		Date createdate = new Date( session.getCreationTime());
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(createdate);
		cal.add(Calendar.MINUTE, 10);
		Date endtime = cal.getTime();
	    if (now.after(endtime)) {
	    	return false ;
	    }else {
	    	return true;
	    }
	}

}
