package coupons.server.mockup;

import java.util.HashMap;

import coupons.management.app.CouponSystem;
import coupons.management.clients.AdminFacade;
import coupons.management.clients.ClientType;
import coupons.management.clients.CompanyFacade;
import coupons.management.clients.CouponClientFacade;
import coupons.management.clients.CustomerFacade;
import coupons.management.exceptions.CouponsDBException;
import coupons.management.exceptions.UserOrPasswNotCorrectException;

public class CouponsMockup {

	private static HashMap<String, CouponClientFacade> sessionToFacadeMap = new HashMap<String, CouponClientFacade>();

	public CouponsMockup() {

	}

	/**
	 * login to the system
	 * 
	 * @param name
	 * @param password
	 * @param type
	 * @param sessionid
	 * @throws UserOrPasswNotCorrectException
	 * @throws CouponsDBException
	 */
	public static void login(String name, String password, String type, String sessionid)
			throws UserOrPasswNotCorrectException, CouponsDBException {
		CouponSystem cs = CouponSystem.getInstance();

		ClientType clienttype = ClientType.valueOf(type);

		CouponClientFacade facade = cs.Login(name, password, clienttype);
		sessionToFacadeMap.put(sessionid, facade);

	}

	/**
	 * is LoggedIn 
	 * @param id
	 * @return
	 */
	public static boolean isLoggedIn(String id) {

		return sessionToFacadeMap.containsKey(id);
	}

	/**
	 * get Admin Facasde 
	 * @param id
	 * @return
	 */
	public static AdminFacade getAdminFacasde(String id) {

		AdminFacade adminFacade = null;

		if (sessionToFacadeMap.containsKey(id))
			adminFacade = (AdminFacade) sessionToFacadeMap.get(id);
		else {
			adminFacade = new AdminFacade();
			sessionToFacadeMap.put(id, adminFacade);
		}

		return adminFacade;
	}

	/**
	 * get Company Facasde
	 * @param id
	 * @return
	 */
	public static CompanyFacade getCompanyFacasde(String id) {

		CompanyFacade companyfacade = null;

		if (sessionToFacadeMap.containsKey(id))
			companyfacade = (CompanyFacade) sessionToFacadeMap.get(id);
		else {
			companyfacade = new CompanyFacade();
			companyfacade.setCompanyId(444); // for debug
			sessionToFacadeMap.put(id, companyfacade);
		}

		return companyfacade;
	}

	/**
	 * get Customer Facasde
	 * @param id
	 * @return
	 */
	public static CustomerFacade getCustomerFacasde(String id) {

		CustomerFacade customerFacade = null;

		if (sessionToFacadeMap.containsKey(id))
			customerFacade = (CustomerFacade) sessionToFacadeMap.get(id);
		else {
			customerFacade = new CustomerFacade();
			customerFacade.setCustomerid(87654321); // for debug
			sessionToFacadeMap.put(id, customerFacade);
		}

		return customerFacade;
	}

}