$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

$(document).on("click", "#btnSave", function(event) {

	// Clear status msges-------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateItemForm();
	
	// If not valid
	if (status!=true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	$.ajax({
		url : "PaymentAPI",
		type : "POST",
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onPaymentSaveComplete(response.responseText, status);
		}
	});

});
$(document).on("click", "#btnUpdate", function(event) {

	// Clear status msges-------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateItemForm();
	
	// If not valid
	if (status!=true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	$.ajax({
		url : "PaymentAPI",
		type : "PUT",
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onPaymentSaveComplete(response.responseText, status);
		}
	});

});

function onPaymentSaveComplete(response, status) {

	if (status == "success") {

		$("#alertSuccess").text("Successfully saved.");
		$("#alertSuccess").show();
		$( "#divpaymentgrid" ).load(window.location.href + " #divpaymentgrid" );

	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#PaymentIDSave").val("");
	$("#formPayment")[0].reset();
}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#PaymentIDSave").val(
					$(this).closest("tr").find('#hidPaymentIDUpdate').val());
			$("#userid").val($(this).closest("tr").find('td:eq(0)').text());
			$("#appointmentid").val(
					$(this).closest("tr").find('td:eq(1)').text());
			document.getElementById("rdoPaycash").checked=($(this).closest("tr").find('td:eq(2)').text()=='Cash');
			document.getElementById("rdoPaycard").checked=($(this).closest("tr").find('td:eq(2)').text()=='Card');
			$("#purpose").val($(this).closest("tr").find('td:eq(4)').text());
			$("#status").val($(this).closest("tr").find('td:eq(6)').text());
			$("#amount").val($(this).closest("tr").find('td:eq(5)').text());
		});

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PaymentAPI",
		type : "DELETE",
		data : "PaymentID=" + $(this).data("itemid"),
		dataType : "text",
		complete : function(response, status) {
			onPaymentDeleteComplete(response.responseText, status);
		}
	});
});

function onPaymentDeleteComplete(response, status) {
	console.log(status)
	if (status == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$( "#divpaymentgrid" ).load(window.location.href + " #divpaymentgrid" );

	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// client model---------------
function validateItemForm() {
	// User Id
	if ($("#userid").val().trim() == "") {
		return "Insert user ID.";
	}

	// appointment Id
	if ($("#appointmentid").val().trim() == "") {
		return "Insert appointment ID.";
	}

	// Payment Method
	if ($('input[name="rdoPay"]:checked').length === 0) {
		return "Select payment method.";
	}

	// purpose
	if ($("#purpose").val().trim() == "") {
		return "Insert purpose.";
	}

	// status
	if ($("#status").val() == "0") {
		return "Select payment status.";
	}

	// amount
	if ($("#amount").val().trim() == "") {
		return "Insert amount.";
	}

	// is numerical value
	var tmpamount = $("#amount").val().trim();
	if (!$.isNumeric(tmpamount)) {
		return "Insert a numerical value for Amount.";
	}

	// convert to decimal price
	$("#amount").val(parseFloat(tmpamount).toFixed(2));

	return true;
}