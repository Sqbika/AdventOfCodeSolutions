const fs = require('fs');

var reg = new RegExp('Step (.*) must be finished before step (.*) can begin.');

var data = fs.readFileSync('./input.txt').toString();

const index = "ABCDEFGHIJKLMNPQRSTUVWXYZ";

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
function processData(data, workerNum) {
   data.forEach(([from, to]) => {
       if (nodes[from] == undefined) nodes[from] = {id: from, parent: [], children: [], time: index.indexOf(from)  + 60};
       if (nodes[to] == undefined) nodes[to] = {id: to, parent: [], children: [], time: index.indexOf(to)  + 60};
       nodes[from].children.push(to);
       nodes[to].parent.push(from);
   });
   var queue = findStarts(nodes).sort((a,b) => a.localeCompare(b));
   console.log(queue);
   var sliceQueue = function () {
       var result = queue[0];
       queue = queue.slice(1, queue.length);
       return result;
   };
   
   var sortQueue = function () {
    queue = queue.sort((a,b) => a.localeCompare(b));
   }

   var done = [];
   var seconds = 0;
   var workers = JSON.parse("[" + '{"startedat": 0},'.repeat(workerNum-1) + '{"startedat":0}]');
   var working = true;
   while (true) {
       for (var i = 0; i < workerNum; i++) {
           var cur = workers[i].cur;
            if (cur == undefined) {
                if (queue.length != 0) {
                    workers[i].cur = sliceQueue();
                    workers[i].startedat = seconds;
                }
            } else if (nodes[cur].time < seconds - workers[i].startedat) {
                done.push(cur);
                console.log("Work Done: " + cur + " / At: " + seconds + " / Nodes: " + nodes[cur].children.map(x => (queue.indexOf(x) == -1) + "/" + (done.indexOf(x) == -1) + "/" + (nodes[x].parent.every(par => done.indexOf(par) != -1) || nodes[x].parent.length == 0)).join(';'));
                queue = queue.concat(nodes[cur].children.filter(x => queue.indexOf(x) == -1 && done.indexOf(x) == -1 && (nodes[x].parent.every(par => done.indexOf(par) != -1) || nodes[x].parent.length == 0)))
                sortQueue();
                if (queue.length != 0) {
                    workers[i].cur = sliceQueue();
                    workers[i].startedat = seconds;
                } else {
                    workers[i].cur = undefined;
                }
            }
       }
       if (queue.length == 0 && workers.every(x => x.cur == undefined)) {
           //console.log(Object.keys(nodes).map(x => x + ": P: [" + nodes[x].parent.join(',') + "] C: [" + nodes[x].children.join(',') + " ]").join('\n'));
           return [seconds];
       }
       seconds++;
       console.log("S:" + seconds + " / Q:[" + queue.join(',') + "] / W: [" + workers.map(x => x.cur + " - " + x.startedat).join(',') + "] / D: " + done.join(','));
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


console.log(processData(readData(data), 5).join('')) ;
//console.log(nodes);