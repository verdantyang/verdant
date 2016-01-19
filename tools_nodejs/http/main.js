var httptools = require('./http-tools.js');

exports.create = function(name) {
	return {
		name: name,
		httptools: httptools
	};
};