const fs = require('fs');

var data = fs.readFileSync('./input.txt').toString();

var dimx = 25, dimy = 6;

var layers = [];

for (var i = 0; i < data.length; i += (dimx * dimy)) {
    layers.push(data.substring(i, i + (dimx*dimy)).split(''));
}

function findFewestZeros(layers) {
    var min = dimx * dimy + 1;
    var minIndx = -1; //-1 cause we start from index 0
    for (var i = 0; i < layers.length; i++) {
        var zeroCount = layers[i].filter(x => x == "0").length;
        if (zeroCount < min) {
            min = zeroCount;
            minIndx = i;
        }
    }
    return minIndx;
}

function multCountOneAndCountTwo(layer) {
    return layer.filter(x => x == "1").length * layer.filter(x => x == "2").length;
}

console.log(multCountOneAndCountTwo(layers[findFewestZeros(layers)]));