/*
Functionality for approving recipes and getting nutritition information
 */
var mySellect;
var calories;
$(document).ready(function() {

	$('#textarea1').val('');
	M.textareaAutoResize($('#textarea1'));

	//Setting values for rejection reason 
	mySellect = sellect("#my-element", {
		originList: ['Description', 'Ingredients', 'Instructions'],
		destinationList: [],
		onInsert: updateDemoLists,
		onRemove: updateDemoLists
	});

	//Initializing select box
	mySellect.init();
	
	// Getting the ingredients from HTML side
	var ingredientListFromHTML = $('.js-ingredientList');
	var x = $(ingredientListFromHTML).children();
	
	// Getting the serving size from HTML side
	var servingSizeFromHTML = $('.js-servingSize');
	var y = $(servingSizeFromHTML).children();
	var servingSize = $(y[2]).data('servingsize');
	
	
	var query = "";

	// Looping through each child of ingredientListFromHTML to get the ingredients
	for (var i = 0; i <= x.length; i++) {
		var food = x[i];
		var quantity = $(food).data('quantity');
		var type = $(food).data('type');
		var name = $(food).data('name');
		if (quantity && name) {
			query += `${quantity} ${type} ${name}\n`
		}
	}
	
	// Nutritionx API Call
	var myHeaders = new Headers();
	myHeaders.append("x-app-id", "52c550ac");
	myHeaders.append("x-app-key", "c9873f02bd95c74d5de0934edd09ff7a");
	myHeaders.append("Content-Type", "application/json");

	var raw = JSON.stringify({
		"query": query,
		"num_servings": servingSize,
		"line_delimited": true,
		"use_raw_foods": true
	});
	
	var requestOptions = {
		method: 'POST',
		headers: myHeaders,
		body: raw,
		redirect: 'follow'
	};
	
	//Gets nutrition information which will be saved to database using Javascript
	fetch('https://trackapi.nutritionix.com/v2/natural/nutrients', requestOptions)
		.then(data => data.json())
		.then(function(data) {
			var nutritionTotal = {
				nf_calories: data.foods.sum('nf_calories')

			};

				calories = Math.round(nutritionTotal.nf_calories * 100) / 100;
				console.log(calories);
		}).then(function(data){
			addCalories();	
		}
		);

});

// Function that calculates the sum of calories
Array.prototype.sum = function(prop) {
	var total = 0
	for (var i = 0, _len = this.length; i < _len; i++) {
		total += this[i][prop]
	}
	return total
}

//API call used to add sum of calories to database
function addCalories(){
	var path = (window.location.pathname).split('/');
	var recipeId = path[3];

	fetch('http://localhost:8080/mamasdish/addCalorie/' + calories + '/' + recipeId)
		.then(data => data.json())
		.then(function(data) {
		});
}

//API call to approve recipe
function approveRecipe(id) {
	fetch('http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/mamasdish/admin/approveRecipe/' + id)
		.then(data => data.json())
		.then(function(data) {
			//Authentication button is disabled once the recipe has been approved
			$("#authBtn").removeClass("waves-effect waves-teal").addClass('disabled');
			$("#approveText").css("display", "block");
		});

	fetch('http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/mamasdish/admin/RecipeApproval/' + id)
		.then(data => data.json())
		.then(function(data) {
			console.log(data);
		});
		
				var adminPage = window.open('/admin', '_self');
				adminPage.location.reload;
}

//Once user selects reject recipe, rejection options are displayed.
//Admin staff can also write a message
function rejectRecipe(id) {
	$("#rejectionBox").toggle();
}



// demo code to return lists
function updateDemoLists(event, item) {
	var selectedList = document.getElementById('selected-list');
	var unselectedList = document.getElementById('unselected-list');
	var selectedArr;
	var unselectedArr;

	while (selectedList.firstChild) {
		selectedList.removeChild(selectedList.firstChild);
	}

	selectedArr = mySellect.getSelected();

	selectedArr.forEach(function(item, index, arr) {
	var span = document.createElement('input');
		span.setAttribute("name", "reason[]");
		span.setAttribute("type", "text");
		span.style.display = 'none';
		span.setAttribute("value", item);
		console.log(span);
		selectedList.appendChild(span);
	});

}