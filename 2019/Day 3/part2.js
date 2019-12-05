const fs = require('fs');

var data = fs.readFileSync('./input.txt').toString().split('\n');

var line1 = data[0].split(',');
var line2 = data[1].split(',');

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
    return lines1.indexOf(str) + lines2.indexOf(str);
}

console.log(filtered.map(x => dist(x)));
