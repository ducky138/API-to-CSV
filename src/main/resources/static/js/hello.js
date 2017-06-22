function myFunction() {
    alert("Hello! I am an alert box!");
}

function errorCheck(){
	var errorMessage = document.getElementsByTagName("div")[1].getAttribute("msg")
	console.log(errorMessage)
	if (errorMessage != ""){

		document.getElementById("errorMessage").innerHTML = "The URL input is invalid";
		
		setTimeout(function() {
		    $('#errorMessage').fadeOut('fast');
		}, 1500); // <-- time in milliseconds
		
	} else {
	    document.getElementById("errorMessage").innerHTML = "";
	}
}