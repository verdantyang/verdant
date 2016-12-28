var fs = require('fs');
var assert = require("assert");
var fstools = require('../util-fs/file_copy');

describe('util-fs', function() {
	before(function() {});
	it("copy", function() {
		var fileName = "fsTemp";
		fs.writeFileSync(fileName, 'Hello Node.js', {
			'encoding': 'utf8'
		});
		fstools.smallCopy(fileName, fileName + "a");
		fstools.bigCopy(fileName, fileName + "b");
		fs.unlink(fileName + "b")
		fs.unlink(fileName + "a")
		fs.unlink(fileName)
	});
})