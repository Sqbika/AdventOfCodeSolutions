
console.log(require('fs').readFileSync("input.txt").toString().split('\n').map(line => {
    var [min, max, char, text] = line.match(/([0-9]*)-([0-9]*) ([a-zA-Z]): (\w*)/).slice(1,5);
    var left = text[min] == char;
    var right = text[max] == char;
    return (left || right) && !(left && right);
}).filter(x => x).length)