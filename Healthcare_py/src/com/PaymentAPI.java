package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/PaymentAPI")
public class PaymentAPI extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	paymentService paymentobj = new paymentService();

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map paras = getParasMap(req); 
		System.out.println(paras.get("PaymentID").toString());
		String output=paymentobj.deletecardDetails(paras.get("PaymentID").toString());
		resp.getWriter().write(output);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String output = paymentobj.storepayment(req.getParameter("rdoPay"),req.getParameter("appointmentid"),req.getParameter("userid"),req.getParameter("purpose"),req.getParameter("amount"),req.getParameter("status"));
		resp.getWriter().write(output);

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map paras = getParasMap(req);
		Payment payment = new Payment();
		payment.setPaymentid(Integer.parseInt(paras.get("PaymentIDSave").toString()));
		payment.setPaymentmethod(paras.get("rdoPay").toString());
		payment.setPurpose(paras.get("purpose").toString());
		payment.setStatues(paras.get("status").toString());
		payment.setAmount(Float.parseFloat(paras.get("amount").toString()));

		String output = paymentobj.updatePaymentdetails(payment);
		resp.getWriter().write(output);

	}

	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();

			String[] params = queryString.split("&");
			for (String param : params) {
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		} catch (Exception e) {
		}
		return map;
	}

}
