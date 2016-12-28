// var rs=fs.createReadStream('./content.txt');
// rs.setEncoding('utf8');
// var data='';
// rs.on('data',function(trunk){
//     data+=trunk;
// })
// rs.on('end',function(){
//     console.log(data);
// })

// var content = fs.readFileSync('./content.txt', function(err, data) {
// 	if (err) throw err;
// 	htmlpage = data.toString("utf8");
// });

var content = fs.readFileSync('./content.txt');
htmlpage = content.toString('utf8');