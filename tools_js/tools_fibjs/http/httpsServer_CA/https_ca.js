var crypto = require("crypto"),
	hash = require("hash"),
	assert = require("assert"),
	http = require("http"),
	ssl = require("ssl"),
	fs = require("fs");

var ca_pk = new crypto.PKey();
ca_pk.genRsaKey(1024);
var svr_pk = new crypto.PKey();
svr_pk.genRsaKey(1024);

/**
 * @subject: 证书的主题可分辨名称(X509Req构造函数使用)
 * @issuer: 签名机构的可分辨名称(sign使用)
 * C是国家代码
 * O是机构组织
 * CN是名字
 **/

//签发CA
var ca_req = new crypto.X509Req("C=CN, O=named", ca_pk);
var ca = ca_req.sign("C=CN, O=named", ca_pk, {
	ca: true
});
// console.error(ca.dump())

//使用CA签发服务器端证书
var svr_req = new crypto.X509Req("CN=localhost", svr_pk);
var svr_cert = svr_req.sign("C=CN, O=named", ca_pk);

//使用服务器证书和私钥启动 https 服务器（服务端）
var svr = new http.HttpsServer(svr_cert, svr_pk, 8883, function(r) {
	r.response.body.write(r.address);
	r.body.copyTo(r.response.body);
});
svr.asyncRun();

//导出CA供客户端使用
fs.writeFile("cert.pem", ca.dump()[0]);

//正确加载证书的请求（客户端）
var ca_pem = new crypto.X509Cert();
ca_pem.loadFile('cert.pem');
ssl.ca.load(ca_pem.dump()[0]);
assert.equal(http.request("GET", "https://localhost:8883/request:", "body").body.read().toString(),
	"/request:body");

//未加载证书的请求（客户端）
ssl.ca.clear();
assert.throws(function() {
	http.request("GET", "https://localhost:8883/request:", "body").body.read().toString();
});
if (fs.exists('cert_pem'))
	fs.unlink('cert.pem');