var left = 123456;
var right = 456789;

var nums = [];

for (var i = left; i < right; i++) {nums.push(i);}

function incrementOrEqualPlusDoubleFilter(num) {
    var str = ""+num;
    var hasDouble = false;
    for (var i = 0; i < str.length-1; i++) {
        if (Number(str[i+1]) == Number(str[i])) hasDouble = true;
        if (Number(str[i+1]) < Number(str[i])) return false;
    }
    return hasDouble;
}

var nums1 = nums.filter(incrementOrEqualPlusDoubleFilter);

console.log(nums1.length)

/*
G: 111122
B: 111112
B: 123444
G: 112333
*/

function part2Filter(num) {
    var str = ""+num;
    for (var i = 0; i < str.length-1; i++) {
        if (str[i] == str[i+1]) {
            if (str.split('').filter(x => str[i] == x).length == 2) {
                return true;
            }
        }
    }
    return false;
}

console.log(nums1.filter(part2Filter).length);