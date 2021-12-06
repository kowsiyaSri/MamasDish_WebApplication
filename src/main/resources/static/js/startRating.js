function getRatings() {
			
	

        // Round to nearest 10
        var rating = getRating();
         const starPercentage = (parseInt(rating) * 100) / 5;
        
        let starPercentageRounded = Math.round(parseFloat(starPercentage) / 10) * 10;
        document.getElementById("stars-inner").style.width = starPercentageRounded+'%';
	
		}
function writeReview() {

	var recipeId = getRecipeId();
	var reviews = getReviews();
		//go to instruction page
		window.open('/reviewRecipe/' +recipeId, '_self');

	}

// Run getRatings when DOM loads 
document.addEventListener('DOMContentLoaded', getRatings,writeReview)

