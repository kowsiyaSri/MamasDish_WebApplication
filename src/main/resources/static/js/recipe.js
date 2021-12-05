/**
 * 
 Functionality for designated users to authenticate recipes
 */
 
 $(document).ready(function() {
	
})

//API call used to authenticate recipes
function authenticateRecipe(id) {
	fetch('http://localhost:8080/mamasdish/authenticate/' + id)
		.then(data => data.json())
		.then(function(data) {
			
		});


}