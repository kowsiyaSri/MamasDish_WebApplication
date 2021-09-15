function approveRecipe(id){
	fetch('http://localhost:8080/mamasdish/admin/approveRecipe/' + id)
				.then(data => data.json())
				.then(function(data) {
					console.log(data);
   					 $("#authBtn").removeClass("waves-effect waves-teal").addClass('disabled');
   					 $("#approveText").css("display","block");


				});
				
				

}