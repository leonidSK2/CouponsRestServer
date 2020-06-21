package rest;

import javax.servlet.http.HttpSession;

import coupons.server.mockup.CouponsMockup;

public class LoginFilter {

	public static boolean verifySession(HttpSession session) {
		return CouponsMockup.isLoggedIn(session.getId());
		
	}

}
