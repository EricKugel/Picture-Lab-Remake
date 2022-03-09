import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.concurrent.CountDownLatch;

import javax.swing.border.*;

/**
 * Displays a picture and lets you explore the picture by displaying the row, column, red,
 * green, and blue values of the pixel at the cursor when you click a mouse button or
 * press and hold a mouse button while moving the cursor.  It also lets you zoom in or
 * out.  You can also type in a row and column value to see the color at that location.
 * 
 * Originally created for the Jython Environment for Students (JES). 
 * Modified to work with DrJava by Barbara Ericson
 * Also modified to show row and columns by Barbara Ericson
 * Modified to have a built-in tester and input panel by Eric Kugel
 * 
 * @author Keith McDermottt, gte047w@cc.gatech.edu
 * @author Barb Ericson ericson@cc.gatech.edu
 * @author Eric Kugel erickugel713@gmail.com
 */
public class PictureExplorerAndTester extends JFrame implements MouseMotionListener, ActionListener, MouseListener {
  private int rowIndex = 0;
  private int colIndex = 0;
  
  private JScrollPane scrollPane;
  private JTextArea inputArea;
  private CountDownLatch latch;
  private boolean inputActive = false;
  
  private JLabel colLabel, rowLabel;
  private JButton colPrevButton, rowPrevButton, colNextButton, rowNextButton;
  private JTextField colValue, rowValue;
  private JLabel rValue, gValue, bValue;
  private JLabel colorLabel;
  private JPanel colorPanel;

  private JScrollPane inputPane;
  
  private JMenuBar menuBar;
  private JMenu zoomMenu;
  private int[] levels = {25, 50, 75, 100, 150, 200, 500};
  private JMenuItem[] zoomMenuItems = new JMenuItem[levels.length];
  
  private DigitalPicture picture;
  private ImageDisplay imageDisplay;
  
  private double zoomFactor;
  private int numberBase = 0;
  
  public PictureExplorerAndTester(DigitalPicture picture) {
    this.picture = picture;
    zoomFactor = 1;
    createWindow();
  }
  
  public Color inputColor() {
      return ColorChooser.pickAColor();
  }

  private void createAndInitPictureFrame() {
    setResizable(true);
    getContentPane().setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setTitle(picture.getTitle());
  }
  
