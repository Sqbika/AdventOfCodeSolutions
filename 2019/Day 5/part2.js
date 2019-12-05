const fs = require('fs');

//Init cause way of implementation
var data = [0];
var opcode = 0;
var accumulator = 0;

var index = 0;

/**The diagnostic input */
var input = 5;

var cycles = {
    1: 4,
    2: 4,
    3: 2,
    4: 2,
    5: 3,
    6: 3,
    7: 4,
    8: 4
};

String.prototype.reverse = function () {
    return this.split('').reverse().join('');
}

function readData(num) {
    return Number(data[num]);
}

function cleanData() {
    index = 0;
    data = fs.readFileSync('./input.txt').toString().split(',');
    opcode = readData(0);
}

function modeRead(index, arg, modes) {
    return modes[arg] == 1 ? readData(index+arg+1) : readData(readData(index+arg+1));
}

function runCycle() {
    cycle:
    while (opcode != 99) {
        opcode = readData(index);
        var modes = [0,0,0,0];
        if ((""+opcode).length > 2) {
            var addr = opcode+"";
            opcode = Number(addr.substring(addr.length-2));
            var mods = addr.substring(0, addr.length-2).reverse();
            for (var i = 0; i < mods.length; i++) {
                modes[i] = Number(mods[i]);
            }
        }
        opcode:
        switch (opcode) {
            case 1:
                data[readData(index+3)] = modeRead(index, 0, modes) + modeRead(index, 1, modes);
                break opcode;
            case 2:
                data[readData(index+3)] = modeRead(index, 0, modes) * modeRead(index, 1, modes);
                break opcode;
            case 3:
                //accumulator = modes[0] == 1 ? readData(index+1) : readData(readData(index+1));
                data[readData(index+1)] = input;
                break opcode;
            case 4:
                console.log("O:" + data[readData(index+1)]);
                break opcode;
            case 5:
                if (modeRead(index, 0, modes) != 0) {
                    index = modeRead(index, 1, modes);
                    continue cycle;
                }
                break opcode;
            case 6:
                if (modeRead(index, 0, modes) == 0) {
                    index = modeRead(index, 1, modes);
                    continue cycle;
                }
                break opcode;
            case 7:
                data[readData(index+3)] = modeRead(index, 0, modes) < modeRead(index, 1, modes) ? 1 : 0;
                break opcode;
            case 8:
                data[readData(index+3)] = modeRead(index, 0, modes) == modeRead(index, 1, modes) ? 1 : 0;
                break opcode;
        }
        index += cycles[opcode];
    }
}

cleanData();
runCycle();