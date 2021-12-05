//Functionality to view, delete and update emails
$( document ).ready(function() {
    $('.sidenav').sidenav();
    	
});

function check(id) {
	
	var urlPath = window.location.pathname;
	const myArr = urlPath.split("/");
	
	//Once user selects an email, it is not longer considered new. API call will update email as read
	fetch('http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/mamasdish/checkEmail/' + id)
					.then(data => data.json())
					.then(function(data) {
						$("#mailCount").text(data);
						
					});
	//Adding email info to box			
	$("#emailSender").css("font-weight", "regular");
	$('#emailSender' + id).removeClass("emailSender");
	$('#emailSender' + id).addClass("emailSender2");
	var subject = $('#emailSubject' + id).text();
	$('#emailSubject' + id).removeClass("emailSubject");
	$('#emailSubject' + id).addClass("emailSubject2");
	var body = $('#emailBody' + id).text();
	$('#emailBody' + id).css("font-weight", "regular");
	$('#emailBody' + id).removeClass("emailBody");
	$('#emailBody' + id).addClass("emailBody2");
	var sender = $('#emailSender' + id).text();
	$('#emailSender' + id).css("font-weight", "regular");
	var receiver = $('#emailReceiver' + id).text();
	var date =$('#emailTime' + id).text();
	$('#emailTime' + id).css("font-weight", "regular")
	$('#emailTime' + id).css("color", "black");
	$("#newBadge" + id).css("display", "none");

	var recipe = $("#recipe" + id).text();
	$("#mssgSub").text(subject);
	$("#mssgBody").text(body);
	$('#mssgSndr').text(sender);
	$('#mssgDate').text(date);
	$('#mssgRec').text("To:" + receiver)
	$('#recipeLink').text("View Recipe");
	
	//Redirects user based on roles
	if(receiver == "Mama's Dish Admin"){
			$("#recipeLink").attr("href", "http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/admin/authRecipe/" + recipe);

	}else if(receiver == "Mama's Dish Authenticators") {
			$("#recipeLink").attr("href", "http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/users/viewRecipe/" + recipe);

	}else {
			$("#recipeLink").attr("href", "http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/chefs/viewRecipe/" + recipe);

	}
	
	if(myArr[2] == "deleted" || myArr[2] == "deleted#"){
		
			$("#deleteBtn").css("display", "none");

	} else {
		$("#deleteBtn").css("display", "block");


	}
	
	//Delete function for email
	$( "#deleteBtn" ).click(function() {
		//API call takes email id and removes from ider inbox and adds to deleted emails
  fetch('http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/mamasdish/deleteEmail/' + id)
				.then(data => data.json())
				.then(function(data) {
					$("#deletedNum").text(data);
					});
					
					$('#mssgBox' + id).remove();
					
				//Clearing email box
				$("#mssgSub").text("");
				$("#mssgBody").text("");
				$('#mssgSndr').text("");
				$('#mssgDate').text("");
				$('#mssgRec').text("");
				$('#recipeLink').text("");
				$("#recipeLink").removeAttr("href");
				$("#deleteBtn").css("display", "none");

});


}

