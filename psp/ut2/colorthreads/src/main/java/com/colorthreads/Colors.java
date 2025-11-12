package com.colorthreads;

public class Colors implements Runnable {
	public Colors(String colorName)
	{
		setColorName(colorName);
	}

	public static Colors[] createColors(String[] colorNames)
	{
		Colors[] colors = new Colors[colorNames.length];

		for (int i = 0; i < colors.length; i++) {
			colors[i] = new Colors(colorNames[i]);
		}

		return colors;
	}

	private	String colorName;

	public void setColorName(String colorName)
		throws IllegalArgumentException
	{
		String exceptionMessage = null;

		if (colorName == null) {
			exceptionMessage = "Color name cannot be null";
		}
		else if (colorName.matches(".*[^a-zA-Z].*")) {
			exceptionMessage = "Color name contains illegal characters";
		}

		if (exceptionMessage != null)
			throw new IllegalArgumentException(exceptionMessage);
		else {
			this.colorName = colorName;
		}
	}

	public String getColorName()
	{
		return this.colorName;
	}

	@Override
	public void run() {
		System.out.printf("Created color %s\n", this.colorName);
	}
}
