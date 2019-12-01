var fs = require('fs');

var data = fs.readFileSync('./input.txt').toString().split('\n');

var a = 0;
var b = {
    '0': 1
};
var c = [0];

for (var i = 0; i < 20000; i++) {
data.forEach((e) => {
    var num = Number(e);
    a += num;
    b[a] == undefined ? 
        b[a] = 1 :
        b[a] += 1;
    if (b[a] == 2) console.log(a);
});
}

//console.log(c.map((e) => e + " -> " + b[e]).join('\n'));