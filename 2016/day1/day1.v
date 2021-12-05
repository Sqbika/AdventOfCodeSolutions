import os
import math.util {iabs}

fn read_file(fileName string) []string {
	return os.read_lines(fileName) or {
		eprintln("Failed to open file: ${fileName}. Aborting Mission")
		return []string{}
	}
}

fn read_test() []string {
	return read_file("test.txt")
}

fn read_input() []string {
	return read_file("input.txt")
}

fn main() {
	input := if os.args.len > 1 && os.args[1] == "test" { read_test() } else { read_input() }

	if input.len < 1 {return}

	directions := input[0].split(', ')

	println(part1(directions))
}

fn part1(input []string) string {

	//println(input)

	mut blocks := [0,0,0,0]

	mut index := 0

	for dir in input {
		mtch_idx := match dir[0].ascii_str() {
			'L' {-1}
			"R" {1}
			else {0}
		}


		index = (4 + index + mtch_idx) % 4

		//println("MatchIndex: $mtch_idx | Dir: ${dir[0].str()} | Idx $index | Inp: ${dir[1..]}")

		blocks[index] += dir[1..].int()
	}

	println(blocks)

	return (iabs(blocks[0] - blocks[2]) + iabs(blocks[1] - blocks[3])).str()
}

fn part2(input string) string {

	return "TODO"
}