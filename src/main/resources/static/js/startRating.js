function getRatings() {
			
	

        // Round to nearest 10
        var rating = getRating();
        console.log("Rating inside js"+rating)
         const starPercentage = (parseInt(rating) * 100) / 5;
        console.log(starPercentage)
        
        let starPercentageRounded = Math.round(parseFloat(starPercentage) / 10) * 10;
        console.log("rating"+starPercentage)
        document.getElementById("stars-inner").style.width = starPercentageRounded+'%';
	
		}
function writeReview() {

	var recipeId = getRecipeId();
	var reviews = getReviews();
    console.log("write review  ")
		//go to instruction page
		window.open('/reviewRecipe/' +recipeId, '_self');

	}

		// Run getRatings when DOM loads 
document.addEventListener('DOMContentLoaded', getRatings,writeReview)

