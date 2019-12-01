const fs = require('fs');

var data = fs.readFileSync('./input.txt').toString();

function sliceData(from) {
    data = data.slice(0, from) + data.slice(from + 2, data.length);
}

console.log(data.length);

while (1) {
    var wasAction = false;
    for (var i = 1; i < data.length; i++) {
        if ((data[i-1].toLowerCase() == data[i] || data[i-1] == data[i].toLowerCase()) && data[i-1] !== data[i]) {
            sliceData(i-1);
            wasAction = true;
        }
    }
    //console.log(data.length);
    if (!wasAction) {
        break;
    }
}

console.log(data.length);