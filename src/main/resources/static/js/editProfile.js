/*
* Updates the array of selected proteins, country, cuisine and diet types in edit profile.
*/

//variables for selected options
var selectProtein;
var selectCountry;
var selectCuisine;
var selectDiet;

$(document).ready(function() {

	//creates the selection options for the proteins
	selectProtein = sellect("#my-protein", {
		originList: listProtein,
		destinationList: userProtein,
		onInsert: updateProteinLists,
		onRemove: updateProteinLists
	});

	selectProtein.init();

	//creates the selection options for the diets
	selectDiet = sellect("#my-diet", {
		originList: listDiet,
		destinationList: userDiet,
		onInsert: updateDietLists,
		onRemove: updateDietLists
	});

	selectDiet.init();

	//creates the selection options for cuisines
	selectCuisine = sellect("#my-cuisine", {
		originList: listCuisine,
		destinationList: userCuisine,
		onInsert: updateCuisineLists,
		onRemove: updateCuisineLists
	});

	selectCuisine.init();

	//creates the selections options for Countries
	selectCountry = sellect("#my-country", {
		originList: listCountry,
		destinationList: userCountry,
		onInsert: updateCountryLists,
		onRemove: updateCountryLists
	});

	selectCountry.init();
});

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