function getRatings() {
			
	

        // Round to nearest 10
        var rating = getRating();
         const starPercentage = (parseInt(rating) * 100) / 5;
        console.log(starPercentage)
        
        let starPercentageRounded = Math.round(parseFloat(starPercentage) / 10) * 10;
        console.log("rating"+starPercentage)
        document.getElementById("stars-inner").style.width = starPercentageRounded+'%';
	
		}
		// Run getRatings when DOM loads 
document.addEventListener('DOMContentLoaded', getRatings)

