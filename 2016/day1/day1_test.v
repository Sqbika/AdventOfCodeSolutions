module main


fn test_part1() {
	assert part1("R2, L3".split(', ')) == "5"
	assert part1("R2, R2, R2".split(', ')) == "2"
	assert part1("R5, L5, R5, R3".split(', ')) == "12"
}