const fs = require('fs');

var data = fs.readFileSync('./input.txt').toString().split('\n');

var grid = JSON.parse("[" + "[],".repeat("999") + "[]]");

var num = {};

data.forEach((e) => parseLine(e));

console.log(Object.values(num).length);

function parseLine(line) {
    var parts = line.split(' ');
    var x = Number(parts[2].split(',')[0]),
        y = Number(parts[2].split(',')[1].replace(':', '')),
        incx = Number(parts[3].split('x')[0]),
        incy = Number(parts[3].split('x')[1]);
    
    for (var i = x; i < x + incx; i++) {
        for (var j = y; j < y + incy; j++) {
            grid[i][j] == undefined ?
                grid[i][j] = 1 :
                num[i + "_" + j] = 1;
        }
    }
}