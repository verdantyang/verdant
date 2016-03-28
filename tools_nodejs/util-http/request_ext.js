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
	options.method = "GET";
	var content = qs.stringify(data);
	options.path = options.path + "?" + content;
	options.url = "http://" + options.host + ":" + options.port + options.path;

	request(options, callback);
}

function post(options, body) {
	options.method = "POST";
	if (options.headers["Content-Type"].indexOf("json") > 0)
		options.body = JSON.stringify(body);
	else
		options.body = qs.stringify(body);
	options.url = "http://" + options.host + ":" + options.port + options.path;

	request(options, callback);
}

module.exports = {
	get: get,
	post: post
}