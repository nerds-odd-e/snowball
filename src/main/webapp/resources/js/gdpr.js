

function sendConsentMail() {
    $.ajax({
	    type: 'POST',
	    url: 'gdpr',
	    success: function(data1) { $("#resultMessage").text("Successfully sent consent mails for all the contacts");},
	    error: function(data2) { $("#resultMessage").text("Error Sending consent mail");}
	});
}
