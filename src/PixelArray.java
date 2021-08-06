import java.util.ArrayList;
import java.util.List;

public class PixelArray {

	int arrayWidth = 0;
	int arrayLength = 0;
	int counter = 0;
	int maxValue = 0;
	List<List<Integer>> displayArray = new ArrayList<List<Integer>>();

	public PixelArray(int widthX, int lengthY) {
		for (int i = 0; i < widthX; i++) {
			displayArray.add(new ArrayList<Integer>());
			for (int k = 0; k < lengthY; k++) {
				displayArray.get(i).add((Integer) (k * i));
			}
		}

		arrayWidth = displayArray.size();
		arrayLength = displayArray.get(0).size();
	}

	public List<List<Integer>> getDataArray() {
		return displayArray;
	}

	public void setDataArray(List<List<Integer>> dataArray) {
		this.displayArray = dataArray;
	}

	public int getArrayWidth() {
		return arrayWidth;
	}

	public void setArrayWidth(int arrayWidth) {
		this.arrayWidth = arrayWidth;
	}

	public int getArrayLength() {
		return arrayLength;
	}

	public void setArrayLength(int arrayLength) {
		this.arrayLength = arrayLength;
	}

	public void reSize(int sizeX, int sizeY) {
		delete();
		maxValue = 0;
		counter = counter + 1;
		for (int i = 0; i < sizeX; i++) {
			displayArray.add(new ArrayList<Integer>());
			for (int k = 0; k < sizeY; k++) {
				Integer b = 0;
				// double z = Math.sin(k*i/50000);
				// z = z * 100000;
				// int y = (int) z;
				 b = k * i;
				//b = (((i * k )+1)%((k+i+35)/(35)));
				// displayArray.get(i).add(y);
				// displayArray.get(i).add((Integer)12345);
				displayArray.get(i).add(b);
				// displayArray.get(i).add((Integer) (k * i * counter-(counter*10)));
			}
		}

		arrayWidth = displayArray.size();
		arrayLength = displayArray.get(0).size();
		// System.out.println("Width "+arrayWidth);
		// System.out.println("Length "+arrayLength);

	}

	public void delete() {

		for (int i = arrayWidth - 1; i >= 0; i--) {
			for (int k = arrayLength - 1; k >= 0; k--) {
				displayArray.get(i).remove(k);
			}
			displayArray.remove(i);
		}
	}

	public void display(int positionX, int positionY, int rangeX, int rangeY) {

		int fromX = (positionX - (rangeX / 2));
		int toX = (positionX + (rangeX / 2));
		int fromY = (positionY - (rangeY / 2));
		int toY = (positionY + (rangeY / 2));

		if (positionX < 0) {
			positionX = 0;
		}
		if (positionY < 0) {
			positionY = 0;
		}

		if (positionX > arrayWidth) {
			positionX = arrayWidth;
		}
		if (positionY > arrayLength) {
			positionY = arrayLength;
		}

		if (fromX < 0) {
			fromX = 0;
		}
		if (toX > arrayWidth) {
			toX = arrayWidth;
		}
		if (fromY < 0) {
			fromY = 0;
		}
		if (toY > arrayLength) {
			toY = arrayLength;
		}

		for (int k = fromY; k < toY; k++) {
			String g = "";
			for (int i = fromX; i < toX; i++) {
				String b = String.valueOf(displayArray.get(i).get(k));
				while (b.length() < 10) {
					b = " " + b;
				}
				b = b + " X: " + i + " Y: " + k;
				g = g + b;
			}
			System.out.println(g);
		}
		System.out.println("Displayed from X: " + fromX + " to X: " + (toX - 1));
		System.out.println("Displayed from Y: " + fromY + " to Y: " + (toY - 1));
		System.out.println("Around X: " + positionX + " Aorund Y: " + positionY);
		System.out.println("RangeX: " + rangeX + " RangeY: " + rangeY);
	}

	public int getMaxValue() {
		for (int i = 0; i < arrayWidth; i++) {
			for (int k = 0; k < arrayLength; k++) {
				if (displayArray.get(i).get(k) > maxValue)
					maxValue = displayArray.get(i).get(k);
			}
		}
		return maxValue;

	}
}
