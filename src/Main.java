import java.awt.*;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {
        System.out.println("test123");


        CustomNumber testNumber1 = new CustomNumber();
        testNumber1.setValue("0.17");
        CustomNumber testNumber2 = new CustomNumber();
        testNumber2.setValue("1.2");
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            testNumber2 = CustomNumberUtils.multiply(testNumber2, testNumber1);
            CustomNumberUtils.print(testNumber2);

        }
        CustomColor testColor = new CustomColor(0,255,0,255);

        StdDraw.setPenRadius(0.01);

        StdDraw.setPenColor(testColor.getRed(),testColor.getGreen(),testColor.getBlue());
        StdDraw.point(0.5, 0.5);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.line(0.2, 0.2, 0.8, 0.2);


    }
}
