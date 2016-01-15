var http = require('http'),
    qs = require('querystring');

var genReq = function(options) {
    console.log("URL: " + options.hostname + ":" + options.port + options.path);
    var req = http.request(options, function(res) {
        console.log('RESPONSE STATUS: ' + res.statusCode);
        console.log('RESPONSE HEADERS: ' + JSON.stringify(res.headers));

        res.setEncoding('utf8');

        var responseString = '';

        res.on('data', function(chunk) {
            responseString += chunk;
        });

        res.on('end', function() {
            // var resultObject = JSON.parse(responseString);
            console.log('-----Response Body-----', responseString);
        });
    });

    req.on('error', function(e) {
        console.log('problem with request: ' + e.message);
    });

    return req;
}

function get(data, options) {
    var content = qs.stringify(data);
    options.path = options.path + "?" + content;
    var req = genReq(options);
    req.end();
}

function post(body, options) {
    var bodyString = JSON.stringify(body);
    var req = genReq(options);

    // write data to request body
    req.write(bodyString);
    req.end();
}

module.exports = {
    get: get,
    post: post
}