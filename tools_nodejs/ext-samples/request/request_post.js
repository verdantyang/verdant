//npm install request
var request = require('request');

var options = {
	headers: {
		"Connection": "close"
	},
	url: 'http://127.0.0.1:3005/Config',
	method: 'POST',
	json: true,
	body: {
		data: "aaa"
	}
};

function callback(error, response, data) {
	if (!error && response.statusCode == 200) {
		console.log('----info------', data);
	}
}

request(options, callback);