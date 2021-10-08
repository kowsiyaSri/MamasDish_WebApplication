$(document).ready(function() {
	 $('select').formSelect();
	     $('.sidenav').sidenav();
  $('.dropdown-trigger').dropdown();
  
	$.each( countries, function( key, value ) {
  		$( "#countryBoxes" ).append( "<p><label><input class='country' value='"+value +"' type='checkbox'/><span>" + value+ "</span></label></p>" );
});

	
});



function showCountries(){
	  $( "#countryBoxes" ).toggle();
  $("#countryBoxes").find("input[type=checkbox]").on("change",function() {
        if($(this).prop('checked')){
			var selCountry = $(this).prop('value');	
	$.each( $('p[id^="recipeCountry"]'), function( key, value ) {
						var id = value.id.substr(13);

			var recipeCoun = value.innerHTML;
			if(selCountry == recipeCoun){
				console.log("match");
				
				$("#recipe" + id).css("display","block");

				
			} else {
								$("#recipe" + id).remove();

			}
		});
	
		}

		
         
    });
}

