$(document).ready(function() {

	//add country names for filter
	$.each(countries, function(key, value) {
		$("#countryBoxes").append("<p><label><input name='countries[]' class='country' value='" + value + "' type='checkbox'/><span>" + value + "</span></label></p>");
	});

	//adds diet names for filter
	$.each(diets, function(key, value) {
		$("#dietBoxes").append("<p><label><input name='diets[]' class='diet' value='" + value + "' type='checkbox'/><span>" + value + "</span></label></p>");
	});

	//adds protein names for filter
	$.each(proteins, function(key, value) {
		$("#proteinBoxes").append("<p><label><input name='proteins[]' class='protein' value='" + value + "' type='checkbox'/><span>" + value + "</span></label></p>");
	});

	//set up slider
	var slider = document.getElementById('slider');
	noUiSlider.create(slider, {
		start: [0, 0],
		connect: true,
		step: 100,
		range: {
			'min': 0,
			'max': 2000
		},
		format: wNumb({
			decimals: 0
		})
	});

	slider.noUiSlider.on('update', function(values) {
		$("#cal1").val(values[0]);
		$("#cal2").val(values[1]);
	});
});

function clearText() {

	console.log("VALUE: " + document.getElementById('searchBar').value);

	if (document.getElementById('searchBar').value !== '' || (document.getElementById('searchBar').value).isEmpty) {
		document.getElementById('searchBar').value = '';
		window.open("/users/viewAllRecipe", '_self');

	}


}

function showCountries() {
	$("#countryBoxes").toggle();
}

function showDiets() {
	$("#dietBoxes").toggle();
}

function showProteins() {
	$("#proteinBoxes").toggle();
}

function showSlider() {
	$("#sliderDiv").toggle();
}