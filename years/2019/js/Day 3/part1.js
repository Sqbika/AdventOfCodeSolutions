const fs = require('fs');

var data = fs.readFileSync('./input.txt').toString().split('\n');

var line1 = data[0].split(',');
var line2 = data[1].split(',');

/*Array.prototype.add = function (target) {
    var that = [...this];
    for (var i = 0; i < target.length; i++) {
        if (that[i] == undefined) that[i] = 0;
        that[i] += target[i];
    }
    return that;
}

function parseDirs(line) {
    var result = [[0,0]];

    var latest = function () {
        return result[result.length-1];
    }

    line.forEach((dir) => {
        switch (dir[0]) {
            case "U": result.push(latest().add([0, Number(dir.substring(1, dir.length))])); break;
            case "D": result.push(latest().add([0, -1 * Number(dir.substring(1, dir.length))])); break;
            case "R": result.push(latest().add([Number(dir.substring(1, dir.length)), 0])); break;
            case "L": result.push(latest().add([-1 * Number(dir.substring(1, dir.length)), 0])); break;
        }
    });


    return result;
}

var wires1 = parseDirs(line1);
var wires2 = parseDirs(line2);
*/
//region Sane math solution [Doesn't work :(]
/*


searchForTheCross(wires1, wires2);

function searchForTheCross(line1, line2) {
    var crosses = [];
    var lowest = 99999999999999999999;
    for (var i = 0; i < line1.length-1; i++) {
        for (var j = 0; j < line2.length-1; j++) {
            var intersect = intersects(line1[i], line1[i+1], line2[j], line2[j+1]);
            if (intersect != false && intersect[0] != 0 && intersect[1] != 0) {
                crosses.push(intersect);
                if (distance(intersect) < lowest) lowest = distance(intersect);
                console.log(intersect);
            }
        }
    }
    console.log(lowest);
}

function distance(point) {
    return Math.abs(point[0]) + Math.abs(point[1]);
}

function intersects(line1a, line1b, line2a, line2b) {
      xdiff = [line1a[0] - line1b[0], line2a[0] - line2b[0]];
      ydiff = [line1a[1] - line1b[1], line2a[1] - line2b[1]];
      var det = function (a,b) {
          return a[0] * b[1] - a[1] * b[0];
      }

      var div = det(xdiff, ydiff);
      if (div == 0) return false;

      var d = [det(line1a, line1b), det(line2a, line2b)];
      return [det(d, xdiff) / div, det(d, ydiff) / div];
  }
*/
  ///endregion

  //region insane resource intensive solutions. Cause I hate myself.
var matrix = [];

/*for (var i = 0; i < wires1.length-1; i++) {
    if (wires1[i][0] == wires1[i+1][0]) {
        for (var j = wires1[i][1]; j < wires1[i+1][1]; j++) {
            if (matrix[wires1[i][0]] == undefined) {
                matrix[wires1[i][0]] = [];
            }
            matrix[wires1[i][0]][j] = 1;
        }
    } else {
        for (var j = wires1[i][0]; j < wires1[i+1][0]; j++) {
            if (matrix[wires1[i][1]] == undefined) {
                matrix[wires1[i][1]] = [];
            }
            matrix[wires1[i][1]][j] = 1;
        }
    }
}

var crosses = [];

for (var i = 0; i < wires2.length-1; i++) {
    if (wires2[i][0] == wires2[i+1][0]) {
        for (var j = wires2[i][1]; j < wires2[i+1][1]; j++) {
            if (matrix[wires2[i][0]] == undefined) {
                matrix[wires2[i][0]] = [];
            }
            if (matrix[wires2[i][0]][j] == 1) {
                crosses.push([wires2[i][0], j])
            }
        }
    } else {
        for (var j = wires2[i][0]; j < wires2[i+1][0]; j++) {
            if (matrix[wires2[i][1]] == undefined) {
                matrix[wires2[i][1]] = [];
            }
            if (matrix[wires2[i][1]][j] == 1) {
                crosses.push([wires2[i][1], j])
            }
        }
    }
}*/

//console.log(crosses);

//endregion

/*
var matrix = [];
var x = 0, y = 0;

line1.forEach((dir) => {
	var num = Number(dir.substring(1));
        switch (dir[0]) {
            case "U": 
		for (var i = y; i < y + num; i++) {
			if (matrix[x] == undefined) matrix[x] = [];
			matrix[x][i] = 1;
		}
		break;
            case "D": 
		for (var i = y; i > y - num; i--) {
			if(matrix[x] == undefined) matrix[x] = [];
			matrix[x][i] = 1;
		}
		break;
            case "R":
		for (var i = x; i < x + num; i++) {
			if (matrix[i] == undefined) matrix[i] = [];
			matrix[i][y] = 1;
		}
		break;
            case "L":
		for (var i = x; i > x - num; i--) {
			if (matrix[i] == undefined) matrix[i] = [];
			matrix[i][y] = 1;
		}
		break;
        }
    });
console.log(matrix.map(x => x.map(y => y == 1? 1 : 0).join('')).join('\n'));
*/

var lines1 = [];
var lines2 = [];

function fillLines(line, arr) {
	var x = 0;
	var y = 0;
	line.forEach(dir => {
		var num = Number(dir.substring(1));
		switch(dir[0]) {
			case "U":
				for (var i = y + num; y < i; y++) {
					arr.push(`${x},${y}`);
				} break;
			case "D":
				for (var i = y - num; y > i; y--) {
					arr.push(x + "," + y);
				} break;
			case "R":
				for (var i = x + num; x < i; x++) {
					arr.push(x + "," + y);
				} break;
			case "L":
				for (var i = x - num; x > i; x--) {
					arr.push(x + "," + y);
				} break;
		}
	});
}

fillLines(line1, lines1);
fillLines(line2, lines2);

console.log(lines1.length, lines2.length);
var filtered = lines1.filter(x => lines2.indexOf(x) != -1);
//console.log(lines1.filter(x => lines2.indexOf(x) != -1));

function dist(str) {
	var nums = str.split(',').map(x => Math.abs(Number(x)));
	return nums[0] + nums[1];
}

console.log(filtered.map(x => dist(x)));
