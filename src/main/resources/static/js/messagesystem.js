$( document ).ready(function() {
    console.log( "ready!" );
});

function check(id) {
console.log(id);
fetch('http://localhost:8080/mamasdish/checkEmail/' + id)
				.then(data => data.json())
				.then(function(data) {
					console.log(data);
					
					$("#mailCount").text(data);
					
					
				});
				
	$("#emailSender").css("font-weight", "regular");


}
