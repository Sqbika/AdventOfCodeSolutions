const fs = require('fs');

var data = fs.readFileSync('./input.txt').toString();

var types = {};

for (var i = 97; i < 123; i++) {
    console.log(i, "Trying Char:", String.fromCharCode(i), types[String.fromCharCode(i)] = react(data.replace(new RegExp(String.fromCharCode(i), 'ig'), '')));
}

console.log(types);

function react(the) {
    function sliceData(from) {
        the = the.slice(0, from) + the.slice(from + 2, the.length);
    }

    while (1) {
        var wasAction = false;
        for (var i = 1; i < the.length; i++) {
            if ((the[i-1].toLowerCase() == the[i] || the[i-1] == the[i].toLowerCase()) && the[i-1] !== the[i]) {
                sliceData(i-1);
                wasAction = true;
            }
        }
        if (!wasAction) {
            break;
        }
    }
    return the.length;

}