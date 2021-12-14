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