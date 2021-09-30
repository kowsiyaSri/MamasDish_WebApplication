function saveRecipe() {

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

		for(let ingrDivs of $('[id^="ingredientDiv"]').children()){


			var ingredient = $("#"+ingrDivs.id).find('input[id^="ingredientName"]').get(0).value;
			var measurement = $("#"+ingrDivs.id).find('select[id^="measurement"]').get(0).value;
			var quantity = $("#"+ingrDivs.id).find('input[id^="quantity"]').get(0).value;
			//console.log($("#"+ingrDivs.id).find('input[id^="quantity"]').get());
			console.log($("#"+ingrDivs.id).find('select[id^="proteinType"]').get());
			var protein = $("#"+ingrDivs.id).find('select[id^="proteinType"]').get(0).value;
			
			var el = $("#"+ingrDivs.id).find('input[id^="protein"]').get(0);
			if (el.checked) {
				console.log(protein)
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
		window.open('/chefs/addInstructions/' + recipeId, '_self');

	}
}

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

		fetch('http://localhost:8080/mamasdish/deleteIngredients/' + recipeId )
			.then(data => data.json())
			.then(function(data) {
			console.log(data);
		});
		
		for(let ingrDivs of $('[id^="ingredientDiv"]').children()){


			var ingredient = $("#"+ingrDivs.id).find('input[id^="ingredientName"]').get(0).value;
			var measurement = $("#"+ingrDivs.id).find('select[id^="measurement"]').get(0).value;
			var quantity = $("#"+ingrDivs.id).find('input[id^="quantity"]').get(0).value;
			//console.log($("#"+ingrDivs.id).find('input[id^="quantity"]').get());
			console.log($("#"+ingrDivs.id).find('select[id^="proteinType"]').get());
			var protein = $("#"+ingrDivs.id).find('select[id^="proteinType"]').get(0).value;
			
			var el = $("#"+ingrDivs.id).find('input[id^="protein"]').get(0);
			if (el.checked) {
				console.log(protein)
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
		window.open('/chefs/editInstructions/' +recipeId, '_self');

	}
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


function getNewId(){

	
	var newDivId = true;
	
	var divID = Math.floor(Math.random() * 100);
	

	while(checkId(divID) == false){
	
			divID = Math.floor(Math.random() * 100);
	}
	
	return divID;
	
}


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


function newIngredient() {

	var measurements = getMeasurements();
	
	var divID = getNewId();
	

	console.log(divID);
	var proteinTypes = getProteins();

	$("#ingredientDiv").append("<div class='row' style='margin-left:33%' id='ingredientBox" + divID + "'> <div class='col s12 m6'> <div class='card' style='border-radius:15px'>" +
		"<div class='card-content'> <div class='row'> <div class='input-field col s2'> " +
		"<input id='quantity" + divID + "' type='number' name='quantity' min='1'> <label for='quantity'>Quantity</label>" +
		"</div> <div class='input-field col s4'>" +
		"<select style='color:#5085A' class='browser-default'id='measurement" + divID + "' required>" +
		"<option value=''  selected disabled  style='color:#5085A5'>Measurement</option>" +
		"</select> " +
		"</div> <div class='input-field col s6'><input id='ingredientName" + divID + "' type='text' name='ingredient' required>" +
		"<label for='ingredient'>Ingredient</label> </div>" +
		"</div> <div class='row' style='padding-bottom:10px; padding-left:380px;'> " +
		"<div class='input-field'><label> <input type='checkbox' class='filled-in' id='protein" + divID + "'  name='protein" + divID + "' onClick='showProtein(this)'  /> <span>Protein</span>" +
		"</label></div></div>" +
		"<div class='row' id='proteinDiv" + divID + "'style='display:none'>" +
		"<label>Protein</label> <br> <br> <select class='browser-default' id='proteinType" + divID + "' name='proteinType' required>" +
		"<option value=''  selected disabled></option>" +
		"</select> </div> </div> </div> </div> " +
		"<div class='col s6' style='padding-top:90px'>" +
			"<a class='btn-floating' onclick='deleteIngredient("+divID+")' id='deleteBtn'>"+
				"<i class='small material-icons'>delete_forever</i>" +
		"</a></div></div></div>");


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

function deleteIngredient(id){
		
	
	if(($('[id^="ingredientDiv"]').children()).length >1){
	
		$("#ingredientBox"+id).remove();
	
	}

	
}
