$(document).ready(function() {

	var checkbox = document.querySelector("input[name^=protein]");

	checkbox.addEventListener('change', function() {
		if (this.checked) {
		$("#proteinDiv").css("display", "block")
			
		} else {
			$("#proteinDiv").css("display", "none")
		}
	});


		console.log($('[id^="ingredientBox"]').length);

});


function addIngredient(id) {

	var test = $("#Quantity1").val();

	console.log($("#recipeId").html());

}


function myFunction(el) {
	var str = el.name;
	
	 var divId = str.split("protein");
	


        if(el.checked) {
			$("#proteinDiv" + divId[1]).css("display", "block")
        }
        else
           $("#proteinDiv" + divId[1]).css("display", "none")
    }



function newIngredient(){

 var divID = $('[id^="ingredientBox"]').length+1;

	$("#ingredientDiv").append("<div class='row' id='ingredientBox" + divID +"'> <div class='col s12 m6'> <div class='card' style='border-radius:15px'>" +
"<div class='card-content'> <div class='row'> <div class='input-field col s2'> " +
"<input id='Quantity1' type='number' name='quantity' min='1'> <label for='quantity'>Quantity</label>" +
"</div> <div class='input-field col s4'>" +
"<select class='browser-default'id='measurement"+ divID +"' required>"+
"<option th:text='Measurement' value=''  selected disabled></option>" +
"<option th:each='m : ${measurements}' th:value='${m.id}' th:text='${m.measurementType}'></option></select> " +
"</div> <div class='input-field col s6'><input id='ingredient1' type='text' name='ingredient'>" +
"<label for='ingredient'>Ingredient</label> </div>" +
"</div> <div class='row' style='padding-bottom:10px; padding-left:540px;'> " +
"<div class='input-field'><label> <input type='checkbox' class='filled-in' name='protein"+ divID +"' onClick='myFunction(this)'  /> <span>Protein</span>" +
"</label></div></div>"+
"<div class='row' id='proteinDiv" + divID + "'style='display:none'>" +
"<label>Protein</label> <br> <br> <select class='browser-default' name='protienType' required>" +
"<option th:each='p : ${proteins}' th:value='${p.id}'th:text='${p.proteinType}'></option>" +
"</select> </div> </div> </div> </div> " +
"<div class='col s6' style='padding-top:60px'> <i class='medium material-icons'>delete_forever</i>" +
"</div></div></div>");









}






/*




"<div class='row'> <div class='col s12 m6'> <div class='card' style='border-radius:15px'>" +
"<div class='card-content'> <div class='row'> <div class='input-field col s2'> " +
"<input id='Quantity1' type='number' name='quantity' min='1'> <label for='quantity'>Quantity</label>" +
"</div> <div class='input-field col s4'>" +
"<select id='measurement1' class='browser-default'name='measurement' required>"+
"<option th:text='Measurement' value=''  selected disabled></option>" +
"<option th:each='m : ${measurements}' th:value='${m.id}' th:text='${m.measurementType}'></option></select> " +
"</div> <div class='input-field col s6'><input id='ingredient1' type='text' name='ingredient'>" +
"<label for='ingredient'>Ingredient</label> </div>"
"</div> <div class='row' style='padding-bottom:10px; padding-left:540px;'> " +
"<div class='input-field'><label> <input type='checkbox' class='filled-in' name='protein' /> <span>Protein</span>"
"</label></div></div>"+
"<div class='row' id='proteinDiv' style='display:none'>" +
"<label>Protein</label> <br> <br> <select class='browser-default' name='protienType' required>" +
"<option th:each='p : ${proteins}' th:value='${p.id}'th:text='${p.proteinType}'></option>" +
"</select> </div> </div> </div> </div> " +
"<div class='col s6' style='padding-top:60px'> <i class='medium material-icons'>delete_forever</i>" +
"</div></div></div>"
			





 */