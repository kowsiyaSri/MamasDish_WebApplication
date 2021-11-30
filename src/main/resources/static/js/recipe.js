/**
 * 
 */
 
 $(document).ready(function() {
	
	console.log("Ready");
})

function authenticateRecipe(id) {
	fetch('http://localhost:8080/mamasdish/authenticate/' + id)
		.then(data => data.json())
		.then(function(data) {
			console.log(data);
			
		});


}