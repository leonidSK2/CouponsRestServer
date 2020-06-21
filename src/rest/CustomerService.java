package rest;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import coupons.management.beans.Coupon;
import coupons.management.beans.CouponType;
import coupons.management.clients.CustomerFacade;
import coupons.management.exceptions.CouponExpiredException;
import coupons.management.exceptions.CouponNotFoundException;
import coupons.management.exceptions.CouponsDBException;
import coupons.server.mockup.CouponsMockup;

@Path("customer")
public class CustomerService {

	@POST
	@Path("/purchase/{id}")
	public Response purchaseCoupon(@PathParam("id") int id, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();

		CustomerFacade customerfacade = CouponsMockup.getCustomerFacasde(session.getId());

		try {

			Coupon coupon = new Coupon(id, null, null, null, id, id, null, null, null);
			customerfacade.purchaseCoupon(coupon);
		} catch (CouponsDBException e) {
			return Response.status(500).build(); // internal error
		} catch (CouponNotFoundException e) {
			return Response.status(404).build(); // not found
		} catch (CouponExpiredException e) {
			return Response.status(409).build(); // coupon expired
		}

		return Response.status(202).build(); // accepted

	}

	@GET
	@Path("/purchased")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllPurchasedCoupons(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();

		CustomerFacade customerfacade = CouponsMockup.getCustomerFacasde(session.getId());

		Collection<Coupon> coupons1 = null;

		try {
			coupons1 = customerfacade.getAllPurchase();
		} catch (CouponsDBException e) {
			return Response.status(500).build(); // internal error
		}

		String json = new Gson().toJson(coupons1);
		return Response.ok(json).build();

	}

	@GET
	@Path("/purchase/{type}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllPorchasedCouponByType(@PathParam("type") String type, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		CustomerFacade customerfacade = CouponsMockup.getCustomerFacasde(session.getId());
		CouponType coupontype = CouponType.valueOf(type);

		Collection<Coupon> coupons = null;
		try {
			coupons = customerfacade.getAllPurchasedCouponByType(coupontype);
		} catch (CouponsDBException e) {
			return Response.status(500).build(); // internal error
		}

		String json = new Gson().toJson(coupons);
		return Response.ok(json).build();
	}

	@GET
	@Path("/purchase/price/{price}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllPorchasedCouponByPrice(@PathParam("price") int price, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		CustomerFacade customerfacade = CouponsMockup.getCustomerFacasde(session.getId());
		Collection<Coupon> coupons = null;
		try {
			coupons = customerfacade.getAllPurchasedCouponByPrice(price);
		} catch (CouponsDBException e) {
			return Response.status(500).build(); // internal error
		}
		String json = new Gson().toJson(coupons);
		return Response.ok(json).build();

	}

}
