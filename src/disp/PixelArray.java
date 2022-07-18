package disp;

import java.util.ArrayList;

public class PixelArray {
	int counter = 0;
	int maxValue = 0;
	int[][] array;

	public PixelArray(int lenX, int lenY) {
		array = new int[lenX][lenY];
	}

	public void set(int[][] kappa) {
		for(int i = 0; i < kappa.length; i++)
		{
			for(int j = 0; j  < kappa[0].length; j++)
			{
				array[i][j] = kappa[i][j];
			}
		}
	}
	public int[][] getDataArray() {
		return array;
	}



	public int getArrLenX() {
		return array.length;
	}



	public int getArrLenY() {
		return array[0].length;
	}


	public void reSize(int sizeX, int sizeY) {
		array = new int[sizeX][sizeY];
	}

	public int getMaxValue()
	{
		int ret = -999999999;

		for (int i = 0; i < array.length; i++)
		{
			for(int j = 0; j < array.length; j++)
			{
				if(array[i][j] > ret)
				{
					ret = array[i][j];
				}
			}
		}
		return ret;
	}

	/*
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
		 */

}
