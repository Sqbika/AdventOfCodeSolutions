const fs = require('fs');

var data = fs.readFileSync('./input.txt').toString().split(',');

var opcode = readData(0);

function readData(num) {
    return Number(data[num]);
}

data[1] = 12;
data[2] = 2;

var index = 0;
console.log(data.length);

while (opcode != 99) {
    opcode = readData(index);
    if (opcode == 1) {
        data[readData(index + 3)] = readData(readData(index+1)) + readData(readData(index+2));
    } else if (opcode == 2) {
        data[readData(index + 3)] = readData(readData(index+1)) * readData(readData(index+2));
    }
    index += 4;
}

console.log(data[0]);