package rest;

import java.util.Collection;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import coupons.management.beans.Coupon;
import coupons.management.beans.CouponType;
import coupons.management.clients.CompanyFacade;
import coupons.management.exceptions.CouponAllredyExistException;
import coupons.management.exceptions.CouponNotFoundException;
import coupons.management.exceptions.CouponsDBException;
import coupons.server.mockup.CouponsMockup;

@Path("company")
public class CompanyService {

	
	@POST
	@Path("/coupon")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response createCoupon(Coupon coupon, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		CompanyFacade companyfacade = CouponsMockup.getCompanyFacasde(session.getId());
		
		Random rand = new Random();
		coupon.setid(rand.nextInt(100)+1);
		try {
				companyfacade.createCoupon(coupon);
		} catch (CouponAllredyExistException e) {
			return Response.status(404).build(); // missing resource
		} catch (CouponsDBException e) {
			return Response.status(500).build(); // internal error
		}
		return Response.status(201).build(); // created
	
	}
	
	@DELETE
	@Path("/coupon/{id}")
	public Response deleteCoupon(@PathParam("id") int id, @Context HttpServletRequest request) {

		HttpSession session = request.getSession();
		CompanyFacade companyfacade = CouponsMockup.getCompanyFacasde(session.getId());

		Coupon coupon = new Coupon(id, null, null, null, id, id, null, null, null);

		try {
			companyfacade.removeCoupon(coupon);
		} catch (CouponsDBException e) {
			return Response.status(500).build(); // internal error
		}
		return Response.status(202).build(); // Accepted

	}

	@PUT
	@Path("/coupon/{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response updateCoupon( Coupon coupon, @Context HttpServletRequest request) {

		HttpSession session = request.getSession();
		CompanyFacade companyfacade = CouponsMockup.getCompanyFacasde(session.getId());


		try {
			companyfacade.updateCoupon(coupon);
		} catch (CouponsDBException e) {
			return Response.status(500).build(); // internal error
		}

		return Response.status(202).build(); // Accepted

	}
	

	@GET
	@Path("/coupon/{id}")
	public Response getCoupon(@PathParam("id") int id, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		CompanyFacade companyfacade= CouponsMockup.getCompanyFacasde(session.getId());
		
		Coupon coupon  = null;
		
		
	
			try {
				coupon = companyfacade.getCoupon(id);
			} catch (CouponNotFoundException e) {
				return Response.status(404).build(); // resource not found
			} catch (CouponsDBException e) {
				return Response.status(500).build(); // internal error
			}
		
		
		String json = new Gson().toJson(coupon);
		return Response.ok(json).build();
	
	}
	
	@GET
	@Path("/coupons")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllCoupons( @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		CompanyFacade companyfacade= CouponsMockup.getCompanyFacasde(session.getId());
		Collection<Coupon> coupons = null;

		try {
			coupons = companyfacade.getAllCoupons();
		} catch (CouponsDBException e) {

			return Response.status(500).build(); // internal error
		}
		String json = new Gson().toJson(coupons);
		return Response.ok(json).build();

	}
	
	@GET
	@Path("/coupon/{type}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getCouponsByType(@PathParam("type")String type, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		CompanyFacade companyfacade= CouponsMockup.getCompanyFacasde(session.getId());

		CouponType  coupontype  = CouponType.valueOf(type);
		Collection<Coupon> coupons = null;
		
		try {
			 coupons = companyfacade.getCouponByType(coupontype);
		} catch (CouponsDBException e) {
			return Response.status(500).build(); // internal error
		}
		
		
		String json = new Gson().toJson(coupons);
		return Response.ok(json).build();
	
	
}


}
