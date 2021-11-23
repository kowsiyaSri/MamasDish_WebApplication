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
    
	//checking the boxes for countries
	if(countriesChecked != null){	
		$.each(countriesChecked, function(key, value) {
			$(".country[value='"+ value +"']").attr("checked", "checked");
		});
	}
	
	//checking boxes for proteins
	if(proteinsChecked != null){	
		$.each(proteinsChecked, function(key, value) {
			$(".protein[value='"+ value +"']").attr("checked", "checked");
		});
	}
	
	//checking boxes for proteins
	if(dietsChecked != null){	
		$.each(dietsChecked, function(key, value) {
			$(".diet[value='"+ value +"']").attr("checked", "checked");
		});
	}
	
	//checking the calories
	var startCal = 0;
	var endCal = 0;
	
	if(cal1 != null && cal1 != 0){
		startCal = cal1;
	}
	if(cal2 != null && cal2 != 0){
		endCal = cal2;
	}
	
	//set up slider
	var slider = document.getElementById('slider');
	noUiSlider.create(slider, {
		start: [startCal, endCal],
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