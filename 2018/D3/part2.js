
const fs = require('fs');

var data = fs.readFileSync('./input.txt').toString().split('\n');

var grid = JSON.parse("[" + "[],".repeat("999") + "[]]");

var b = data.map((e) => parseLine(e));

for (var i = 0; i < b.length; i++) {
    var busted = false;
    for (var j = 0; j < b.length; j++) {
        if (i == j) continue;
        if (intersects(b[i], b[j])) {
            busted = true;
            break;
        }
    }
    if (!busted) {
        console.log(i);
        process.exit(1);
    }
}   

function parseLine(line) {
    var parts = line.split(' ');
    var x = Number(parts[2].split(',')[0]),
        y = Number(parts[2].split(',')[1].replace(':', '')),
        incx = Number(parts[3].split('x')[0]),
        incy = Number(parts[3].split('x')[1]);
    
    return {top: {
        x: x,
        y: y,
    }, bottom: {
        x: incx + x,
        y: incy + y
    }};
}

function intersects({top: aleft,bottom: aright}, {top: bleft ,bottom: bright }) {
    if (aleft.x > bright.x || bleft.x > aright.x)
        return false;
    if (aleft.y > bright.y || bleft.y > aright.y)
        return false;
    return true;
}