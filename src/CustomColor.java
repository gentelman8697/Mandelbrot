public class CustomColor {

	int colorValue = 0;
	int alpha = 0;
	int red = 0;
	int green = 0;
	int blue = 0;

	public CustomColor(int alpha, int red, int green, int blue) {
		this.alpha = alpha;
		this.red = red;
		this.green = green;
		this.blue = blue;
		calculateColorValue();
	}

	public void calculateColorValue() {
		colorValue = (alpha << 24) | (red << 16) | (green << 8) | blue;
	}

	public int getColorValue() {
		return colorValue;
	}

	public int getAlpha() {
		return alpha;
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha%256;
		calculateColorValue();
	}

	public void setRed(int red) {
		this.red = red%256;
		calculateColorValue();
	}

	public void setGreen(int green) {
		this.green = green%256;
		calculateColorValue();
	}

	public void setBlue(int blue) {
		this.blue = blue%256;
		calculateColorValue();
	}

	@Override
	public String toString() {
		return "CustomColor [colorValue=" + colorValue + ", alpha=" + alpha + ", red=" + red + ", green=" + green
				+ ", blue=" + blue + "]";
	}

	
}
