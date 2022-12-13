const fs = require('fs');
const IntComp = require('../intcomp.js');

String.prototype.reverse = function () {
    return this.split('').reverse().join('');
}

class Game {

    constructor() {
        this.grid = [];
        this.comp = new IntComp(0);
    }

    run() {
        game.comp.runCycle();
        for (var i = 0; i < game.comp.outputs.length; i+=3) {
            var cmd = game.comp.outputs.slice(i, i+3);
            if (this.grid[cmd[1]] == undefined) this.grid[cmd[1]] = [];
            this.grid[cmd[1]][cmd[0]] = cmd[2];
        }
    }
}

var game = new Game();

if (true) {
    game.run();
    console.log("Part 1:" + game.grid.map((arr) => arr.filter(x => x == 2).length).reduce((a,b) => a += b, 0));
    console.log(1);
}