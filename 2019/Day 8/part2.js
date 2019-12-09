const fs = require('fs');

var data = fs.readFileSync('./input.txt').toString();

var dimx = 25, dimy = 6;

var layers = [];

for (var i = 0; i < data.length; i += (dimx * dimy)) {
    layers.push(data.substring(i, i + (dimx*dimy)).split(''));
}

function flatten(layers) {
    //Our layers are dimx * dimy long, so the end result is dimx * dimy too
    var resultImage = new Array(dimx * dimy).fill(2);
    //Colors -> 0: Black, 1: White, 2: Transparent
    for (var i = 0; i < dimx * dimy; i++) {
        for (var j = 0; j < layers.length; j++) {
            if (layers[j][i] != "2") {
                resultImage[i] =  layers[j][i];
                break;
            }
        }
    }
    return resultImage;
}

function debug (layers) {
    var debugImage = [];
    for (var i = 0; i < dimx * dimy; i++) {
        for (var j = 0; j < layers.length; j++) {
            if (debugImage[i] == undefined) debugImage[i] = "";
            console.log(layers[j][i]);
            debugImage[i] += layers[j][i]; 
        }
    }
    for (var j = 0; j < debugImage.length; j++) {
        var firstNonTwo = "";
        for (var i = 0; i < debugImage[j].length; i++) {
            if (debugImage[j][i] != "2") {
                firstNonTwo = debugImage[j][i];
                break;
            }
        }
        debugImage[j] += " - " + firstNonTwo;
    }
    return debugImage;
}

//console.log(layers[0][0]);
//console.log(debug(layers));
var result = flatten(layers);
var img = [];

for (var i = 0; i < result.length; i+= dimx) {
    img.push(result.join('').substring(i, i+dimx).replace(new RegExp("0", 'g'), ".").replace(new RegExp("1", 'g'), "B"));
}
console.log(img.join('\n'));
//console.log(flatten(layers).join(''));

