/*console.log(require('fs').readFileSync("input.txt").toString().split('\n').map(x => x.match(/([0-9]*)-([0-9]*) ([a-zA-Z]): (\w*)/)).map(line => line[4].replace(new RegExp(`[^${line[3]}]`, "g"), "").match(new RegExp(`${line[3]}{${line[1] + ',' + line[2]}}`, "g")) != null).filter(x => x).length)*/
//Failed "fancy" 1liner


console.log(require('fs').readFileSync("input.txt").toString().split('\n').map(line => {
    var [min, max, char, text] = line.match(/([0-9]*)-([0-9]*) ([a-zA-Z]): (\w*)/).slice(1,5);
    var cleanString = text.replace(new RegExp(`[^${char}]`, "g"), "");
    return cleanString.length >= min && cleanString.length <= max;
}).filter(x => x).length)