  private void setUpMenuBar() {
    menuBar = new JMenuBar();
    zoomMenu = new JMenu("Zoom");
    for (int levelIndex = 0; levelIndex < levels.length; levelIndex++) {
        JMenuItem item = new JMenuItem(levels[levelIndex] + "%");
        zoomMenuItems[levelIndex] = item;
        item.addActionListener(this);
        zoomMenu.add(item);
    }
    menuBar.add(zoomMenu);

    JMenu testMenu = new JMenu("Test Method");
    String[][] methods = {{"testZeroBlue", "testKeepOnlyBlue", "testNegate", "testGrayscale", "testFixUnderwater"}, {"testMirrorVerticalLeftToRight", "testMirrorVerticalRightToLeft", "testMirrorHorizontalTopToBottom", "testMirrorHorizontalBottomToTop", "testMirrorDiagonal"}, {"testMirrorTemple", "testMirrorArms", "testMirrorSwan"}, {"testChange1", "testChange2"}};
    String[] labs = {"6.C Lab", "6.D Lab", "6.E Lab", "6.F Lab"};
    for (int labIndex = 0; labIndex < labs.length; labIndex++) {
      JMenu labMenu = new JMenu(labs[labIndex]);
      for (int methodIndex = 0; methodIndex < methods[labIndex].length; methodIndex++) {
        JMenuItem item = new JMenuItem(methods[labIndex][methodIndex]);
        item.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            String method = item.getText();
            PictureTester.run(method);
            dispose();
          }
        });
        labMenu.add(item);
      }
      testMenu.add(labMenu);
    }
    menuBar.add(testMenu);
    setJMenuBar(menuBar);
  }
  
  private void createAndInitScrollingImage() {
    scrollPane = new JScrollPane();
    
    BufferedImage bimg = picture.getBufferedImage();
    imageDisplay = new ImageDisplay(bimg);
    imageDisplay.addMouseMotionListener(this);
    imageDisplay.addMouseListener(this);
    imageDisplay.setToolTipText("Click a mouse button on a pixel to see the pixel information");
    scrollPane.setViewportView(imageDisplay);
    getContentPane().add(scrollPane, BorderLayout.CENTER);
  }

  public void setImage(Picture picture) {
    this.picture = picture;
    createAndInitScrollingImage();
  }
  
  private void createWindow() {
    createAndInitPictureFrame();
    setUpMenuBar();
    createInfoPanel();
    createAndInitScrollingImage();
    createAndInitInputBox();
    
    String pictureName = picture.getFileName().substring(picture.getFileName().lastIndexOf("/") + 1);
    log("Viewing " + pictureName);

    pack();
    setVisible(true);
  }
  
  private void setUpNextAndPreviousButtons() {
    Icon prevIcon = new ImageIcon(DigitalPicture.class.getResource("leftArrow.gif"), "previous index");
    Icon nextIcon = new ImageIcon(DigitalPicture.class.getResource("rightArrow.gif"), "next index");

    colPrevButton = new JButton(prevIcon);
    colNextButton = new JButton(nextIcon);
    rowPrevButton = new JButton(prevIcon);
    rowNextButton = new JButton(nextIcon);
    
    colNextButton.setToolTipText("Click to go to the next column value");
    colPrevButton.setToolTipText("Click to go to the previous column value");
    rowNextButton.setToolTipText("Click to go to the next row value");
    rowPrevButton.setToolTipText("Click to go to the previous row value");
    
    int prevWidth = prevIcon.getIconWidth() + 2;
    int nextWidth = nextIcon.getIconWidth() + 2;
    int prevHeight = prevIcon.getIconHeight() + 2;
    int nextHeight = nextIcon.getIconHeight() + 2;
    Dimension prevDimension = new Dimension(prevWidth,prevHeight);
    Dimension nextDimension = new Dimension(nextWidth, nextHeight);
    colPrevButton.setPreferredSize(prevDimension);
    rowPrevButton.setPreferredSize(prevDimension);
    colNextButton.setPreferredSize(nextDimension);
    rowNextButton.setPreferredSize(nextDimension);
    
    colPrevButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        colIndex--;
        if (colIndex < 0)
          colIndex = 0;
        displayPixelInformation(colIndex,rowIndex);
      }
    });
    
    rowPrevButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        rowIndex--;
        if (rowIndex < 0)
          rowIndex = 0;
        displayPixelInformation(colIndex,rowIndex);
      }
    });
    
    colNextButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        colIndex++;
        if (colIndex >= picture.getWidth())
          colIndex = picture.getWidth() - 1;
        displayPixelInformation(colIndex,rowIndex);
      }
    });
    
    rowNextButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        rowIndex++;
        if (rowIndex >= picture.getHeight())
          rowIndex = picture.getHeight() - 1;
        displayPixelInformation(colIndex,rowIndex);
      }
    });
  }
  
  public JPanel createLocationPanel(Font labelFont) {
    JPanel locationPanel = new JPanel();
    locationPanel.setLayout(new FlowLayout());
    Box hBox = Box.createHorizontalBox();
    
    rowLabel = new JLabel("Row:");
    colLabel = new JLabel("Column:");
    
    colValue = new JTextField(Integer.toString(colIndex + numberBase),6);
    rowValue = new JTextField(Integer.toString(rowIndex + numberBase),6);
    for (JTextField value : new JTextField[] {colValue, rowValue}) {
      value.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          displayPixelInformation(colValue.getText(),rowValue.getText());
        }
      });
    }
    
    setUpNextAndPreviousButtons();
    
    for (Component comp : new Component[] {colLabel, rowLabel, colValue, rowValue}) {
      comp.setFont(labelFont);
    }
    
    for (Component comp : new Component[] {Box.createHorizontalGlue(), rowLabel, rowPrevButton, rowValue, rowNextButton, Box.createHorizontalStrut(10), colLabel, colPrevButton, colValue, colNextButton}) {
      hBox.add(comp);
    }
    locationPanel.add(hBox);
    hBox.add(Box.createHorizontalGlue());
    return locationPanel;
  }

  public void createAndInitInputBox() {
    inputArea = new JTextArea(10, 20);
    inputPane = new JScrollPane(inputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    latch = new CountDownLatch(1);
    inputArea.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          if (inputActive) {
            latch.countDown();
          }
        }
      }
    });
    getContentPane().add(inputPane, BorderLayout.EAST);
  }

  public void log(String s) {
    inputArea.append(s + "\n");
    inputArea.setCaretPosition(inputArea.getText().length());
  }

  public String input(String prompt) {
    log(prompt);
    inputActive = true;
    pack();

    new Runnable() {
      public void run() {
        try {
          latch.await();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }.run();
    latch = new CountDownLatch(1);
    inputActive = false;

    return inputArea.getText().substring(inputArea.getText().lastIndexOf("\n"));
  }

  public double inputDouble(String prompt) {
    String input = input(prompt);
    boolean done = false;
    while (!done) {
        try {
            Double.parseDouble(input);
            done = true;
        } catch(Exception e) {
            log("You must enter a number");
            input = input(prompt);
        }
    }
    return Double.parseDouble(input);
  }

  public int inputInt(String prompt) {
      return (int) inputDouble(prompt);
  }

  public String inputFile() {
      return FileChooser.pickAFile();
  }
  
  private JPanel createColorInfoPanel(Font labelFont) {
    JPanel colorInfoPanel = new JPanel();
    colorInfoPanel.setLayout(new FlowLayout());
    
    Pixel pixel = new Pixel(picture, colIndex, rowIndex);

    rValue = new JLabel("R: " + pixel.getRed());
    gValue = new JLabel("G: " + pixel.getGreen());
    bValue = new JLabel("B: " + pixel.getBlue());
    JLabel[] colorValueLabels = {rValue, gValue, bValue};
    
    colorLabel = new JLabel("Color at location: ");
    colorLabel.setFont(labelFont);

    colorPanel = new JPanel();
    colorPanel.setPreferredSize(new Dimension(25,25));
    colorPanel.setBorder(new LineBorder(Color.black, 1));
    colorPanel.setBackground(pixel.getColor());
    
    for (JLabel label : colorValueLabels) {
      label.setFont(labelFont);
      colorInfoPanel.add(label);
    }
    colorInfoPanel.add(colorLabel);
    colorInfoPanel.add(colorPanel);
    
    return colorInfoPanel; 
  }
  
  private void createInfoPanel() {
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BorderLayout());
    Font largerFont = new Font(infoPanel.getFont().getName(), infoPanel.getFont().getStyle(), 14);
    JPanel locationPanel = createLocationPanel(largerFont);
    JPanel colorInfoPanel = createColorInfoPanel(largerFont);
    infoPanel.add(BorderLayout.NORTH,locationPanel);
    infoPanel.add(BorderLayout.SOUTH,colorInfoPanel); 
    getContentPane().add(BorderLayout.NORTH,infoPanel);
  } 

  public void checkScroll() {
    int xPos = (int) (colIndex * zoomFactor); 
    int yPos = (int) (rowIndex * zoomFactor); 
    
    if (zoomFactor > 1) {
      JViewport viewport = scrollPane.getViewport();
      Rectangle rect = viewport.getViewRect();
      int rectWidth = (int) rect.getWidth();
      int rectHeight = (int) rect.getHeight();
       
      int macolIndexX = (int) (picture.getWidth() * zoomFactor) - rectWidth - 1;
      int macolIndexY = (int) (picture.getHeight() * zoomFactor) - rectHeight - 1;
      
      int viewX = xPos - (int) (rectWidth / 2);
      int viewY = yPos - (int) (rectHeight / 2);
      
      if (viewX < 0)
        viewX = 0;
      else if (viewX > macolIndexX)
        viewX = macolIndexX;
      if (viewY < 0)
        viewY = 0;
      else if (viewY > macolIndexY)
        viewY = macolIndexY;
      
      viewport.scrollRectToVisible(new Rectangle(viewX,viewY,rectWidth,rectHeight));
    }
  }
  
  public void zoom(double factor) {
    zoomFactor = factor;
    
    int width = (int) (picture.getWidth()*zoomFactor);
    int height = (int) (picture.getHeight()*zoomFactor);
    BufferedImage bimg = picture.getBufferedImage();
    
    imageDisplay.setImage(bimg.getScaledInstance(width, height, Image.SCALE_DEFAULT));
    imageDisplay.setCurrentX((int) (colIndex * zoomFactor));
    imageDisplay.setCurrentY((int) (rowIndex * zoomFactor));
    imageDisplay.revalidate();
    checkScroll();
  }

  public void mouseDragged(MouseEvent e) {
    displayPixelInformation(e);
  }

  public void mouseMoved(MouseEvent e) {}
  
  private boolean isLocationInPicture(int column, int row) {
    return column >= 0 && column < picture.getWidth() && row >= 0 && row < picture.getHeight();
  }
  
  public void displayPixelInformation(String xString, String yString) {
    int x = -1;
    int y = -1;
    try {
      x = Integer.parseInt(xString);
      x = x - numberBase;
      y = Integer.parseInt(yString);
      y = y - numberBase;
    } catch (Exception ex) { }
    
    if (x >= 0 && y >= 0) {
      displayPixelInformation(x,y);
    }
  }
  
  private void displayPixelInformation(int pictureX, int pictureY) {
    if (isLocationInPicture(pictureX, pictureY)) {
      colIndex = pictureX;
      rowIndex = pictureY;
      Pixel pixel = new Pixel(picture,colIndex,rowIndex);
      
      colValue.setText(Integer.toString(colIndex  + numberBase));
      rowValue.setText(Integer.toString(rowIndex + numberBase));
      rValue.setText("R: " + pixel.getRed());
      gValue.setText("G: " + pixel.getGreen());
      bValue.setText("B: " + pixel.getBlue());
      colorPanel.setBackground(new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue())); 
    } else {
      clearInformation();
    }
    
    imageDisplay.setCurrentX((int) (colIndex * zoomFactor));
    imageDisplay.setCurrentY((int) (rowIndex * zoomFactor));
  }
  
  private void displayPixelInformation(MouseEvent e) {
    displayPixelInformation((int) (e.getY() / zoomFactor + numberBase), (int) (e.getX() / zoomFactor + numberBase));
  }
  
  private void clearInformation() {
    colValue.setText("N/A");
    rowValue.setText("N/A");
    rValue.setText("R: N/A");
    gValue.setText("G: N/A");
    bValue.setText("B: N/A");
    colorPanel.setBackground(Color.black);
    colIndex = -1;
    rowIndex = -1;
  }
  
  public void mouseClicked(MouseEvent e) {
    displayPixelInformation(e);
  }
  
  public void mousePressed(MouseEvent e) {
    displayPixelInformation(e);
  }
  
  public void mouseReleased(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  
  private void enableZoomItems() {
    for (JMenuItem item : zoomMenuItems) {
        item.setEnabled(true);
    }
  }
  
  public void actionPerformed(ActionEvent a) {
    if (a.getActionCommand().equals("Update")) {
        this.repaint();
    } else {
      try {
          int zoomLevel = Integer.parseInt(a.getActionCommand().substring(0, a.getActionCommand().indexOf("%")));
          int zoomLevelIndex = -1;
          for (int levelIndex = 0; levelIndex < levels.length; levelIndex++) {
              if (levels[levelIndex] == zoomLevel) {
                  zoomLevelIndex = levelIndex;
                  break;
              }
          }
          this.zoom((double) zoomLevel / 100);
          enableZoomItems();
          zoomMenuItems[zoomLevelIndex].setEnabled(false);
      } catch (Exception e) {}
    }
  }
  
  public static void main(String[] arg0) {
    Picture pic = new Picture("images/beach.jpg");
    pic.explore();
  }
}