/*
* Purpose: Gathering the nutrition information for each ingredient for the recipe that is given using Nutritionx's API.
*/

$(document).ready(function() {
	$('.collapsible').collapsible();
	
	// Getting the ingredients from HTML side
	var ingredientListFromHTML = $('.js-ingredientList');
	var x = $(ingredientListFromHTML).children();
	//console.log($(x).length, x);

	// Getting the serving size from HTML side
	var servingSizeFromHTML = $('.js-servingSize');
	var y = $(servingSizeFromHTML).children();
	var servingSize = $(y[2]).data('servingsize');
	//console.log(servingSize);

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
	
	console.log(query);

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

	fetch('https://trackapi.nutritionix.com/v2/natural/nutrients', requestOptions)
		.then(data => data.json())
		.then(function(data) {
			var nutritionTotal = {
				nf_calories: data.foods.sum('nf_calories'),
				nf_total_fat: data.foods.sum('nf_total_fat'),
				nf_saturated_fat: data.foods.sum('nf_saturated_fat'),
				nf_cholesterol: data.foods.sum('nf_cholesterol'),
				nf_sodium: data.foods.sum('nf_sodium'),
				nf_total_carbohydrate: data.foods.sum('nf_total_carbohydrate'),
				nf_dietary_fiber: data.foods.sum('nf_dietary_fiber'),
				nf_sugars: data.foods.sum('nf_sugars'),
				nf_protein: data.foods.sum('nf_protein')
			};

			$('.js-totalCalories').text(Math.round(nutritionTotal.nf_calories * 100) / 100);
			$('.js-totalFat').text(Math.round(nutritionTotal.nf_total_fat * 100) / 100);
			$('.js-totalSaturatedFat').text(Math.round(nutritionTotal.nf_saturated_fat * 100) / 100);
			$('.js-totalCholesterol').text(Math.round(nutritionTotal.nf_cholesterol * 100) / 100);
			$('.js-totalSodium').text(Math.round(nutritionTotal.nf_sodium * 100) / 100);
			$('.js-totalCarbohydrate').text(Math.round(nutritionTotal.nf_total_carbohydrate * 100) / 100);
			$('.js-totalDietaryFiber').text(Math.round(nutritionTotal.nf_dietary_fiber * 100) / 100);
			$('.js-totalSugars').text(Math.round(nutritionTotal.nf_sugars * 100) / 100);
			$('.js-totalProtein').text(Math.round(nutritionTotal.nf_protein * 100) / 100);
		});
});

// Function that calculates the sum
Array.prototype.sum = function(prop) {
	var total = 0
	for (var i = 0, _len = this.length; i < _len; i++) {
		total += this[i][prop]
	}
	return total
}

