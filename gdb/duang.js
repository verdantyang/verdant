var coroutine = require("coroutine");
function dang(n){
  while(true){
    console.log('DANG %d...', n);
    coroutine.sleep(1000);
  }
}
for(var i = 0; i < 5; i ++){
  coroutine.start(dang,i);
  coroutine.sleep(200);
}
while(true)
  coroutine.sleep(1000);
