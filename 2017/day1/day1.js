const fs = require('fs');
const input = fs.readFileSync("day1/input.txt").toString().split("");

var result = 0;

for (var i = 0; i < input.length; i++) {
  if (input[i] == input[(i+1) % input.length])
      result += Number(input[i]);
}

console.log(result);
