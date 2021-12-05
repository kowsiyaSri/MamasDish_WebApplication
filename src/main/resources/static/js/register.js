/*
* Sets up the multiple selection options for the preferences 
*/

var selectProtein;
var selectCountry;
var selectCuisine;
var selectDiet;

$(document).ready(function() {

	//sets up the protein selection
	selectProtein = sellect("#my-protein", {
		originList: listProtein,
		destinationList: [],
		onInsert: updateProteinLists,
		onRemove: updateProteinLists
	});

	selectProtein.init();

	//sets up the diet selection
	selectDiet = sellect("#my-diet", {
		originList: listDiet,
		destinationList: [],
		onInsert: updateDietLists,
		onRemove: updateDietLists
	});

	selectDiet.init();

	//sets up the cuisine selection
	selectCuisine = sellect("#my-cuisine", {
		originList: listCuisine,
		destinationList: [],
		onInsert: updateCuisineLists,
		onRemove: updateCuisineLists
	});

	selectCuisine.init();

	//sets up the countru selection
	selectCountry = sellect("#my-country", {
		originList: listCountry,
		destinationList: [],
		onInsert: updateCountryLists,
		onRemove: updateCountryLists
	});

	selectCountry.init();
});

function showDescription(el) {
	if (el.checked) {
		$("#descriptionBox").css("display", "block")
	}
	else {
		$("#descriptionBox").css("display", "none")
	}
}

//to return list of proteins that were selected
function updateProteinLists(event, item) {
	var selectedList = document.getElementById('selected-protein');
	var selectedArr;

	while (selectedList.firstChild) {
		selectedList.removeChild(selectedList.firstChild);
	}

	selectedArr = selectProtein.getSelected();

	selectedArr.forEach(function(item, index, arr) {
		var span = document.createElement('input');
		span.setAttribute("name", "proteins[]");
		span.setAttribute("type", "text");
		span.style.display = 'none';
		span.setAttribute("value", item);
		console.log(span);
		selectedList.appendChild(span);		
	});

}

//to return list of diets that were selected
function updateDietLists(event, item) {
	var selectedList = document.getElementById('selected-diet');
	var selectedArr;

	while (selectedList.firstChild) {
		selectedList.removeChild(selectedList.firstChild);
	}

	selectedArr = selectDiet.getSelected();

	selectedArr.forEach(function(item, index, arr) {
		var span = document.createElement('input');
		span.setAttribute("name", "diets[]");
		span.setAttribute("type", "text");
		span.style.display = 'none';
		span.setAttribute("value", item);
		console.log(span);
		selectedList.appendChild(span);
		
	});
}

//to return list of cuisines that were selected
function updateCuisineLists(event, item) {
	var selectedList = document.getElementById('selected-cuisine');
	var selectedArr;

	while (selectedList.firstChild) {
		selectedList.removeChild(selectedList.firstChild);
	}

	selectedArr = selectCuisine.getSelected();

	selectedArr.forEach(function(item, index, arr) {
		var span = document.createElement('input');
		span.setAttribute("name", "cuisines[]");
		span.setAttribute("type", "text");
		span.style.display = 'none';
		span.setAttribute("value", item);
		console.log(span);
		selectedList.appendChild(span);

	});

}

//to return list of countries that were selected
function updateCountryLists(event, item) {
	var selectedList = document.getElementById('selected-country');
	var selectedArr;

	while (selectedList.firstChild) {
		selectedList.removeChild(selectedList.firstChild);
	}

	selectedArr = selectCountry.getSelected();

	selectedArr.forEach(function(item, index, arr) {
		var span = document.createElement('input');
		span.setAttribute("name", "countries[]");
		span.setAttribute("type", "text");
		span.style.display = 'none';
		span.setAttribute("value", item);
		console.log(span);
		selectedList.appendChild(span);

	});

}