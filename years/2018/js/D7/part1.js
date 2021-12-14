const fs = require('fs');

var reg = new RegExp('Step (.*) must be finished before step (.*) can begin.');

var data = fs.readFileSync('./input.txt').toString();

/**
 * 
 * @param {String} data 
 */
function readData(data) {
    return data.split('\n').map(x => reg.exec(x).slice(1,3))
}

var nodes = {};

/**
 * 
 * @param {Any[][]} data 
 */
function processData(data) {
    data.forEach(([from, to]) => {
        if (nodes[from] == undefined) nodes[from] = {id: from, parent: [], children: []};
        if (nodes[to] == undefined) nodes[to] = {id: to, parent: [], children: []};
        nodes[from].children.push(to);
        nodes[to].parent.push(from);
    });
    var queue = findStarts(nodes).sort((a,b) => a.localeCompare(b));
    var done = [];
    while (queue.length != 0) {
        var curr = queue[0];
        done.push(curr);
        queue = queue.slice(1).concat(nodes[curr].children.filter(x => queue.indexOf(x) == -1 && done.indexOf(x) == -1 && (nodes[x].parent.every(par => done.indexOf(par) != -1) || nodes[x].parent.length == 0)));
        queue = queue.sort((a,b) => a.localeCompare(b));
    }
    return done;
}

/**
 * 
 * @param {Object} data 
 */
function findStarts(data) {
    return Object.keys(data).filter(x => nodes[x].parent.length == 0);
}

var test = `Step C must be finished before step A can begin.
Step C must be finished before step F can begin.
Step A must be finished before step B can begin.
Step A must be finished before step D can begin.
Step B must be finished before step E can begin.
Step D must be finished before step E can begin.
Step F must be finished before step E can begin.`;


console.log(processData(readData(data)).join('')) ;
//console.log(nodes);