const fs = require('fs');

const testid = 3;

if (true)
    var data = fs.readFileSync('./input.txt').toString().split('\n').map(x => x.split(''));
else
    var data = getTestData(testid);

var width = data[0].length,
    height = data.length;

var asteroids = [];

class Asteroid {

    constructor(x,y) {
        this.x = x;
        this.y = y;
    }

    isPos(x,y) {
        return this.x == x && this.y == y;
    }

    angle(x,y) {
        var dx = x - this.x;
        var dy = this.y - y;
        var rad = Math.atan2(dy, dx);

        if (rad < 0)
            rad = Math.abs(rad);
        else
            rad = 2 * Math.PI - rad;

        return rad * (180/Math.PI);
    }
}

function part1() {
    for (var y = 0; y < height; y++) {
        for (var x = 0; x < width; x++) {
            if (data[y][x] == "#") {
                asteroids.push(new Asteroid(x,y));
            }
        }
    }

    var max = 0, xpos = 0, ypos = 0;

    for (var i = 0; i < asteroids.length; i++) {
        var angles = [];
        for (var j = 0; j < asteroids.length; j++) {
            var deg = asteroids[i].angle(asteroids[j].x, asteroids[j].y);
            if (angles.indexOf(deg) == -1) angles.push(deg);
        }
        if (angles.length > max) {
            max = angles.length;
            xpos = asteroids[i].x;
            ypos = asteroids[i].y;
        }
    }
    console.log(max, xpos, ypos);
}

function getTestData(num) {
    return fs.readFileSync("./test" + num + ".txt").toString().split("\n").map(x => x.split(''));
}

part1();
console.log("end");