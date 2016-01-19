var fs = require('fs');

var fstools = require('../fs/file-copy');

fstools.smallCopy("./test.js", "./test1.js");
fstools.bigCopy("./test.js", "./test2.js");
fs.unlink("./test1.js")
fs.unlink("./test2.js")