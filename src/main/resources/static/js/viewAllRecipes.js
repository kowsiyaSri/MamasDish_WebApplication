/**
 * 
 */
  // Or with jQuery

function clearText(){
	
	console.log("VALUE: " + document.getElementById('searchBar').value);
	
	if(document.getElementById('searchBar').value !== '' || (document.getElementById('searchBar').value).isEmpty){
			document.getElementById('searchBar').value = '';
		window.open("/users/viewAllRecipe", '_self' );

	}
	
}