package rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import coupons.management.exceptions.CouponsDBException;
import coupons.management.exceptions.UserOrPasswNotCorrectException;
import coupons.server.mockup.CouponsMockup;

@Path("login")
public class LoginService {

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response login(User user, @Context HttpServletRequest request) {

		HttpSession session = request.getSession();

		try {
			CouponsMockup.login(user.username, user.userpassword, user.usertype, session.getId());
		} catch (UserOrPasswNotCorrectException e) {
			return Response.status(401).build(); // Unauthorized
		} catch (CouponsDBException e) {
			return Response.status(500).build();
		}

		return Response.status(201).build(); // created
	}

}
