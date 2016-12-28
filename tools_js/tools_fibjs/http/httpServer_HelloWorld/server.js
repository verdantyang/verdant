"use strict";

/* This file defined and run the httpServer.
 */

var http = require('http'),
	mq = require('mq');
var config = require("./config.js"),
	hdlr = require("./router.js")['hello'];

var svrgo = function() {
	console.notice("====== start server ======");
	//create a http server
	var svr = new http.Server(config.addr, config.port, hdlr);
	//run the http server
	svr.run();
}

svrgo()