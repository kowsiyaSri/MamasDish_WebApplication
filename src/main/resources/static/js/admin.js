	var mySellect;
$(document).ready(function() {

	$('#textarea1').val('');
	M.textareaAutoResize($('#textarea1'));

	mySellect = sellect("#my-element", {
		originList: ['Description', 'Ingredients', 'Instructions'],
		destinationList: [],
		onInsert: updateDemoLists,
		onRemove: updateDemoLists
	});

	mySellect.init();

});


function approveRecipe(id) {
	fetch('http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/mamasdish/admin/approveRecipe/' + id)
		.then(data => data.json())
		.then(function(data) {
			console.log(data);
			$("#authBtn").removeClass("waves-effect waves-teal").addClass('disabled');
			$("#approveText").css("display", "block");
		});

	fetch('http://mamasdish-env.eba-k9gt2v97.us-east-1.elasticbeanstalk.com/mamasdish/admin/RecipeApproval/' + id)
		.then(data => data.json())
		.then(function(data) {
			console.log(data);
		});
		
				window.open('/admin', '_self');


}

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