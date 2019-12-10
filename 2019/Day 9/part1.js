const fs = require('fs');

String.prototype.reverse = function () {
    return this.split('').reverse().join('');
}

class IntCodeComp {
    
    constructor(indx) {
        this.indexHist = [];
        this.opcodeHist = [];
        this.indx = indx;
        this.index = 0;
        this.data = fs.readFileSync('./input.txt').toString().split(',');
        this.opcode = this.readData(0);
        this.outputs = [];
        this.inputs = [];
        this.cycles = {
            1: 4,
            2: 4,
            3: 2,
            4: 2,
            5: 3,
            6: 3,
            7: 4,
            8: 4,
            9: 2
        };
        this.relativeBase = 0;
    }

    addInput(num) {
        this.inputs.push(Number(num));
    }

    readData(num) {
        if (this.data[num] == undefined) this.data[num] = 0;
        return Number(this.data[num]);
    }

    set index(index) {
        this._index = Number(index);
        this.indexHist.push(index);
    }

    get index() {
        return Number(this._index);
    }

    get opcode() {
        return this._opcode;
    }

    set opcode(opcode) {
        this._opcode = opcode;
        this.opcodeHist.push(opcode);
    }

    cleanData() {
        this.index = 0;
        this.data = fs.readFileSync('./input.txt').toString().split(',');
        this.opcode = this.readData(0);
        this.outputs = [];
        this.inputs = [];
    }

    modeRead(arg, modes) {
        switch (modes[arg]) {
            case 1:
                return this.readData(this.index + arg + 1);
            case 0:
                return this.readData(this.readData(this.index + arg + 1));
            case 2:
                return this.readData(this.readData(this.index + arg + 1) + this.relativeBase);
        }
    }

    getOutput() {
        if (this.outputs.length == 0) return false;
        return this.outputs.shift();
    }

    readOutput() {
        return this.outputs[this.outputs.length-1];
    }

    runCycle() {
        cycle:
        while (this.opcode != 99) {
            this.opcode = this.readData(this.index);
            var modes = [0,0,0,0];
            if ((""+this.opcode).length > 2) {
                var addr = this.opcode+"";
                this.opcode = Number(addr.substring(addr.length-2));
                var mods = addr.substring(0, addr.length-2).reverse();
                for (var i = 0; i < mods.length; i++) {
                    modes[i] = Number(mods[i]);
                }
            }
            opcode:
            switch (this.opcode) {
                case 1:
                    this.data[this.readData(this.index+3)] = this.modeRead(0, modes) + this.modeRead(1, modes);
                    break opcode;
                case 2:
                    this.data[this.readData(this.index+3)] = this.modeRead(0, modes) * this.modeRead(1, modes);
                    break opcode;
                case 3:
                    //accumulator = modes[0] == 1 ? readData(index+1) : readData(readData(index+1));
                    if (this.inputs.length == 0) {
                        break cycle;
                    }
                    this.data[this.modeRead(0, modes)] = this.inputs.shift();
                    break opcode;
                case 4:
                    //console.log("O:" + this.modeRead(0, modes));
                    this.outputs.push(this.modeRead(0, modes));
                    break opcode;
                case 5:
                    if (this.modeRead(0, modes) != 0) {
                        this.index = this.modeRead(1, modes);
                        continue cycle;
                    }
                    break opcode;
                case 6:
                    if (this.modeRead(0, modes) == 0) {
                        this.index = this.modeRead(1, modes);
                        continue cycle;
                    }
                    break opcode;
                case 7:
                    this.data[this.readData(this.index+3)] = this.modeRead(0, modes) < this.modeRead(1, modes) ? 1 : 0;
                    break opcode;
                case 8:
                    this.data[this.readData(this.index+3)] = this.modeRead(0, modes) == this.modeRead(1, modes) ? 1 : 0;
                    break opcode;
                case 9:
                    this.relativeBase += this.modeRead(0, modes);
                    break opcode;
                case 99:
                    break cycle;
            }
            this.index += this.cycles[this.opcode];
        }
    
    }
}

function run() {
    var comp = new IntCodeComp(0);
    comp.addInput(1);
    comp.runCycle();
    console.log(comp.outputs);
}

function debug(inputData) {
    var comp = new IntCodeComp(0);
    comp.data = inputData.split(',');
    comp.opcode = comp.readData(0);
    comp.runCycle();
    console.log(comp.outputs[0]);
}

if (true)
    run();
else
    //debug("1102,34915192,34915192,7,4,7,99,0");
    //debug("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99");
    debug("104,1125899906842624,99");