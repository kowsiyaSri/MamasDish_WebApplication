//Functionality for users to add ingredients to recipe

function saveRecipe() {

	var recipeId = $("#recipeId").html();

	//Adding all ingredient divs to an array
	var ingredientNames = $('[id^="ingredientName"]');
	var ingredientCheck = true;

	//Filtering through array and checking all ingredient information
	for (let ingrName of ingredientNames) {
	
			if (ingrName.value == "") {
	//If ingredient information is incorrect then the user cannot go to next step
				ingredientCheck = false;
				$("#errId").show();
				
				break;
			}

	}

	//If all ingredients are valid they will be saved to database
	if (ingredientCheck) {		

		for(let ingrDivs of $('[id^="ingredientDiv"]').children()){

			//Getting values for ingredients, measurement, quantity and protein
			var ingredient = $("#"+ingrDivs.id).find('input[id^="ingredientName"]').get(0).value;
			var measurement = $("#"+ingrDivs.id).find('select[id^="measurement"]').get(0).value;
			var quantity = $("#"+ingrDivs.id).find('input[id^="quantity"]').get(0).value;
			var protein = $("#"+ingrDivs.id).find('select[id^="proteinType"]').get(0).value;
			
			//If ingredient is protein, protein type will be retrieced and saved to database
			var el = $("#"+ingrDivs.id).find('input[id^="protein"]').get(0);
			if (el.checked) {
				if (protein == null) {
					protein = 0;
				} else {
					protein = parseInt(protein)
				}
			} else {
				protein = 0;
			}

			if (measurement == "") {
				measurement = 0;
			} else {
				measurement = parseInt(measurement)
			}

			if (quantity == "") {
				quantity = 0;
			} else {
				quantity = parseInt(quantity)
			}

			//API call to save ingredients to database
			fetch('http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/mamasdish/addIngredient/' + ingredient + '/' + quantity + '/' + measurement + '/' + recipeId + '/' + protein)
				.then(data => data.json())
				.then(function(data) {
				});


		}

		//go to instruction page
		window.open('/chefs/addInstructions/' + recipeId, '_self');

	}
}

//Same process as add ingredient
function editRecipe() {

	var recipeId = $("#recipeId").html();

	var ingredientNames = $('[id^="ingredientName"]');
	var ingredientCheck = true;

	for (let ingrName of ingredientNames) {

			if (ingrName.value == "") {
	
				ingredientCheck = false;
				$("#errId").show();
				
				break;
				
			}

	}

	if (ingredientCheck) {		

		fetch('http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/mamasdish/deleteIngredients/' + recipeId )
			.then(data => data.json())
			.then(function(data) {
		});
		
		for(let ingrDivs of $('[id^="ingredientDiv"]').children()){

			var ingredient = $("#"+ingrDivs.id).find('input[id^="ingredientName"]').get(0).value;
			var measurement = $("#"+ingrDivs.id).find('select[id^="measurement"]').get(0).value;
			var quantity = $("#"+ingrDivs.id).find('input[id^="quantity"]').get(0).value;
			var protein = $("#"+ingrDivs.id).find('select[id^="proteinType"]').get(0).value;
			
			var el = $("#"+ingrDivs.id).find('input[id^="protein"]').get(0);
			if (el.checked) {
				if (protein == null) {
					protein = 0;
				} else {
					protein = parseInt(protein)
				}
			} else {
				protein = 0;
			}



			if (measurement == "") {
				measurement = 0;
			} else {
				measurement = parseInt(measurement)
			}


			if (quantity == "") {
				quantity = 0;
			} else {
				quantity = parseInt(quantity)
			}

			fetch('http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/mamasdish/addIngredient/' + ingredient + '/' + quantity + '/' + measurement + '/' + recipeId + '/' + protein)
				.then(data => data.json())
				.then(function(data) {
				});
		}

		//go to instruction page
		window.open('/chefs/editInstructions/' +recipeId, '_self');

	}
}

//Functionality for displaying protein
function showProtein(el) {
	var str = el.name;

	var divId = str.split("protein");

	//If protein box is checked, protein options will be displayed
	if (el.checked) {
		$("#proteinDiv" + divId[1]).css("display", "block")
	}
	else{
		$("#proteinDiv" + divId[1]).css("display", "none")
	}
}


//Creates a new random id for each div created
function getNewId(){
	
	var newDivId = true;
	
	var divID = Math.floor(Math.random() * 100);

	while(checkId(divID) == false){
	
			divID = Math.floor(Math.random() * 100);
	}
	
	return divID;
	
}

//Checking if id already exists
function checkId(id){

	for(let ingrDivs of $('[id^="ingredientBox"]')){
			var num = (ingrDivs.id).split("ingredientBox");
			var num = num[1];
			
			
			if(num == id){
				return false;
			
			} else {
			
				return true;
			}
			
		}

}


//Creates new ingredient div box and returns it each time user selects add ingredient
function newIngredient() {

	var measurements = getMeasurements();
	
	var divID = getNewId();

	var proteinTypes = getProteins();

	$("#ingredientDiv").append("<div class='row' id='ingredientBox" + divID + "'> <div class='col s11'> <div class='card' style='border-radius:15px'>" +
		"<div class='card-content'> <div class='row'> <div class='input-field col s2'> " +
		"<input id='quantity" + divID + "' type='number' name='quantity' min='1'> <label for='quantity'>Quantity</label>" +
		"</div> <div class='input-field col s4'>" +
		"<select style='color:#5085A' class='browser-default'id='measurement" + divID + "' required>" +
		"<option value=''  selected disabled  style='color:#5085A5'>Measurement</option>" +
		"</select> " +
		"</div> <div class='input-field col s6'><input id='ingredientName" + divID + "' type='text' name='ingredient' required>" +
		"<label for='ingredient'>Ingredient</label> </div>" +
		"</div> <div class='row' style='padding-bottom:10px; padding-left:650px;'> " +
		"<div class='input-field'><label> <input type='checkbox' class='filled-in' id='protein" + divID + "'  name='protein" + divID + "' onClick='showProtein(this)'  /> <span>Protein</span>" +
		"</label></div></div>" +
		"<div class='row' id='proteinDiv" + divID + "'style='display:none'>" +
		"<label>Protein</label> <br> <br> <select class='browser-default' id='proteinType" + divID + "' name='proteinType' required>" +
		"<option value=''  selected disabled></option>" +
		"</select> </div> </div> </div> </div> " +
		"<div class='col s1' style='padding-top:90px'>" +
			"<a class='btn-floating' onclick='deleteIngredient("+divID+")' id='deleteBtn'>"+
				"<i class='small material-icons'>delete_forever</i>" +
		"</a></div></div></div>");


	//Adding measurement options to new ingredient div
	for (let measurement of measurements) {

		$("#measurement" + divID).append($('<option>', {
			value: measurement.id,
			text: measurement.measurementType
		}));
	}
	
	//Adding protein options to new ingredient div
	for (let protein of proteinTypes) {

		$("#proteinType" + divID).append($('<option>', {
			value: protein.id,
			text: protein.proteinType
		}));
	}
}

//Function to delete ingredients
function deleteIngredient(id){
	
	if(($('[id^="ingredientDiv"]').children()).length >1){
	//takes div id and removes it
		$("#ingredientBox"+id).remove();
	}
	
}
