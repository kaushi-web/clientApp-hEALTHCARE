package com;

import java.sql.*;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.text.DateFormatter;
import org.apache.el.parser.ParseException;


public class paymentService {
	DBconnection db = DBconnection.getDBconnection();
	Connection connection;
	

	public String storepayment(String Payment_method, String appointment_id, String user_id,String purpose, String amount, String status) {

		String output = "";
		String query1 = " INSERT INTO `payment`(`Payment_method`,`appointment_id`, `user_id`,`purpose`, `amount`, `status`) VALUES (?,?,?,?,?,?)";
		PreparedStatement preparedStmt1;

		try {
			preparedStmt1 = db.connection.prepareStatement(query1);
			preparedStmt1.setString(1,Payment_method);
			preparedStmt1.setInt(2, Integer.parseInt(appointment_id));
			preparedStmt1.setInt(3, Integer.parseInt(user_id));
			preparedStmt1.setString(4, purpose);
			preparedStmt1.setFloat(5, Float.parseFloat(amount));
			preparedStmt1.setString(6, status);

			preparedStmt1.execute();

			String newItems = readAllPymentRecords();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (SQLException e) {
			output = "{\"status\":\"error\", \"data\":         \"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readAllPymentRecords() {

		String output = "";
		// Prepare the html table to be displayed
		output = "<table border=\"1\"><th>Appointment ID</th><th>User ID</th><th>Paymment Method</th><th>Payment time</th><th>Purpose</th><th>Amount</th><th>Statues</th><th>Update</th><th>Delete</th></tr>";
		String query = "select * from `payment`";
		try {

			Statement stmt = db.connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String paymentid = Integer.toString(rs.getInt("payment_id"));
				String appointmentid = Integer.toString(rs.getInt("appointment_id"));
				String userid = Integer.toString(rs.getInt("user_id"));
				String paymentMethod = rs.getString("Payment_method");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String paymentTime = dateFormat.format(rs.getDate("paid_time"));
				String purpose = rs.getString("purpose");
				String Amount = Float.toString(rs.getFloat("amount"));
				String statues = rs.getString("status");

				// Add into the html table
				output += "<tr><td><input id=\"hidPaymentIDUpdate\" name=\"hidPaymentIDUpdate\"     type=\"hidden\" value=\""
						+ paymentid + "\">" + appointmentid + "</td>";
				output += "<td>" + userid + "</td>";
				output += "<td>" + paymentMethod + "</td>";
				output += "<td>" + paymentTime + "</td>";
				output += "<td>" + purpose + "</td>";
				output += "<td>" + Amount + "</td>";
				output += "<td>" + statues + "</td>";

				// buttons
				output += "<td><input name='btnUpdate'          type='button' value='Update'         class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove'         type='button' value='Remove'         class='btnRemove btn btn-danger'        data-itemid='"
						+ paymentid + "'>" + "</td></tr>";
			}

			// db.connection.close();
			// Complete the html table
			output += "</table>";
		} catch (SQLException e) {
			output = "Error while reading the payments.";
			System.err.println(e.getMessage());
		}
		return output;

	}

	public String updatePaymentdetails(Payment payment) {
		String output = "";
		String query = "UPDATE `payment` SET `Payment_method`=?,`purpose`=?,`amount`=?,`status`=? WHERE `payment_id`=?";
		PreparedStatement preparedStmt;

		try {

			java.util.Date today = new java.util.Date();

			preparedStmt = db.connection.prepareStatement(query);
			// preparedStmt.setInt(1,payment.getUserid());
			preparedStmt.setString(1, payment.getPaymentmethod());
			preparedStmt.setString(2, payment.getPurpose());
			preparedStmt.setFloat(3, payment.getAmount());
			preparedStmt.setString(4, payment.getStatues());
			preparedStmt.setInt(5, payment.getPaymentid());
			// execute the statement
			preparedStmt.execute();
			String newItems = readAllPymentRecords();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (SQLException e) {
			output = "{\"status\":\"error\", \"data\":         \"Error while updating the payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletecardDetails(String payment_id) {
		String output;

		String query2 = "DELETE FROM `payment` WHERE `payment_id`=?";

		PreparedStatement preparedStmt2;
		try {
			System.out.print("===============" + payment_id + "===============");

			preparedStmt2 = db.connection.prepareStatement(query2);
			preparedStmt2.setInt(1, Integer.parseInt(payment_id));
			preparedStmt2.execute();

			String newItems = readAllPymentRecords();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":         \"Error while deleting the Payment.\"}"; 
			System.err.println(e.getMessage());
		}

		return output;
	}

}
