const fs = require('fs');

var data = fs.readFileSync('./input.txt').toString().split('\n');

var nodes = {};

function processData(data) {
    data.forEach(node => {
        var [from, to] = node.split(')');
        if (nodes[from] == undefined) {
            nodes[from] = {id: from, parent: [], children:[]};
        }
        if (nodes[to] == undefined) {
            nodes[to] = {id: to, parent: [], children:[]};
        }
        nodes[from].children.push(to);
        nodes[to].parent.push(from);
        //nodes[from].push(nodes[to]);
    });
}

function search(from, to, seenNodes) {
    if (seenNodes == undefined) seenNodes = [];
    var arr = nodes[from].children.concat(nodes[from].parent).filter(x => seenNodes.indexOf(x) == -1);
    seenNodes.push(from);
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == to) return [from, arr[i]];
        var srh = search(arr[i], to, seenNodes);
        if (srh != false) {
            return [from].concat(srh);
        }
    }
    return false;
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
K)L
K)YOU
I)SAN`;

/*processData(data);
console.log(count(nodes["COM"], 0));*/
//processData(test.split('\n'));
processData(data);
console.log(nodes);
console.log(search("YOU", "SAN").length -3);