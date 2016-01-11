var httptools = require('../http/http-tools');

var body = {
    "attackId": "d4b88ea0b07e487f8b59c4fb4edbf807",
    "trafficRate": 693.77696,
    "packetRate": 3.84267,
    "timestamp": 1451545070910,
    "startTime": "2015-12-29 11:21:17",
    "endTime": "2015-12-29 11:23:17",
    "message": "test",
    "duration": 2640140,
    "customerId": 2,
    "attackType": "tcpsyn,udp,icmp",
    "alarmLevel": 2,
    "maxPacketRate": 4.53333,
    "alarmType": 1,
    "attackStatus": "done",
    "dstIp": "33.33.33.33",
    "maxTrafficRate": 4.53333
};

var options = {
    hostname: '192.168.112.152',
    port: 8080,
    path: '/cgpboss-admin-web/api/attack/113.12.83.4/notice',
    method: 'POST',
    headers: {
        'Content-Type': 'application/json; charset=UTF-8'
    }
};

httptools.post(body, options);