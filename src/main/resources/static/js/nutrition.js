$(document).ready(function() {
	console.log("ready!");
	

	var myHeaders = new Headers();
	myHeaders.append("x-app-id", "52c550ac");
	myHeaders.append("x-app-key", "c9873f02bd95c74d5de0934edd09ff7a");
	myHeaders.append("Content-Type", "application/json");

	var raw = JSON.stringify({
		"query": "1 gram sugar"
	});

	var requestOptions = {
		method: 'POST',
		headers: myHeaders,
		body: raw,
		redirect: 'follow'
	};

	fetch("https://trackapi.nutritionix.com/v2/natural/nutrients", requestOptions)
		.then(response => response.text())
		.then(result => console.log(result))
		.catch(error => console.log('error', error));
});