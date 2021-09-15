function getNewId() {


	var newDivId = true;

	var divID = Math.floor(Math.random() * 100);


	while (checkId(divID) == false) {

		divID = Math.floor(Math.random() * 100);
	}

	return divID;

}


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
			console.log(instructDivs)
			var instruction = $("#"+instructDivs.id).find('textarea[id^="instructionValue"]').get(0).value;
			fetch('http://localhost:8080/mamasdish/addInstructions/' + recipeId, {
				method: 'post',
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({ stepNumber: counter, description: instruction })
			}).then(res => res.json())
				.then(
				res => console.log(res));
			counter++;
		}
		
		fetch('http://localhost:8080/mamasdish/admin/approvalRequest/' + recipeId)
				.then(data => data.json())
				.then(function(data) {
					console.log(data);
				});
		
				window.open('/chefs/chefIndex/' ,  '_self');
		
	}
}

function addInstruction() {
	var divID = getNewId();
	$("#instructionDiv").append("<div class='row' id='instructionBox" + divID + "'> <div class='col s10 m6' style='margin-left:400px'> <div class='card' style='border-radius: 15px'>" +
		"<div class='card-content'> <div class='row'> <div class='input-field col s12'>" +
		"<textarea id='instructionValue" + divID + "' class='materialize-textarea' name='instruction'></textarea>" +
		"<label for='instruction'>Instruction</label> </div></div></div></div></div>" +
		"<div class='col s2' style='padding-top: 90px'>" +
		"<a class='btn-floating' onclick='deleteInstruction(" + divID + ")' id='deleteBtn'>" +
		"<i class='small material-icons'>delete_forever</i></a>" +
		"</div></div>");
}

function deleteInstruction(id) {

	if (($('[id^="instructionDiv"]').children()).length > 1) {

		$("#instructionBox" + id).remove();

	}

}
