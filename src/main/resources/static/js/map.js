function initMap() {
	
var options = {
      zoom:3,
      center: { lat:43.478563, lng:-35.182627},
	  mapTypeControl: false,
	  streetViewControl: false,
	  styles:[
  {
    "featureType": "administrative.land_parcel",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "administrative.locality",
    "elementType": "geometry",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "administrative.neighborhood",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "administrative.province",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "landscape",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "poi",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "road",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "transit",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "water",
    "stylers": [
      {
        "color": "#becedd"
      }
    ]
  }
]
   }
var map = new google.maps.Map(document.getElementById('map'), options);

fetch('http://localhost:8080/mamasdish/countryRecipes')
	.then(data => data.json())
	.then(function(data){
		var markers = [];
		var information=[];
		for(let i = 0; i < data.length; i++){
			if(data[i].recipes.length > 0){
					
				markers[i] = new google.maps.Marker({
				   position:{lat: parseFloat(data[i].country.latitude), lng: parseFloat(data[i].country.longitude)}, 
				   map:map, 
				   icon:'/images/marker2.png',
				   draggable: false
				});
				
				var MAX_SIZE = 3;
				var contentString = "<div><div id='discoverCountry'>" + data[i].country.name + "</div><ul style='margin-top:10px;padding:0px;border:0px'>";
				for (let x=0; x < data[i].recipes.length ; x++){
				
					var recipeImg = ""
					
					if(data[i].recipes[x].recipeImg == null){
						recipeImg = "shakshuka.png"
					} else {
						recipeImg = data[i].recipes[x].recipeImg + ".jpeg"
					}
					if (x < MAX_SIZE){
						contentString += "<li style='border:0px; padding:0px; margin-left:0px; width:400px;'>"
						contentString += "<div class='row' style='border: 2px solid #e4e8eb; padding:10px; border-radius: 15px;'><img class='col s4' style='height:70px; margin-left:0px;' src='/images/recipes/" + recipeImg + "'>"
						contentString += "<a id='discoverFont' class='col s8' style='margin-top:10px; padding-top:0px;' href=/users/viewRecipe/"+ data[i].recipes[x].recipeId + ">"
						contentString += data[i].recipes[x].recipeTitle + "</a></div></li>"						
					}
				}
				contentString += "</ul><p><a id='discoverFont' href=/users/viewRecipesByCountry/"+data[i].country.name+">"+"View All Recipes</p></div>"
				information[i] = new google.maps.InfoWindow({
				   content: contentString
				});
				
				markers[i].addListener('click', function() {
					information[i].open(map, markers[i]);
					map.panTo(this.getPosition()); map.setZoom(4);
				});	
			}
		}
	});		
}
