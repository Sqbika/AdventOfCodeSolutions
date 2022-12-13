const fs = require('fs');

if (true)
    var data = fs.readFileSync('./input.txt').toString().split('\n');
else
    var data = fs.readFileSync('./test0.txt').toString().split('\n');

const moonRegex = new RegExp('<x=([-0-9]*), y=([-0-9]*), z=([-0-9]*)>');

class Moon {

    constructor(posArr) {
        this.pos = {
            x: Number(posArr[0]),
            y: Number(posArr[1]),
            z: Number(posArr[2])
        };
        this.velocity = {
            x: 0,
            y: 0,
            z: 0
        };
        this.dir = Object.keys(this.pos);
    }

    incVelo (pos, amount) {
        this.velocity[pos] = Number(this.velocity[pos]) + Number(amount);
    }

    applyGravity(moon) {
        for (var i = 0; i < this.dir.length; i++) {
            var dir = this.dir[i];
            if (this.pos[dir] == moon.pos[dir]) continue;
            if (this.pos[dir] > moon.pos[dir]) {
                this.incVelo(dir, -1);
                moon.incVelo(dir, 1);
            } else {
                this.incVelo(dir, 1);
                moon.incVelo(dir, -1);
            }
        }
    }

    applyVelocity() {
        Object.keys(this.pos).forEach(pos => {
            this.pos[pos] += Number(this.velocity[pos]);
        });
    }

    totalEnergy() {
        return Object.values(this.pos).reduce((a,b) => a += Math.abs(b), 0) *
                Object.values(this.velocity).reduce((a,b) => a+= Math.abs(b), 0);
    }
}

var moons = data.map(x => new Moon(x.match(moonRegex).slice(1)));


function iterateGravity() {
    for (var i = 0; i < moons.length-1; i++) {
        for (var j = i+1; j < moons.length; j++) {
            moons[i].applyGravity(moons[j]);
        }
    }
}

function cycle() {
    iterateGravity();
    moons.forEach(moon => moon.applyVelocity());
}


function part1() {
    var i = 0;
    while(true) {
        if (i == 1000) break;
        cycle();
        i++;
    }
    console.log("Part1: " + moons.reduce((a,b) => a+= b.totalEnergy(), 0));
}

part1();
