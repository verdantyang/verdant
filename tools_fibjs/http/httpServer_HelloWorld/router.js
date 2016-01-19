"use strict";

/* This file defined the routing.
 */

var mq = require('mq'),
	http = require('http');
var _pageView = 0;

/**
 * @describe Hello World!
 * @param req HttpRequest
 * @return
 **/
function hello(req) {
	req.response.write('Hello World!');
	_pageView += 1;
}

/**
 * @describe pageView counter
 * @param req HttpRequest
 * @return
 **/
function count(req) {
	req.response.write('Hello World ' + _pageView + ' times!');
}

module.exports = {
	hello: new mq.Chain([
		/**
		 * @describe ignore the favicon.ico
		 * @param req HttpRequest
		 * @return
		 **/
		function(req) {
			if (req.address !== '/favicon.ico')
				console.notice("address:", req.address)
		},
		new mq.Routing({
			'^/hello$': hello,
			'^/count$': count,
			'^/favicon.ico$': function(r) {}
		})
	])
};