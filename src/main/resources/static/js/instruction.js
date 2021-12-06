
//creates new Id that doesn't matche the other divs's ids'
function getNewId() {


	var newDivId = true;

	var divID = Math.floor(Math.random() * 100);


	while (checkId(divID) == false) {

		divID = Math.floor(Math.random() * 100);
	}

	return divID;

}


//checks if the id is maches any of the div's id that starts with instructionBox
function checkId(id) {

	for (let instructDivs of $('[id^="instructionBox"]')) {
		var num = (instructDivs.id).split("instructionBox");
		var num = num[1];


		if (num == id) {
			return false;

		} else {

			return true;
		}

	}

}

//extracts the instructions from divs start with instructionValue and call API that responsible for adding the instructions
function saveInstruction() {

	var recipeId = $("#recipeId").html();

	var instructions = $('[id^="instructionValue"]');
	var instructionCheck = true;

	for (let instruct of instructions) {

		if (instruct.value == "") {

			instructionCheck = false;
			$("#errId").show();
			
			break;
		}

	}

	if (instructionCheck) {
		let counter = 1;
		
		for(let instructDivs of $('[id^="instructionDiv"]').children()){
			var instruction = $("#"+instructDivs.id).find('textarea[id^="instructionValue"]').get(0).value;
			fetch('http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/mamasdish/addInstructions/' + recipeId, {
				method: 'post',
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({ stepNumber: counter, description: instruction })
			}).then(res => res.json())
				.then();
			counter++;
		}
		
		fetch('http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/mamasdish/admin/approvalRequest/' + recipeId)
				.then(data => data.json())
				.then(function(data) {
				});
		
				window.open('/awaitApproval/' + recipeId ,  '_self');
		
	}
}

//deletes the old instructions for the recipe and add the updated instructions by calling coresponding APIs
function editInstruction() {

	var recipeId = $("#recipeId").html();

	var instructions = $('[id^="instructionValue"]');
	var instructionCheck = true;

	for (let instruct of instructions) {

		if (instruct.value == "") {

			instructionCheck = false;
			$("#errId").show();
			
			break;
		}

	}

	if (instructionCheck) {
		let counter = 1;
		
		fetch('http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/mamasdish/deleteInstructions/' + recipeId )
			.then(data => data.json())
			.then(function(data) {
			
		});
		
		for(let instructDivs of $('[id^="instructionDiv"]').children()){
			var instruction = $("#"+instructDivs.id).find('textarea[id^="instructionValue"]').get(0).value;
			fetch('http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/mamasdish/addInstructions/' + recipeId, {
				method: 'post',
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({ stepNumber: counter, description: instruction })
			}).then(res => res.json())
				.then(
				);
			counter++;
		}
		
		fetch('http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/mamasdish/admin/approvalRequest/' + recipeId)
				.then(data => data.json())
				.then(function(data) {
				});
		
				window.open('/awaitApproval/' + recipeId ,  '_self');
		
	}
}

//addes new instruction dive when click on add button
function addInstruction() {
	var divID = getNewId();
	$("#instructionDiv").append("<div class='row' id='instructionBox" + divID + "'> <div class='col s11' > <div class='card' style='border-radius: 15px'>" +
		"<div class='card-content'> <div class='row'> <div class='input-field col s12'>" +
		"<textarea id='instructionValue" + divID + "' class='materialize-textarea' name='instruction'></textarea>" +
		"<label for='instruction'>Instruction</label> </div></div></div></div></div>" +
		"<div class='col s1' style='padding-top: 90px'>" +
		"<a class='btn-floating' onclick='deleteInstruction(" + divID + ")' id='deleteBtn'>" +
		"<i class='small material-icons'>delete_forever</i></a>" +
		"</div></div>");
}

//deletes div with specific id 
function deleteInstruction(id) {

	if (($('[id^="instructionDiv"]').children()).length > 1) {

		$("#instructionBox" + id).remove();

	}

}
