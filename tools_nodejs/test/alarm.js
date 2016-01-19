var httptools = require('../http/http-tools');
/*
线上环境：boss.cloudguarder.com（183.78.183.69:8081）
本地环境：192.168.112.152:8080

产品IP： 113.12.83.4
测试IP： 113.17.141.242
*/
var body = {
    "attackId": "d4b88ea0b07e487f8b59c4fb4edbf813",
    "trafficRate": 693.77696,
    "packetRate": 3.84267,
    "timestamp": 1451545070910,
    "startTime": "2015-01-19 11:21:17",
    "message": "test",
    "duration": 2640140,
    "customerId": 2,
    "attackType": "tcpsyn,udp,icmp",
    "alarmLevel": 2,
    "maxPacketRate": 4.53333,
    "alarmType": 1,
    "attackStatus": "ongoing",
    "dstIp": "113.17.141.242",
    "maxTrafficRate": 4.53333
};

var options = {
    hostname: '192.168.112.152',
    port: 8080,
    path: '/cgpboss-admin-web/api/attack/1.1.1.1/notice',
    // hostname: '183.78.183.69',
    // port: 8081,
    // path: '/api/attack/113.17.141.242/notice',
    method: 'POST',
    headers: {
        'Content-Type': 'application/json; charset=UTF-8'
    }
};

httptools.post(body, options);