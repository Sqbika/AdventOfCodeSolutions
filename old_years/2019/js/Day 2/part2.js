const fs = require('fs');

//Init cause way of implementation
var data = [0];
var opcode = 0;

var data1Override = 12;
var data2Override = 2;

var index = 0;
var target = 19690720;

function readData(num) {
    return Number(data[num]);
}

function cleanData() {
    index = 0;
    data = fs.readFileSync('./input.txt').toString().split(',');
    opcode = readData(0);
    data[1] = data1Override;
    data[2] = data2Override;
}

function runCycle() {
    while (opcode != 99) {
        opcode = readData(index);
        if (opcode == 1) {
            data[readData(index + 3)] = readData(readData(index+1)) + readData(readData(index+2));
        } else if (opcode == 2) {
            data[readData(index + 3)] = readData(readData(index+1)) * readData(readData(index+2));
        }
        index += 4;
    }
}

function adjustData1AndTwo() {
    var diff = readData(0) - target;
    if (Number(data[0]) == target) {
        console.log("FOUND IT: " + data[1] + " / " + data[2]);
        console.log("R:" + readData(1) +""+ readData(2));
    }
    if (Math.abs(diff) < 100) {
        if (diff < 0) {
            if (data2Override + (-1 * diff) > 99) {
                data1Override += 1;
                data2Override = 1;
            } else {
                data2Override += (-1 * diff);
            }
        } else {
            if (data2Override - diff < 0) {
                data2Override -= (-1 * diff) + 99;
            }
        }
    } else {
        if (diff < 0) {
            data1Override++;
        } else {
            data2Override--;
        }
    }
}

while (readData(0) != target) {
    cleanData();
    runCycle();
    adjustData1AndTwo();
}