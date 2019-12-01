const fs = require('fs');

var data = fs.readFileSync('./input.txt').toString().split('\n');

data = data.map((e) => {return {
    time: new Date(e.split(']')[0].replace('[', '') + ":00"),
    data: e.split('] ')[1]
}});

data.sort((a,b) => a.time - b.time);

function toMinutes(ms) {
    return Number(ms) / 60000;
}

var sleeps = {};

function fillArray(guard, start, end) {
    start = start.getMinutes();
    end = end.getMinutes();
    for (var i = start; i < end; i++) {
        sleeps[guard][i]++;
    }
}

var currGuard = 0;
var fallsAsleep;
data.forEach((e) => {
    if (e.data.startsWith('Guard')) {
        currGuard = Number(e.data.split('#')[1].split(' ')[0]);
        if (sleeps[currGuard] == undefined)
            sleeps[currGuard] = JSON.parse('[' + "0,".repeat(59) + '0]');
    }

    if (e.data == "falls asleep") {
        fallsAsleep = e.time;
    }

    if (e.data == "wakes up") {
        fillArray(currGuard, fallsAsleep, e.time);
    }
});

var most = 0, mostWho, mostMinute;
for (var i = 0; i < 60; i++) {
    var bad = false;
    var max = 0;
    var whoMax;
    Object.keys(sleeps).forEach((j) => {
        if (max == sleeps[j][i])
            bad = true;
        if (max < sleeps[j][i]) {
            max = sleeps[j][i];
            bad = false;
            whoMax = j;
        } 
    });
    //console.log(i, Object.keys(sleeps).map((e) => sleeps[e][i]), max, bad);
    if (!bad) {
        if (most < max) {
            most = max;
            mostMinute = i;
            mostWho = whoMax;
        }
    }
}
console.log(mostMinute * mostWho);