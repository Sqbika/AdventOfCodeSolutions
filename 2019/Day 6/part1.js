const fs = require('fs');

var data = fs.readFileSync('./input.txt').toString().split('\n');

var nodes = {};

function processData(data) {
    data.forEach(node => {
        var [from, to] = node.split(')');
        if (nodes[from] == undefined) {
            nodes[from] = [];
        }
        if (nodes[to] == undefined) {
            nodes[to] = [];
        }
        nodes[from].push(nodes[to]);
    });
}

function count(node, depth) {
    var nodeCount = depth;
    depth++;
    return node.map(x => count(x, depth)).reduce((acc, cur) => acc += cur, nodeCount);
}

var test = `COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L`;

processData(data);
console.log(count(nodes["COM"], 0));