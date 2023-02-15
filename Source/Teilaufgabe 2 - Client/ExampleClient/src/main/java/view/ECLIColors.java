package view;

public enum ECLIColors {

	// ###### TAKEN FROM START https://stackoverflow.com/a/51944613 ######
	// I could not remember the ANSI codes and I have looked on Internet for them.
	// I have also seen this answer which purpose to use an enum class.
	RESET("\u001B[0m"),

	BLACK("\u001B[30m"),

	// player/castle colours
	YELLOW("\u001B[33m"), RED("\u001B[31m"),

	// terrain colours
	GREEN("\u001B[32m"), BLUE("\u001B[34m"), BROWN("\u001B[33m"),

	// treasure colours
	MAGENTA("\u001B[45m");

	private final String color;

	private ECLIColors(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	// ###### TAKEN FROM END https://stackoverflow.com/a/51944613 ######

}
