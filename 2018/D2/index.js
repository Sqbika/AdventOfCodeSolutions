const fs = require('fs');

var data = fs.readFileSync('./input.txt').toString().split('\n');

var sames = [];

data.forEach((e) => {
    data.forEach((f) => {
        if (e == f) return;
        var asd = 0;
        for (var i = 0; i < e.length; i++) {
            e[i] == f[i] ? asd += 0 : asd += 1;
        }
        if (asd == 1) sames.push( {
            "e": e,
            "f": f
        });
    });
});

console.log(sames);