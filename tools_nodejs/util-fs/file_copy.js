var fs = require('fs');

/**
 * @describe File Copy (from src to dst)
 * @param src
 * @param dst
 * @return
 **/
function smallFileCopy(src, dst) {
	fs.writeFileSync(dst, fs.readFileSync(src));
}

function bigFileCopy_1(src, dst) {
	fs.createReadStream(src).pipe(fs.createWriteStream(dst));
}

function bigFileCopy_2(src, dst) {
	var rs = fs.createReadStream(src);
	var ws = fs.createWriteStream(dst);

	rs.on('data', function(chunk) {
		if (ws.write(chunk) === false) {
			rs.pause();
		}
	});

	rs.on('end', function() {
		ws.end();
	});

	ws.on('drain', function() {
		rs.resume();
	});
}

module.exports = {
    smallCopy: smallFileCopy,
    bigCopy: bigFileCopy_1
}