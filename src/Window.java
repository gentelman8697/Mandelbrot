import datatypes.CustomComplexNumber;
import datatypes.CustomNumber;
import disp.CustomColor;
import disp.CustomColorTransition;
import disp.PixelArray;
import util.CCNUtils;
import util.CNUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Window {

    int i = 0;

    // dont touch
    private JFrame frame; // just touched it, yolo

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Window window = new Window();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Starting Point
     */
    private void initialize() {
        frame = new JFrame();
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
        JButton btnNewButton = new JButton("START");
        PixelArray testArray = new PixelArray(1920, 1080); // DADA
        BufferedImage img = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.white);
        frame = initializeFrame(frame);
        updateScreen(lblNewLabel, img, testArray);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent arg0) {

                btnNewButton.setLocation(frame.getWidth() - 180, 20);
                lblNewLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                //lblNewLabel.setBounds(0, 0, 1820, 980);


            }
        });

        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
            }
        });
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateScreen(lblNewLabel, img, testArray);
            }
        });
        btnNewButton.setBounds(1732, 21, 141, 35);
        frame.getContentPane().add(btnNewButton);
    }

    /**
     * Create the application.
     */
    public Window() {
        initialize();
    }

    public JFrame initializeFrame(JFrame frame) {

        frame.setBounds(100, 100, 1920 / 2, 1080 / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        return frame;
    }

    public static BufferedImage resizeImage(BufferedImage img, int newW, int newH) {
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
        return dimg;
    }

    private void updateScreen(JLabel lblNewLabel, BufferedImage img, PixelArray testArray) {


        testArray.reSize(120,120);

        //testArray.reSize(frame.getWidth() - 12, frame.getHeight() - 35);
        // -26 und -71 für ein genaues Einpassen ins JFrame


        img = resizeImage(img, testArray.getArrLenX(), testArray.getArrLenY());

        System.out.println("Frame W: " + frame.getWidth());
        System.out.println("Frame H: " + frame.getHeight());
        System.out.println("Array W: " + testArray.getArrLenX());
        System.out.println("Array H: " + testArray.getArrLenY());
        System.out.println("img   W: " + img.getWidth());
        System.out.println("img   H: " + img.getHeight());


		CustomColor testColor2 = new CustomColor(255,255,0,0);
        CustomColor testColor3 = new CustomColor(255,255,255,0);
        CustomColor testColor4 = new CustomColor(255, 0,255,0);
		CustomColor testColor5 = new CustomColor(255, 0,255,255);
        CustomColor testColor6 = new CustomColor(255, 0,0,255);
        CustomColor testColor7 = new CustomColor(255, 255,0,255);
        CustomColor testColor8 = new CustomColor(255, 0,0,0);


/*
        disp.CustomColor testColor2 = new disp.CustomColor((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        disp.CustomColor testColor3 = new disp.CustomColor((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        disp.CustomColor testColor4 = new disp.CustomColor((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
		disp.CustomColor testColor5 = new disp.CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		disp.CustomColor testColor6 = new disp.CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		disp.CustomColor testColor7 = new disp.CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		disp.CustomColor testColor8 = new disp.CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		disp.CustomColor testColor9 = new disp.CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		disp.CustomColor testColor10 = new disp.CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		disp.CustomColor testColor11 = new disp.CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		disp.CustomColor testColor12 = new disp.CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		disp.CustomColor testColor13 = new disp.CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		disp.CustomColor testColor14 = new disp.CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		disp.CustomColor testColor15 = new disp.CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		disp.CustomColor testColor16 = new disp.CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		disp.CustomColor testColor17 = new disp.CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));*/


        CustomColor outPutColor = new CustomColor(0, 0, 0, 0);

        CustomColorTransition testTransition1 = new CustomColorTransition(testColor2, testColor3);

        testTransition1.addTransitionColor(testColor4);
		testTransition1.addTransitionColor(testColor5);
		testTransition1.addTransitionColor(testColor6);
		testTransition1.addTransitionColor(testColor7);
        testTransition1.addTransitionColor(testColor8);

        CustomNumber n1 = new CustomNumber();
        CustomNumber n2 = new CustomNumber();
        CustomNumber n3 = new CustomNumber();
        CustomNumber n4 = new CustomNumber();
        CustomNumber n5 = new CustomNumber();
        CustomNumber n6 = new CustomNumber();
        CustomNumber n7 = new CustomNumber();

        n1.set("+1.");
        n2.set("+2.");
        n3.set("+3.");
        n4.set("+4.");

        CustomComplexNumber c1 = new CustomComplexNumber(new CustomNumber(), new CustomNumber());
        CustomComplexNumber c2 = new CustomComplexNumber(new CustomNumber("+.45"), new CustomNumber("+.45"));

        int[][] mandelArr = new int[120][120];

        for (int i = 0; i < mandelArr.length; i++) {
            for (int j = 0; j < mandelArr[0].length; j++) {
                mandelArr[i][j] = 0;
            }
        }

        int iVal = 0;
        int jVal = 0;

        int accur = 6;
        int iters = 200;


        for (CustomNumber i = new CustomNumber("-2."); CNUtils.isSmaller(i, new CustomNumber("+1.")); i = CNUtils.addUp(i, new CustomNumber("+.025"))) {
            System.out.println(i);
            for (CustomNumber j = new CustomNumber("-1.5"); CNUtils.isSmaller(j, new CustomNumber("+1.5")); j = CNUtils.addUp(j, new CustomNumber("+.025"))) {
                System.out.print("+");
                int safeVal = 0;
                c1 = new CustomComplexNumber(new CustomNumber(), new CustomNumber());
                if (!j.isZero() && !i.isZero()) {
                    for (int k = 0; k < iters; k++) {
                        c1 = CCNUtils.multiply(c1, c1, accur);
                        c1 = CCNUtils.addUp(c1, new CustomComplexNumber(i, j), accur);
                        safeVal = k;
                        if (CNUtils.isGreater(CCNUtils.absolute(c1, accur/2), new CustomNumber("+2."))) {
                            break;
                        }
                    }
                }
                mandelArr[iVal][jVal] = safeVal;
                jVal++;
            }
            iVal++;
            jVal = 0;
        }


        for (int i = 0; i < mandelArr.length; i++) {
            for (int j = 0; j < mandelArr[0].length; j++) {
                String printString = " ";
                if (mandelArr[i][j] < 100) {
                    printString += " ";
                }
                if (mandelArr[i][j] < 10) printString += " ";
                {
                    printString += mandelArr[i][j];
                    System.out.print(printString);
                }
            }
            System.out.println();
        }

        testArray.set(mandelArr);
        testTransition1.setMaxValue(testArray.getMaxValue());

        //for (int x = 0; x < width; x++) {
        //    for (int y = 0; y < height; y++) {

        for (int x = 0; x < testArray.getArrLenX(); x++) {
            for (int y = 0; y < testArray.getArrLenY(); y++) {
                int temp;
                temp = testArray.getDataArray()[x][y];
                outPutColor = testTransition1.returnCustomColorTransitionColor(temp);
                img.setRGB(x, y, outPutColor.getColorValue());
            }
        }

        frame.getContentPane().add(lblNewLabel);
        lblNewLabel.setIcon(new ImageIcon(img));
		/*
		System.out.println();
		System.out.println(img.getRGB(0, 0));
		System.out.println(img.getRGB(testArray.getArrayWidth() - 10, testArray.getArrayLength() - 10)); */

        // ===============================================================================================================
    }
}
