var selectProtein;
var selectCountry;
var selectCuisine;
var selectDiet;

$(document).ready(function() {
	console.log(proteins);
	console.log(countries);
	console.log(diets);
	console.log(cuisines);
	
	selectProtein = sellect("#my-protein", {
		originList: listProtein,
		destinationList: [],
		onInsert: updateProteinLists,
		onRemove: updateProteinLists
	});

	selectProtein.init();
	
	selectDiet = sellect("#my-diet", {
		originList: listDiet,
		destinationList: [],
		onInsert: updateDietLists,
		onRemove: updateDietLists
	});

	selectDiet.init();
	
	selectCuisine = sellect("#my-cuisine", {
		originList: listCuisine,
		destinationList: [],
		onInsert: updateCuisineLists,
		onRemove: updateCuisineLists
	});

	selectCuisine.init();
	
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

//to return lists that were selected
function updateProteinLists(event, item) {
	var selectedList = document.getElementById('selected-protein');
	var selectedArr;

	while (selectedList.firstChild) {
		selectedList.removeChild(selectedList.firstChild);
	}

	selectedArr = selectProtein.getSelected();

	selectedArr.forEach(function(item, index, arr) {
		var span = document.createElement('span');
		span.innerText = item;
		selectedList.appendChild(span);
	});

}

//to return lists that were selected
function updateDietLists(event, item) {
	var selectedList = document.getElementById('selected-diet');
	var selectedArr;

	while (selectedList.firstChild) {
		selectedList.removeChild(selectedList.firstChild);
	}

	selectedArr = selectDiet.getSelected();

	selectedArr.forEach(function(item, index, arr) {
		var span = document.createElement('span');
		span.innerText = item;
		selectedList.appendChild(span);
	});

}

//to return lists that were selected
function updateCuisineLists(event, item) {
	var selectedList = document.getElementById('selected-cuisine');
	var selectedArr;

	while (selectedList.firstChild) {
		selectedList.removeChild(selectedList.firstChild);
	}

	selectedArr = selectCuisine.getSelected();

	selectedArr.forEach(function(item, index, arr) {
		var span = document.createElement('span');
		span.innerText = item;
		selectedList.appendChild(span);
	});

}

//to return lists that were selected
function updateCountryLists(event, item) {
	var selectedList = document.getElementById('selected-country');
	var selectedArr;

	while (selectedList.firstChild) {
		selectedList.removeChild(selectedList.firstChild);
	}

	selectedArr = selectCountry.getSelected();

	selectedArr.forEach(function(item, index, arr) {
		var span = document.createElement('span');
		span.innerText = item;
		selectedList.appendChild(span);
	});

}