'use strict';

let http = require('http'),
	fs = require("fs"),
	url = require('url'),
	qs = require('querystring');
let request = require('request'),
	jsdom = require("jsdom"),
	async = require('async');

let record = new Array(31);

let browser = function(userid, startDate, endDate) {

	let formData = {
		UserId: userid,
		ctl07: "查询",
		StartDate: startDate,
		EndDate: endDate,
		__EVENTTARGET: '',
		__EVENTARGUMENT: '',
		__VIEWSTATE: "Ea5Aah9Z8HrCPvfUl/Sd7dyT/WGZ06ZYV7jh0V8kf9nAWzr4VTENrKAPOEbPVuRyqFRDumsYrIv6aY7ifUCcm6JXx7ruOFne2KS9tGNXo8wqPN3/FDBWcjwZtq2NpCt8+0C4UMss1ZLX+W2TTtM12s+rOPFb5Slpn5JWKvnEd36cQu1X6AiMnv9OJaay+B3GNl5epy0Me2o5lhDMhjwzYrL8Sv7z49vBskvCWeYjWXTIiKHRdAb9amyV9DrmNLyxdXjsfWw8pI8FjeJPSKYF18pCJvSIAjTXHxATDuWk2TOjw9tIq2fyiZ31jIrTnJ5Sbog4mIXluNhycW+SQcEg8pCOgm4Amf4Xx19sKXd6z5fsPnAUQKN4RlF2+ip3+OdsJHNs8vg4fXvcK3ZPussldXBk1n1WEjrbkNHX4TQBh2KCn8pBuKBaqjy3obMTNYmilv5xqPEFViai31ookZIbvzvRvMrj4wuWHW0WiDc3pkOT5RB0A9yrZtsTZprN/52a",
		__VIEWSTATEGENERATOR: "CA0B0334",
		__EVENTVALIDATION: "x4GNr5wYhYBEHU4FAKTxBGcMLYfOCzoHTFLv5HtjHMww4orum/dKffcg2tZ3K8inSU+Ms/5BHYTLpXtrb9PwUOOerBx/bTD2vhFPtuTO2TzDmG5iYNPT2Gkh3RY//yM7Q0OyfvN72/VMfam/v0Ch1va58scfb8eLiSB+nat2C1FTBuEUwH//WKGgq3YMdNUy"
	}

	let promise = new Promise((resolve, reject) => {
		request.post('http://kaoqin.geely.auto', {
			form: formData
		}, (error, response, body) => {
			if (error) {
				console.log('error|request_ext|', error)
				reject(error);
			}
			if (!error && response.statusCode == 200) {
				console.log('success|request');
				resolve(body);
			}
		})
	});
	return promise;
}


let parse = function(content) {
	jsdom.env(
		content, ["http://code.jquery.com/jquery.js"],
		(errors, window) => {
			let tableObj = window.document.getElementById("GridView1");
			let tableInfo = "";
			for (let i = 0; i < tableObj.rows.length - 1; i++) {
				for (let j = 0; j < tableObj.rows[i].cells.length; j++) {
					tableInfo += tableObj.rows[i].cells[j].textContent;
					tableInfo += " ";
				}
				tableInfo += "\n";
			}

			console.log(tableInfo);
		}
	);
}

let report = function(userid, month) {
	let promises = new Array();
	for (let i = 1; i < 31; i += 5)
		promises.push(browser(userid, "2016/9/" + i, "2016/9/" + (i + 5)).then(
			result => {
				parse(result)
			},
			error => {}))
	Promise.all(promises)
		// browser(userid, "2016/9/1", "2016/9/6").then(
		// 	result => {
		// 		console.error(result)
		// 		parse(result)
		// 	},
		// 	error => {})
}

let str = "000074596 张钰崧 2016/9/10 18:55:43 总部销售3楼";

// report(74596, 9);
// async.parallel({
// 	a: function(callback) {
// 		browser(74596, "2016/9/1", "2016/9/6").then(
// 			result => {
// 				console.error(result)
// 				parse(result);
// 				callback(null, result);
// 			},
// 			error => {})
// 	},
// 	b: function(callback) {
// 		browser(74596, "2016/9/6", "2016/9/10").then(
// 			result => {
// 				console.error(result)
// 				parse(result);
// 				callback(null, result);
// 			},
// 			error => {})
// 	}
// }, function(err, results) {
// 	console.log(results);
// 	parse(results.a);
// });

// parse(pageContent);

// parse('./content.html');