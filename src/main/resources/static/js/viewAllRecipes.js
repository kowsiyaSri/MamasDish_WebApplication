/**
 * 
 */

  document.addEventListener('DOMContentLoaded', function() {
    var elems = document.querySelectorAll('select');
    var instances = M.FormSelect.init(elems, options);
  });

  // Or with jQuery

function clearText(){
	
	console.log("VALUE: " + document.getElementById('searchBar').value);
	
	if(document.getElementById('searchBar').value !== '' || (document.getElementById('searchBar').value).isEmpty){
			document.getElementById('searchBar').value = '';
		window.open("viewAllRecipe", '_self' );

	}
	
}