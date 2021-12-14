const fs = require('fs');
const IntComp = require('../intcomp.js');

String.prototype.reverse = function () {
    return this.split('').reverse().join('');
}

class Painter {

    constructor(custData) {
        this.comp = new IntComp(0);
        if (custData != undefined)
            data = custData;
        this.comp.opcode = this.comp.readData(0);
        this.grid = [];
        this.pos = {
            x: 0,
            y: 0
        };
        this.paintCount = 0;
        this.paintHist = [];
        this.curDir = 0;
        this.posChange = [{x:0, y:-1}, {x:-1, y:0}, {x:0, y:1}, {x:1, y:0}];
    }

    turn(output) {
        if (output == 1) {
            this.curDir--;
        } else {
            this.curDir++;
        }
        if (this.curDir < 0) this.curDir += 4;
        this.curDir %= 4;
    }

    formatPos(x,y) {
        return x+";"+y;
    }

    move() {
        this.addPos(this.posChange[this.curDir]);
    }

    addPos(vec) {
        this.pos['x'] += vec.x;
        this.pos['y'] += vec.y;
    }

    readPos(x,y) {
        if (this.grid[y] == undefined) this.grid[y] = [];
        if (this.grid[y][x] == undefined) this.grid[y][x] = ".";
        return this.grid[y][x] == "." ? 0 : 1;
    }

    paint(x,y,num) {
        if (this.grid[y] == undefined) this.grid[y] = [];
        if (this.grid[y][x] == undefined) this.grid[y][x] = ".";
        this.grid[y][x] = num == 0 ? "." : "#";
        if (this.paintHist.indexOf(this.formatPos(x,y)) == -1) this.paintHist.push(this.formatPos(x,y));
    }

    run() {
        while (this.comp.opcode != 99) {
            var pos = this.readPos(this.pos.x, this.pos.y);
            this.comp.addInput(pos);
            this.comp.runCycle();
            if (this.comp.opcode == 99) break;
            var paintNum = this.comp.getOutput();
            var dir = this.comp.getOutput();
            this.paint(this.pos.x, this.pos.y, paintNum);
            this.turn(dir);
            this.move();
        }
    }
}

if (true) {
    var robo = new Painter();
    robo.run();
    console.log("Part1:" + robo.paintHist.length);
    var robo = new Painter();
    robo.grid[0] = ["#"];
    robo.run();
    console.log("Part2:");
    var gridKeys = Object.keys(robo.grid).map(Number).sort((a,b) => a-b);
    var ymin = Math.min(...gridKeys), 
        ymax = Math.max(...gridKeys),
        xmin = 10000000,
        xmax = 0;
    for (var i = 0; i < gridKeys.length; i++) {
        var gridInnerKeys = Object.keys(robo.grid[gridKeys[i]]).map(Number);
        var innerMin = Math.min(...gridInnerKeys);
        var innerMax = Math.max(...gridInnerKeys);
        if (innerMin < xmin) xmin = innerMin;
        if (innerMax > xmax) xmax = innerMax;
    }

    var result = [];

    for (var i = ymin; i < ymax+1; i++) {
        var innerResult = "";
        if (robo.grid[i] == undefined) robo.grid[i] = [];
        for (var j = xmin; j < xmax; j++) {
            if (robo.grid[i][j] == "#") innerResult += "█";
            else innerResult += "░";
        }
        result.push(innerResult);
    }
    console.log(result.join("\n"));
}

