var head = require('./head');
var body = require('./body');

function main(argv) {
	copy(argv[0], argv[1]);
}

main(process.argv.slice(2));
 
exports.create = function (name) {
    return {
        name: name,
        head: head.create(),
        body: body.create()
    };
};