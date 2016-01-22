var http = require('http'),
    url = require('url');

var requestNatvie = require('../util-http/request_native'),
    requestThird = require('../util-http/request_ext');

describe('util-http', function() {
    var svr;
    before(function() {
        svr = http.createServer(function(req, res) {
            var pathname = url.parse(req.url).pathname;
            if ("/json" === pathname) {
                res.writeHead(200);
                res.end(JSON.stringify({
                    "cont": "hello"
                }));
            }
            if ("/hello" === pathname) {
                res.writeHead(200);
                res.end("Hello World!\n");
            }
        })
        svr.on("error", function(error) {
            console.log(error);
        });
        svr.on("close", function(error) {
            console.log("Server closed!");
        });
        svr.listen(8081);
    });

    after(function() {
        svr.close();
    });

    it("GET", function() {
        var data = {
        };

        var options = {
            hostname: '127.0.0.1',
            port: 8081,
            path: '/hello',
            method: 'GET',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8'
            }
        };

        requestNatvie.get(options, data);
        requestThird.get(options, data);
    })

    it("POST", function() {
        var body = {
            "attackId": "d4b88ea0b07e487f8b59c4fb4edbf813",
            "trafficRate": 693.77696
        };

        var options = {
            hostname: '127.0.0.1',
            port: 8081,
            path: '/json',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8'
            }
        };

        requestNatvie.post(options, body);
        requestThird.post(options, body);
    })
    it("wait", function() {})
})
