$( document ).ready(function() {
    $('.sidenav').sidenav();
    
   


	

});

function check(id) {
	
	var urlPath = window.location.pathname;
	const myArr = urlPath.split("/");
	
	fetch('http://localhost:8080/mamasdish/checkEmail/' + id)
					.then(data => data.json())
					.then(function(data) {
						$("#mailCount").text(data);
					});
				
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
	var mailCnt = $("#mailCount").text();

	$("#navCnt").text(mailCnt);


	var recipe = $("#recipe" + id).text();
	console.log(myArr);
	$("#mssgSub").text(subject);
	$("#mssgBody").text(body);
	$('#mssgSndr').text(sender);
	$('#mssgDate').text(date);
	$('#mssgRec').text("To:" + receiver)
	$('#recipeLink').text("View Recipe");
	
	if(receiver == "Mama's Dish Admin"){
			$("#recipeLink").attr("href", "http://localhost:8080/admin/authRecipe/" + recipe);

	}else {
			$("#recipeLink").attr("href", "http://localhost:8080/chefs/viewRecipe/" + recipe);

	}
	
	if(myArr[2] == "deleted" || myArr[2] == "deleted#"){
		
			$("#deleteBtn").css("display", "none");

	} else {
		$("#deleteBtn").css("display", "block");


	}
	
	$( "#deleteBtn" ).click(function() {
  fetch('http://localhost:8080/mamasdish/deleteEmail/' + id)
				.then(data => data.json())
				.then(function(data) {
					$("#deletedNum").text(data);
					});
					
					$('#mssgBox' + id).remove();
					
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

