const fs = require('fs');

if (true)
    var data = fs.readFileSync('./input.txt').toString().split('\n');
else
    var data = fs.readFileSync('./test0.txt').toString().split('\n');

const moonRegex = new RegExp('<x=([-0-9]*), y=([-0-9]*), z=([-0-9]*)>');

class Moon {

    constructor(posArr, id) {
        this.pos = {
            x: Number(posArr[0]),
            y: Number(posArr[1]),
            z: Number(posArr[2])
        };
        this.starting = {
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
        this.hist = [];
        this.velohist = [];
        this.cycles = 0;
        this.id = id;
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
        this.velohist.push({...this.velocity});
        Object.keys(this.pos).forEach(pos => {
            this.pos[pos] += Number(this.velocity[pos]);
        });
        this.hist.push({...this.pos});
        this.cycles++;
    }

    totalEnergy() {
        return Object.values(this.pos).reduce((a,b) => a += Math.abs(b), 0) *
                Object.values(this.velocity).reduce((a,b) => a+= Math.abs(b), 0);
    }
}

var moons = data.map((x,i) => new Moon(x.match(moonRegex).slice(1), i));


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

function gcd(a,b){
    var t = 0;
    a < b && (t = b, b = a, a = t); // swap them if a < b
    t = a%b;
    return t ? gcd(b,t) : b;
  }

  function lcm(a,b){
    return a/gcd(a,b)*b;
  }



function part1() {
    var i = 0;
    const dir = 'xyz'.split('');
    var LCM = {
        x: 0,
        y: 0,
        z: 0
    };
    while(true) {
        cycle();
        for (var j in dir) {
            var a = dir[j];
            if (moons.every(x => {
                return x.pos[a] == x.starting[a] && x.velocity[a] == 0;
            }) && LCM[a] == 0) {
                LCM[a] = i+1;
            }
        }
        if (LCM.x != 0 && LCM.y != 0 && LCM.z != 0) {
            console.log("LCM:" + BigInt(Object.values(LCM).reduce(lcm)).toString());
        }
        i++;
    }
}

part1();
