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
				var contentString = "<div><h6>" + data[i].country.name + "</h6>";
				for (let x=0; x < data[i].recipes.length ; x++){
					if (x < MAX_SIZE){
						contentString += "<p><a href=/viewRecipe/" +data[i].recipes[x].recipeId+">"+ data[i].recipes[x].recipeTitle+"</a></p>"
					}
				}
				contentString += "<p><a href=/viewRecipesByCountry/"+data[i].country.name+">"+"View All Recipes</p></div>"
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
