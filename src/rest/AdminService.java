package rest;

import java.util.ArrayList;
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

import coupons.management.beans.Company;
import coupons.management.beans.Customer;
import coupons.management.clients.AdminFacade;
import coupons.management.exceptions.CompanyAllredyExistException;
import coupons.management.exceptions.CompanyNotFoundException;
import coupons.management.exceptions.CouponsDBException;
import coupons.management.exceptions.CustomerAllredyExistException;
import coupons.management.exceptions.CustomerNotFoundException;
import coupons.server.mockup.CouponsMockup;

@Path("admin")
public class AdminService {

	@POST
	@Path("/company")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response addCompany(Company company, @Context HttpServletRequest request) {

		HttpSession session = request.getSession();
		AdminFacade adminfacade = CouponsMockup.getAdminFacasde(session.getId());

		Random rand = new Random();
		company.setId(rand.nextInt(100) + 1);

		try {
			adminfacade.createCompany(company);
		} catch (CompanyAllredyExistException e) {
			return Response.status(404).build(); // missing resource
		}

		return Response.status(201).build(); // created
	}

	@DELETE
	@Path("/company/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteCompany(@PathParam("id") int id, @Context HttpServletRequest request) {

		HttpSession session = request.getSession();
		AdminFacade adminfacade = CouponsMockup.getAdminFacasde(session.getId());

		Company company = new Company(id, "", "", "");
		try {
			adminfacade.removeCompany(company);
		} catch (CompanyNotFoundException e) {
			return Response.status(404).build(); // company not found
		} catch (CouponsDBException e) {
			return Response.status(500).build(); // internal error
		}

		return Response.status(204).build(); // deleted
	}

	@PUT
	@Path("/company/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response updateCompany(Company company, @Context HttpServletRequest request) {

		HttpSession session = request.getSession();
		AdminFacade adminfacade = CouponsMockup.getAdminFacasde(session.getId());

		try {
			adminfacade.updateCompany(company);
		} catch (CompanyNotFoundException e) {
			return Response.status(404).build(); // resource not found
		} catch (CouponsDBException e) {
			return Response.status(500).build(); // intrenal error
		}

		return Response.status(201).build(); // updated
	}

	@GET
	@Path("/company/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getCompany(@PathParam("id") int number, @Context HttpServletRequest request) {

		HttpSession session = request.getSession();
		AdminFacade adminfacade = CouponsMockup.getAdminFacasde(session.getId());
		Collection<Company> companies = new ArrayList<Company>();

		try {
			Company company = adminfacade.getCompany(number);
			companies.add(company);
		} catch (CouponsDBException e) {

			return Response.status(500).build(); // internal error
		} catch (CompanyNotFoundException e) {
			return Response.status(404).build(); // not found
		}
		String json = new Gson().toJson(companies);
		return Response.ok(json).build();

	}

	@GET
	@Path("/company")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllCompanies(@Context HttpServletRequest request) {

		HttpSession session = request.getSession();
		AdminFacade adminfacade = CouponsMockup.getAdminFacasde(session.getId());
		Collection<Company> companies = new ArrayList<Company>();

		try {
			companies = adminfacade.getAllCompanies();

		} catch (CouponsDBException e) {

			return Response.status(500).build(); // internal error
		}
		String json = new Gson().toJson(companies);
		return Response.ok(json).build();

	}

	@POST
	@Path("/customer")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createCustomer(Customer customer, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		AdminFacade adminfacade = CouponsMockup.getAdminFacasde(session.getId());

		Random rand = new Random();
		customer.setId(rand.nextInt(100) + 1);

		try {
			adminfacade.createCustomer(customer);
		} catch (CustomerAllredyExistException e) {
			return Response.status(500).build(); // internal error
		} catch (CouponsDBException e) {

		}
		return Response.status(201).build(); // created
	}

	@DELETE
	@Path("/customer/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteCustomer(@PathParam("id") int id, @Context HttpServletRequest request) {

		HttpSession session = request.getSession();
		AdminFacade adminfacade = CouponsMockup.getAdminFacasde(session.getId());

		Customer customer = new Customer(id, "", "");

		try {
			adminfacade.removeCustomer(customer);
		} catch (CouponsDBException e) {
			return Response.status(404).build(); // customer not found
		}

		return Response.status(200).build();

	}

	@PUT
	@Path("/customer/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateCustomer(Customer customer, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		AdminFacade adminfacade = CouponsMockup.getAdminFacasde(session.getId());

		try {
			adminfacade.updateCustomer(customer);
		} catch (CouponsDBException e) {
			return Response.status(500).build(); // internal error
		}

		return Response.status(201).build(); // created

	}

	@GET
	@Path("/customer/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getCustomer(@PathParam("id") int number, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		AdminFacade adminfacade = CouponsMockup.getAdminFacasde(session.getId());
		Customer customer = null;

		try {
			customer = adminfacade.getCustomer(number);
		} catch (CustomerNotFoundException e) {
			return Response.status(404).build(); // customer not found
		} catch (CouponsDBException e) {
			return Response.status(500).build(); // internal error
		}

		String json = new Gson().toJson(customer);
		return Response.ok(json).build();

	}

	@GET
	@Path("/customer")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllCustomers(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		AdminFacade adminfacade = CouponsMockup.getAdminFacasde(session.getId());
		Collection<Customer> customers = null;
		try {
			customers = adminfacade.getAllCompanyCustomers();
		} catch (CouponsDBException e) {
			return Response.status(500).build(); // internal error
		}

		String json = new Gson().toJson(customers);
		return Response.ok(json).build();

	}

}
