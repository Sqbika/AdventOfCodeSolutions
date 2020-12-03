
console.log(require('fs').readFileSync("input.txt").toString().split('\n').map(line => {
    var [min, max, char, text] = line.match(/([0-9]*)-([0-9]*) ([a-zA-Z]): (\w*)/).slice(1,5);
    var left = text[min-1] == char;
    var right = text[max-1] == char;
    return (left || right) && !(left && right);
}).filter(x => x).length)