import java.util.ArrayList;
import java.util.List;

public class CustomColorTransition {
	int maxValue = 100;
	List<CustomColor> colorArray = new ArrayList<CustomColor>();

	public CustomColorTransition(CustomColor transitionFrom, CustomColor transitionTo) {
		colorArray.add(transitionFrom);
		colorArray.add(transitionTo);
	}

	public void addTransitionColor(CustomColor transitionColor) {
		colorArray.add(transitionColor);
	}

	public void removeTransitionColor(int position) {
		if (position > colorArray.size()) {
			position = colorArray.size();
		}
		if (colorArray.size() > 0) {
			colorArray.remove(position);
		}
	}

	public CustomColor returnCustomColorTransitionColor(int value) {
		CustomColor returnColor = new CustomColor(0, 0, 0, 0);
		int colorBatchPos = calculateCustomColorTransitionSubPosition(value);
		int stepSize = maxValue / (getTransitionSize() - 1);

		returnColor = calculateCustomColorTransitionColor(colorArray.get(colorBatchPos),
				colorArray.get(colorBatchPos + 1), stepSize, colorBatchPos, value);

		return returnColor;
	}

	public CustomColor calculateCustomColorTransitionColor(CustomColor colorFrom, CustomColor colorTo, int stepSize,
			int colorBatchPos, int value) {
		CustomColor returnColor = new CustomColor(colorFrom.getAlpha(), colorFrom.getRed(), colorFrom.getGreen(),
				colorFrom.getBlue());
		int offsetAlpha = (colorFrom.getAlpha() - colorTo.getAlpha()) * -1;
		int offsetRed = (colorFrom.getRed() - colorTo.getRed()) * -1;
		int offsetGreen = (colorFrom.getGreen() - colorTo.getGreen()) * -1;
		int offsetBlue = (colorFrom.getBlue() - colorTo.getBlue()) * -1;
		int changePosition = value - (stepSize * colorBatchPos);

		double changeRatio = (double) changePosition / (double) stepSize;
		double changeValueAlpha = changeRatio * offsetAlpha;
		double changeValueRed = changeRatio * offsetRed;
		double changeValueGreen = changeRatio * offsetGreen;
		double changeValueBlue = changeRatio * offsetBlue;

		returnColor.setAlpha(returnColor.getAlpha() + (int) changeValueAlpha);
		returnColor.setRed(returnColor.getRed() + (int) changeValueRed);
		returnColor.setGreen(returnColor.getGreen() + (int) changeValueGreen);
		returnColor.setBlue(returnColor.getBlue() + (int) changeValueBlue);

		return returnColor;
	}

	public int calculateCustomColorTransitionSubPosition(int value) {
		int divisor = 1;
		double tempVal = value;
		double temp2 = (double) maxValue / ((double) getTransitionSize() - 1);
		double temp = temp2;

		for (; temp < tempVal;) {
			divisor++;
			temp = temp2;
			temp = temp * divisor;
		}

		return divisor - 1;
	}

	public List<CustomColor> getColorArray() {
		return colorArray;
	}
	
	public int getTransitionSize() {
		return colorArray.size();
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setColorArray(List<CustomColor> colorArray) {
		this.colorArray = colorArray;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public void printColorTransition() {
		System.out.println("TransitionSize: " + getTransitionSize());
		for (int i = 0; i < getTransitionSize(); i++)
			System.out.println(i + ": " + colorArray.get(i));
	
	}

}
