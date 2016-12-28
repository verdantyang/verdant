return new Promise((resolve, reject) => {
	request.post('http://kaoqin.geely.auto', {
		form: formData
	}, (error, response, body) => {
		if (error) {
			console.log('error|request_ext|', error)
			reject(error);
		}
		if (!error && response.statusCode == 200) {
			console.log('success|request');
			htmlpage = body;
			resolve(htmlpage);
		}
	})
});
funcName(…).then(result=>{},error=>{})


async.series(
	[(callback) =>
		request.post('http://kaoqin.geely.auto', {
			form: formData
		}, (error, response, body) => {
			if (error)
				console.log('error|request_ext|', error)
			if (!error && response.statusCode == 200) {
				console.log('success|request');
				htmlpage = body;
			}
			// console.log(body);
			callback(null, body)
		})
	],
	(err, results) => {
		htmlpage = results[0];
		// console.log(results[0]);
		return htmlpage
	}
);


async() => {
	let html = await browser(...)
}