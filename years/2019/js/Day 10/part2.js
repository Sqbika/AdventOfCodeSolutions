const fs = require('fs');

const testid = 4;

//get this from part1.js
var xpos = 26, ypos = 29;

if (true)
    var data = fs.readFileSync('./input.txt').toString().split('\n').map(x => x.split(''));
else
    var data = getTestData(testid, 11, 13);

var width = data[0].length,
    height = data.length;

class Asteroid {

    constructor(x,y) {
        this.x = x;
        this.y = y;
        // angle from center, not from asteroid
        this.angle = this.angle(xpos, ypos);
        this.dist = this.dist(xpos, ypos);
    }

    isPos(x,y) {
        return this.x == x && this.y == y;
    }

    dist(x,y) {
        return Math.abs(Math.abs(this.x) - Math.abs(x)) + Math.abs(Math.abs(this.y) - Math.abs(y));
    }

    angle(x,y) {
        var dx = x - this.x;
        var dy = this.y - y;
        var rad = Math.atan2(dy, dx);

        if (rad < 0)
            rad = Math.abs(rad);
        else
            rad = 2 * Math.PI - rad;

        return ((rad * (180/Math.PI) + 270) % 360);
    }
}

function part2() {
    var angles = {};
    var astcount = 0;
    for (var y = 0; y < height; y++) {
        for (var x = 0; x < width; x++) {
            if (x == xpos && y == ypos) continue;
            if (data[y][x] == "#") {
                var ast = new Asteroid(x,y);
                if (angles[ast.angle] == undefined) angles[ast.angle] = [];
                angles[ast.angle].push(ast);
                astcount++;
            }
        }
    }

    var sortedAngles = Object.keys(angles).sort((a,b) => a-b);

    sortedAngles.forEach(arr => {
        angles[arr].sort((a,b) => a.dist - b.dist);
    });

    var destList = [];
    var i = 0;
    while (destList.length < astcount) {
        if (angles[sortedAngles[i % sortedAngles.length]].length > 0)
        destList.push(angles[sortedAngles[i % sortedAngles.length]].shift());
        i++;
    }
    console.log(destList[199]);

    console.log(1);
}

function getTestData(num, xp, yp) {
    xpos = xp;
    ypos = yp;
    return fs.readFileSync("./test" + num + ".txt").toString().split("\n").map(x => x.split(''));
}

part2();
console.log("end");