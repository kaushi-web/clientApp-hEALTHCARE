<%@page import="org.apache.jasper.tagplugins.jstl.core.Import"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="com.*"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Details</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.5.0.min.js" type="text/javascript"></script>
<script src="Components/payment.js" type="text/javascript"></script>
</head>
<body>
	<div class="container">

		<div class="row">
			<div class="col-6">

				<h1 class="m-3">Payment details</h1>

				<form id="formPayment" name="formPayment" method="post"
					action="Payment.jsp">

					<!-- user id -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblName">USER ID: </span>
						</div>
						<input type="text" id="userid" name="userid"
							class="form-control form-control-sm">
					</div>

					<!-- appointment id -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblName">APPOINTMENT
								ID: </span>
						</div>
						<input type="text" id="appointmentid" name="appointmentid"
							class="form-control form-control-sm">
					</div>

					<!-- payment method -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblName">PaymentMethod:
							</span>
						</div>
						&nbsp;&nbsp;Cash <input type="radio" id="rdoPaycash" name="rdoPay"
							value="Cash"> &nbsp;&nbsp;Card <input type="radio"
							id="rdoPaycard" name="rdoPay" value="Card">
					</div>

					<!-- purpose -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblName">PURPOSE: </span>
						</div>
						<input type="text" id="purpose" name="purpose"
							class="form-control form-control-sm">
					</div>

					<!-- status -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblName">STATUS: </span>
						</div>
						<select id="status" name="status"
							class="form-control form-control-sm">
							<option value="0">--Select Status--</option>
							<option value="pending">pending</option>
							<option value="paid">paid</option>
							<option value="unpaid">unpaid</option>

						</select>
					</div>


					<!-- amount -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblName">AMOUNT: </span>
						</div>
						<input type="text" id="amount" name="amount"
							class="form-control form-control-sm">
					</div>

					<input type="button" id="btnSave" value="Save"
						class="btn btn-primary">&nbsp;&nbsp;<input type="button" id="btnUpdate" value="Update"
						class="btn btn-primary"> <br/><br/><input type="hidden"
						id="PaymentIDSave" name="PaymentIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success">
				</div>
				<div id="alertError" class="alert alert-danger"></div>
			</div>
			<br>
			<div id="divpaymentgrid">
			<%
				paymentService paymentobj = new paymentService();
			out.print(paymentobj.readAllPymentRecords());
			%>
			</div>
		</div>
		<br>


	</div>
</body>
</html>