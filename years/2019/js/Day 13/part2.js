const fs = require('fs');
const IntComp = require('../intcomp.js');

String.prototype.reverse = function () {
    return this.split('').reverse().join('');
}

class Game {

    constructor() {
        this.grid = [];
        this.comp = new IntComp(0);
        this.tiles = ['_', '▧', '■', '▬', '◉'];
        this.comp.data[0] = 2;
        this.points = 0;
    }

    cycle() {
        game.comp.runCycle();
        for (var i = 0; i < game.comp.outputs.length; i+=3) {
            var cmd = game.comp.outputs.slice(i, i+3);
            if (cmd[0] == -1 && cmd[1] == 0) {
                this.points = cmd[2];
            } else {
                if (this.grid[cmd[1]] == undefined) this.grid[cmd[1]] = [];
                this.grid[cmd[1]][cmd[0]] = cmd[2];
            }
        }
        var op = this.ourPos();
        var ball = this.findBall();
        this.comp.addInput(comparePos(op, ball));
    }

    renderGrid() {
        return this.grid.map(x => x.map(y => this.tiles[y]).join('')).join('\n');
    }

    ourPos() {
        return this.grid.find(x => x.indexOf(3) != -1).indexOf(3);
    }

    findBall() {
        return this.grid.find(x => x.indexOf(4) != -1).indexOf(4);
    }

    isBeated() {
        return this.comp.opcode == 99;
    }
}

var game = new Game();

if (true) {
    while (!game.isBeated()) {
        game.cycle();
        console.clear();
        console.log(game.renderGrid());
        console.log("Points: " + game.points);
        for (var j = 0; j < 20000000; j++) { 100+100}
    }
}

function comparePos(us,ball) {
    if (ball < us) return -1;
    if (us < ball) return 1;
    return 0; 
}