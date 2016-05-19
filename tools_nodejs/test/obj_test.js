function Foo() {}
var foo = new Foo();
// console.log(window.JSON)
console.log(foo.__proto__) // function Foo(){}
console.log(foo.constructor) // function Foo(){}


// var carObj = {
//     color: ['red']
// };
// var carObj1 = clone(carObj);
// carObj.color.push('blue');