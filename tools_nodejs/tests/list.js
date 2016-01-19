var httptools = require('../http/http-tools.js');

var data = {
    begin: new Date(2015, 1, 1).getTime(),
    end: new Date().getTime()
};

var options = {
    hostname: '192.168.112.152',
    port: 8080,
    path: '/cgpboss-portal-web/api/alarm/list',
    method: 'GET',
    headers: {
        'Cookie': 'JSESSIONID=9C594381D264EB8C5693E77A847CBA97',
        'Content-Type': 'application/json; charset=UTF-8'
    }
};

httptools.get(data, options);