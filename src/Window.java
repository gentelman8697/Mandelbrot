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

        testArray.reSize(frame.getWidth() - 12, frame.getHeight() - 35);
        // -26 und -71 für ein genaues Einpassen ins JFrame

        int width = testArray.getArrayWidth();
        int height = testArray.getArrayLength();

        img = resizeImage(img, testArray.getArrayWidth(), testArray.getArrayLength());

        System.out.println("Frame W: " + frame.getWidth());
        System.out.println("Frame H: " + frame.getHeight());
        System.out.println("Array W: " + testArray.getArrayWidth());
        System.out.println("Array H: " + testArray.getArrayLength());
        System.out.println("img   W: " + img.getWidth());
        System.out.println("img   H: " + img.getHeight());

	/*
		CustomColor testColor2 = new CustomColor((int) (Math.random()*255),255,0,0);
		CustomColor testColor3 = new CustomColor((int) (Math.random()*255),(int) 0,255,0);
		CustomColor testColor4 = new CustomColor((int) (Math.random()*255),(int) 0,0,255); */

        CustomColor testColor2 = new CustomColor((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        CustomColor testColor3 = new CustomColor((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        CustomColor testColor4 = new CustomColor((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
		/*CustomColor testColor5 = new CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		CustomColor testColor6 = new CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		CustomColor testColor7 = new CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		CustomColor testColor8 = new CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		CustomColor testColor9 = new CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		CustomColor testColor10 = new CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		CustomColor testColor11 = new CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		CustomColor testColor12 = new CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		CustomColor testColor13 = new CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		CustomColor testColor14 = new CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		CustomColor testColor15 = new CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		CustomColor testColor16 = new CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));
		CustomColor testColor17 = new CustomColor((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255));*/


        CustomColor outPutColor = new CustomColor(0, 0, 0, 0);

        CustomColorTransition testTransition1 = new CustomColorTransition(testColor2, testColor3);


        testTransition1.addTransitionColor(testColor4);
		/*
		testTransition1.addTransitionColor(testColor5);
		testTransition1.addTransitionColor(testColor6);
		testTransition1.addTransitionColor(testColor7);
		testTransition1.addTransitionColor(testColor8);
		testTransition1.addTransitionColor(testColor9);
		testTransition1.addTransitionColor(testColor10);
		testTransition1.addTransitionColor(testColor11);
		testTransition1.addTransitionColor(testColor12);
		testTransition1.addTransitionColor(testColor13);
		testTransition1.addTransitionColor(testColor14);
		testTransition1.addTransitionColor(testColor15);
		testTransition1.addTransitionColor(testColor16);
		testTransition1.addTransitionColor(testColor17); */


        testTransition1.setMaxValue(testArray.getMaxValue());

        //System.out.println(testTransition1.toString());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int temp;

                temp = testArray.getDataArray().get(x).get(y);
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

        CustomNumber n1 = new CustomNumber();
        CustomNumber n2 = new CustomNumber();
        CustomNumber n3 = new CustomNumber();
        CustomNumber n4 = new CustomNumber();
        CustomNumber n5 = new CustomNumber();
        CustomNumber n6 = new CustomNumber();
        CustomNumber n7 = new CustomNumber();
        CustomNumberUtils.test(35);

        /*
        n1.setValue("1.");
        n2.setValue("1.");
        n3.setValue("1.");

        for(int i = 0; i < 10000; i++)
        {
            n1 = CustomNumberUtils.multiply(n1,n2);
            n2 = CustomNumberUtils.addUp(n2,n3);
            CustomNumberUtils.print(n1);
        }
        */
    }
}
