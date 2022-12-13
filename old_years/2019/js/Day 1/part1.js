#!/bin/node
const fs = require('fs');

var data = fs.readFileSync('./input.txt').toString().split('\n');


function calcFuel(input) {
    input = Number(input);
    return Math.floor(input / 3) -2;
}

console.log(data.reduce((prev, cur, ind, arr) => prev + calcFuel(cur), 0));