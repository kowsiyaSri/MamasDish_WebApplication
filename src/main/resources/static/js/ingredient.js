function saveRecipe() {

	var ingredientCount = $('[id^="ingredientBox"]').length;
	var recipeId = $("#recipeId").html();

	var ingredientNames = $('[id^="ingredientName"]');
	var ingredientCheck = true;

	for (let ingrName of ingredientNames) {

		if (ingrName.value == "") {

			ingredientCheck = false;
			break;
		}

	}

	if (ingredientCheck) {

		for (let i = 1; i <= ingredientCount; i++) {

			var ingredient = $("#ingredientName" + i).val();
			var measurement = $("#measurement" + i).val();
			var quantity = $("#quantity" + i).val();
			var protein = $("#proteinType" + i).val();

			var el = $("#protein" + i);
			if (el[0].checked) {
				console.log(protein)
				if (protein == null) {
					protein = 0;
				} else {
					protein = parseInt(protein)
				}
			} else {
				protein = 0;
			}



			if (measurement == null) {
				measurement = 0;
			} else {
				measurement = parseInt(measurement)
			}


			if (quantity == "") {
				quantity = 0;
			} else {
				quantity = parseInt(quantity)
			}

			console.log(ingredient);
			console.log("measurement: " + measurement);
			console.log("quantity:" + quantity);
			console.log("protein:" + protein);


			fetch('http://localhost:8080/mamasdish/addIngredient/' + ingredient + '/' + quantity + '/' + measurement + '/' + recipeId + '/' + protein)
				.then(data => data.json())
				.then(function(data) {
					console.log(data);
				});


		}

		//go to instruction page
		window.open('/addInstructions/' + recipeId, '_self');

	}




}

function addIngredient(id) {

	var test = $("#Quantity1").val();

	console.log($("#recipeId").html());

}


function showProtein(el) {
	var str = el.name;

	var divId = str.split("protein");

	if (el.checked) {
		$("#proteinDiv" + divId[1]).css("display", "block")
	}
	else{
		$("#proteinDiv" + divId[1]).css("display", "none")
	}
}



function newIngredient() {

	var measurements = getMeasurements();
	var divID = $('[id^="ingredientBox"]').length + 1;
	var proteinTypes = getProteins();

	$("#ingredientDiv").append("<div class='row' id='ingredientBox" + divID + "'> <div class='col s12 m6'> <div class='card' style='border-radius:15px'>" +
		"<div class='card-content'> <div class='row'> <div class='input-field col s2'> " +
		"<input id='quantity" + divID + "' type='number' name='quantity' min='1'> <label for='quantity'>Quantity</label>" +
		"</div> <div class='input-field col s4'>" +
		"<select class='browser-default'id='measurement" + divID + "' required>" +
		"<option value=''  selected disabled>Measurement</option>" +
		"</select> " +
		"</div> <div class='input-field col s6'><input id='ingredientName" + divID + "' type='text' name='ingredient' required>" +
		"<label for='ingredient'>Ingredient</label> </div>" +
		"</div> <div class='row' style='padding-bottom:10px; padding-left:540px;'> " +
		"<div class='input-field'><label> <input type='checkbox' class='filled-in' id='protein" + divID + "'  name='protein" + divID + "' onClick='showProtein(this)'  /> <span>Protein</span>" +
		"</label></div></div>" +
		"<div class='row' id='proteinDiv" + divID + "'style='display:none'>" +
		"<label>Protein</label> <br> <br> <select class='browser-default' id='proteinType" + divID + "' name='proteinType' required>" +
		"<option value=''  selected disabled></option>" +
		"</select> </div> </div> </div> </div> " +
		"<div class='col s6' style='padding-top:90px'> <i class='small material-icons'>delete_forever</i>" +
		"</div></div></div>");



	for (let measurement of measurements) {

		$("#measurement" + divID).append($('<option>', {
			value: measurement.id,
			text: measurement.measurementType
		}));
	}

	for (let protein of proteinTypes) {

		$("#proteinType" + divID).append($('<option>', {
			value: protein.id,
			text: protein.proteinType
		}));
	}



}
