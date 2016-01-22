/*
 * dependency: request
 */
var request = require('request'),
	qs = require('querystring');


function callback(error, response, data) {
	if (error)
		console.log('error|request_ext|', error)

	if (!error && response.statusCode == 200) {
		console.log('success|request|', data);
	}
}

function get(options, data) {
	var content = qs.stringify(data);
	options.path = options.path + "?" + content;
	options.url = "http://" + options.hostname + ":" + options.port + options.path
	request(options, callback);
}

function post(options, body) {
	// options.body = body;
	// options.json = true;
	options.body = JSON.stringify(body);
	options.url = "http://" + options.hostname + ":" + options.port + options.path
	console.log(options.url)
	console.log(options)
	request(options, callback);
}

module.exports = {
	get: get,
	post: post
}