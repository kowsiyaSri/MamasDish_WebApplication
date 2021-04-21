function saveInstruction() {

	var instructionCount = $('[id^="instructionBox"]').length;
	var recipeId = $("#recipeId").html();

	var instructions = $('[id^="instructionValue"]');
	var instructionCheck = true;

	for (let instruct of instructions) {

		if (instruct.value == "") {

			instructionCheck = false;
			break;
		}

	}

	if (instructionCheck) {

		for (let i = 1; i <= instructionCount; i++) {
		
			var instruction = $("#instructionValue" + i).val();
			fetch('http://localhost:8080/mamasdish/addInstructions/' + recipeId, {
				method: 'post',
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({ stepNumber: i, description: instruction })
			}).then(res => res.json())
				.then(res => console.log(res));
		}
	}
}

function addInstruction(){
	var divID = $('[id^="instructionBox"]').length + 1;
		$("#instructionDiv").append("<div class='row' id='instructionBox1'> <div class='col s12 m6'> <div class='card' style='border-radius: 15px'>" +
			"<div class='card-content'> <div class='row'> <div class='input-field col s12'>" +
			"<textarea id='instructionValue" + divID +"' class='materialize-textarea' name='instruction'></textarea>" +
			"<label for='instruction'>Instruction</label> </div></div></div></div></div>" +
			"<div class='col s6' style='padding-top: 90px'><i class='small material-icons'>delete_forever</i>" +
			"</div></div>");
}
/*
			"<div class='row' id='instructionBox1'> <div class='col s12 m6'> <div class='card' style='border-radius: 15px'>" +
			"<div class='card-content'> <div class='row'> <div class='input-field col s12'>" +
			"<textarea id='instructionValue" + divID +"' class='materialize-textarea' name='instruction'></textarea>" +
			"<label for='instruction'>Instruction</label> </div></div></div></div></div>" +
			"<div class='col s6' style='padding-top: 90px'><i class='small material-icons'>delete_forever</i>" +
			"</div></div>"
*/