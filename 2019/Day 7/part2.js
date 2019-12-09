const fs = require('fs');

function permutator (inputArr) {
    let result = [];
  
    const permute = (arr, m = []) => {
      if (arr.length === 0) {
        result.push(m)
      } else {
        for (let i = 0; i < arr.length; i++) {
          let curr = arr.slice();
          let next = curr.splice(i, 1);
          permute(curr.slice(), m.concat(next))
       }
     }
   }
  
   permute(inputArr)
  
   return result;
}

String.prototype.reverse = function () {
    return this.split('').reverse().join('');
}

class IntCodeComp {
    
    constructor(indx) {
        this.indexHist = [];
        this.indx = indx;
        this.index = 0;
        this.data = fs.readFileSync('./input.txt').toString().split(',');
        this.opcode = this.readData(0);
        this.lastOutput = 0;
        this.inputs = [];
        this.cycles = {
            1: 4,
            2: 4,
            3: 2,
            4: 2,
            5: 3,
            6: 3,
            7: 4,
            8: 4
        };
    }

    addInput(num) {
        this.inputs.push(Number(num));
    }

    readData(num) {
        return Number(this.data[num]);
    }

    set index(index) {
        this._index = Number(index);
        this.indexHist.push(index);
    }

    get index() {
        return Number(this._index);
    }

    cleanData() {
        this.index = 0;
        this.data = fs.readFileSync('./input.txt').toString().split(',');
        this.opcode = this.readData(0);
        this.lastOutput = 0;
        this.inputs = [];
    }

    modeRead(arg, modes) {
        return modes[arg] == 1 ? this.readData(this.index + arg + 1) : this.readData(this.readData(this.index + arg + 1));
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
                    this.data[this.readData(this.index+2)] = this.modeRead(0, modes) + this.modeRead(1, modes);
                    break opcode;
                case 2:
                    this.data[this.readData(this.index+2)] = this.modeRead(0, modes) * this.modeRead(1, modes);
                    break opcode;
                case 3:
                    //accumulator = modes[0] == 1 ? readData(index+1) : readData(readData(index+1));
                    if (this.inputs.length == 0) {
                        break cycle;
                    }
                    this.data[this.readData(this.index+1)] = this.inputs.shift();
                    break opcode;
                case 4:
                    console.log("O:" + this.modeRead(0, modes));
                    this.lastOutput = this.modeRead(0, modes);
                    break cycle;
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
                    this.data[this.readData(this.index+2)] = this.modeRead(0, modes) < this.modeRead(1, modes) ? 1 : 0;
                    break opcode;
                case 8:
                    this.data[this.readData(this.index+2)] = this.modeRead(0, modes) == this.modeRead(1, modes) ? 1 : 0;
                    break opcode;
                case 99:
                    break cycle;
            }
            this.index += this.cycles[this.opcode];
        }
    
    }
}

var phases = permutator([5,6,7,8,9]);

var comps = [];

const numOfAmps = 5;

for (var i = 0; i < numOfAmps; i++) {
    comps.push(new IntCodeComp(i));
}

function tryPhases() {
    var max = 0;
    phases.forEach(phase => {
        phase.forEach((input, i) => {
            comps[i].addInput(input);
        });
        while(comps[numOfAmps-1].opcode != 99) {
            for (var i = 0; i < numOfAmps; i++) {
                comps[i].runCycle();
                comps[(i+1) % numOfAmps].addInput(comps[i].lastOutput);
            }
            console.log(comps[numOfAmps-1].opcode);
        }

        if (comps[numOfAmps-1].lastOutput > max) max = comps[numOfAmps-1].lastOutput;
        comps.forEach(comp => comp.cleanData());
        console.log("P:" + phase.join(',') + " Complete.");
    });
    console.log(max);
}

function debug(codes, innerData) {
    var innerComps = [];
    for (var i = 0; i < 5; i++) {
        var comp = new IntCodeComp(i);
        comp.data = new Array(...innerData);
        comp.addInput(codes[i]);
        if (i == 0) comp.addInput(0);
        comp.opcode = comp.readData(0);
        innerComps.push(comp);
    }
    while(innerComps[4].opcode != 99) {
        for (var i = 0; i < 5; i++) {
            innerComps[i].runCycle();
            if (innerComps[4].opcode == 99) {
                break;
            }
            innerComps[(i+1) % 5].addInput(innerComps[i].lastOutput);
        }
        console.log(innerComps[4].opcode);
    }
}




/*cleanData();
runCycle();*/
/*
if (!true)
    tryPhases();
else
    debug([9,7,8,5,6], `3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,
    -5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,
    53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10`.split(','));*/

    debug([9,8,7,6,5], `3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,
    27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5`.split(','));