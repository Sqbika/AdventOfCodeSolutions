const fs = require('fs');

if (!true)
    var data = fs.readFileSync('./input.txt').toString().split('\n');
else
    var data = fs.readFileSync('./test2.txt').toString().split('\n');

var recipes = {
    "ORE": {
        amount: 0,
        from: []
    }
};

data.forEach(e => processLine(e))

function processLine(line) {
    var splitLine = line.split(' => ');
    var result = parsePair(splitLine[1]);
    recipes[result['thing']] = {
        amount: result['amount'],
        from: splitLine[0].split(', ').map(parsePair)
    };
}

function parsePair(pair) {
    var splitPair = pair.split(' ');
    return {
        amount: Number(splitPair[0]),
        thing: splitPair[1]
    };
}

function calculateRequirement(obj, acc) {
    return recipes[obj].from.reduce((acc, cur) => {
        if (cur.thing == "ORE") return acc;
        if (recipes[cur.thing].from.length == 1 && recipes[cur.thing].from[0].thing == "ORE") {
            return acc + (clamp(cur.amount, recipes[cur.thing].amount) / recipes[cur.thing].amount) * recipes[cur.thing].from[0].amount; 
        } else {
            return calculateRequirement(cur.thing, acc);
        }
    }, acc)
}

function calcDepth(obj, depth) {
    obj.depth = depth;
    obj.from.forEach(x => calcDepth(recipes[x.thing], depth+1))
}
 
var oreGenerators = Object.keys(recipes).filter(x => recipes[x].from.length == 1 && recipes[x].from[0].thing == "ORE");

function calculateRequirement2(obj) {
    var objKeys = Object.keys(obj).sort((a,b) => recipes[a].depth - recipes[b].depth);
    while (!objKeys.every(x => oreGenerators.indexOf(x) != -1)) {
        objKeys = Object.keys(obj).sort((a,b) => recipes[a].depth - recipes[b].depth);
        for (var o2b in objKeys) {
            var ob = objKeys[o2b];
            if (oreGenerators.indexOf(ob) != -1) {continue;}
            var recipe = recipes[ob];
            for (var from in recipe.from) {
                var thing = recipe.from[from].thing;
                if (obj[thing] == undefined) obj[thing] = 0;
                obj[thing] += recipe.from[from].amount * (obj[ob] || 1);
            }
            delete(obj[ob]);
        }
    }
    return Object.keys(obj).reduce((acc, curr) => {
        return acc + (clamp(recipes[curr].amount, obj[curr]) / recipes[curr].amount) * recipes[curr].from[0].amount;
    }, 0);
}

function clamp(req, amount) {
    while (amount % req != 0) {
        amount++;
    }
    return amount;
}

calcDepth(recipes["FUEL"], 0);
//console.log(calculateRequirement("FUEL", 0));
console.log(calculateRequirement2({"FUEL": 1}))
