#!/bin/node
const fs = require('fs');

var data = fs.readFileSync('./input.txt').toString().split('\n');


function calcFuel(input) {
    input = Number(input);
    if (input <= 0) return 0;
    return Math.max(Math.floor(input / 3) -2 + calcFuel(Math.floor(input / 3) -2), 0);
}

console.log(data.reduce((prev, cur, ind, arr) => prev + calcFuel(cur), 0));