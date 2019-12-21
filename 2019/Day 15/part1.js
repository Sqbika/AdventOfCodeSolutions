const IntComp = require('../intcomp.js');

class Droid {

    constructor() {
        this.pos = {
            x: 0,
            y: 0,
        };
        this.grid = [["."]];
        this.render = ["#", ".", "O", "D"];
        this.dirs = [[0,0], [0, -1], [0, 1], [-1, 0], [1, 0]];
        this.reversedirs = [0, 2, 1, 4, 3];
        this.lastdir = -1;
        this.comp = new IntComp(0);
        this.minwidth = 0; this.maxwidth = 1;
        this.done = false;
        this.steps = 0;
    }

    addInput(int) {
        this.comp.addInput(int);
        this.lastdir = int;
    }

    moveDroidLocal(dir) {
        var move = this.dirs[dir];
        this.pos.y += move[1];
        this.pos.x += move[0];
        if (this.pos.x < this.minwidth) this.minwidth = this.pos.x;
        if (this.pos.x > this.maxwidth) this.maxwidth = this.pos.x;
    }

    getPosFromDir(dir) {
        return {
            x: this.pos.x + this.dirs[dir][0],
            y: this.pos.y + this.dirs[dir][1]
        }
    }

    run() {
        if (!this.done) {
            var dir = this.lastdir == -1 ? 1 : this.nextDirection();
            this.lastdir = dir;
            this.comp.addInput(dir);

            this.comp.runCycle();

            this.steps++;

            var out = Number(this.comp.getOutput());
            var targetPos = this.getPosFromDir(dir);

            if (this.grid[targetPos.y] == undefined) this.grid[targetPos.y] = [];
            this.grid[targetPos.y][targetPos.x] = this.render[out];

            if (targetPos.x < this.minwidth) this.minwidth = targetPos.x;
            if (targetPos.x > this.maxwidth) this.maxwidth = targetPos.x;

            if (out == 1)
                this.moveDroidLocal(this.lastdir);

            if (out == 2) {
                this.done = true;
                this.oxypos = {
                    x: this.pos.x,
                    y: this.pos.y
                }
                this.distanceFromStarting(this.oxypos.x, this.oxypos.y);
            }
        }
    }

    reverse() {
        return this.reversedirs[this.lastdir];
    }

    distanceFromStarting(x,y) {
        var queue = [[x,y, 0]]; 
        var seenpos = [x + ";" + y];
        while(true) {
            var que = queue.shift();
            var around = this.getAroundPos(que[0], que[1]);
            around = around.filter(x => (x.thing == '.' || x.thing == 'X') && seenpos.indexOf(x.x + ";" + x.y) == -1);
            var start = around.find(x => x.x == 0 && x.y == 0);
            if (start != undefined) {
                console.log("Part 1:" + Number(que[2]) + 1);
                this.done = true;
                return;
            } else {
                around.forEach(x => queue.push([x.x, x.y, Number(que[2])+1]));
                this.grid[que[1]][que[0]] = "C";
                this.renderGrid();
            }
        }
    }

    nextDirection() {
        var next = this.getAroundPos(this.pos.x, this.pos.y).filter(pos => {
            if (pos.pos == this.reverse()) return false;
            if (pos.thing == "#") return false;
            return true;
        });
        if (next.length == 0) return this.reverse();
        if (next.filter(pos => pos.thing == undefined).length != 0) {
            return next.filter(pos => pos.thing == undefined)[0].pos;
        }
        return next[0].pos;
    }

    getAroundPos(x,y) {
        return this.dirs.slice(1,5).map((dir, i) => {
            if (this.grid[y + dir[1]] == undefined) this.grid[y + dir[1]] = [];
            return {
                pos: i+1,
                thing: this.grid[y + dir[1]][x + dir[0]],
                x: x + dir[0],
                y: y + dir[1]
            }
        });
    }

    isDeadEnd(x,y) {
        return this.getAroundPos(x,y).filter(x => x.thing == '#').length == 3;
    }

    renderGrid() {
        var result = ["=".repeat(this.maxwidth-this.minwidth+3)];
        var yline = Object.keys(this.grid).sort((a,b) => a-b).map(Number);
        for (var y = yline[0]; y < yline[yline.length-1]+1; y++) {
            var line = this.grid[y];
            if (line == undefined) result.push(" ");
            else {
                var xline = "|";
                for (var x = this.minwidth; x < this.maxwidth+1; x++) {
                    if (x == this.pos.x && y == this.pos.y) xline += this.render[3];
                    else if (x == 0 && y == 0) xline += 'X';
                    else if (this.grid[y][x] == undefined) xline += " ";
                    else {
                        xline += this.grid[y][x];
                    }
                }
                result.push(xline + "|");
            }
        }
        console.clear();
        result.push("=".repeat(this.maxwidth-this.minwidth+3));
        console.log(result.join('\n'));
        console.log("Pos:" + this.pos.x + "/" + this.pos.y);
        console.log("Last dir: " + this.lastdir + " / " + this.dirs[this.lastdir]);
    }
}

var droid = new Droid();

async function part1() {
    while(!droid.done) {
        droid.run();
        if (droid.done) return;
        droid.renderGrid();
        await sleep(1000/60);
    }
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

part1();