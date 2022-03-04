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
public class PictureExplorerAndTester implements MouseMotionListener, ActionListener, MouseListener {

  private int rowIndex = 0;
  private int colIndex = 0;
  
  private JFrame pictureFrame;
  private JScrollPane scrollPane;
  
  private JLabel colLabel;
  private JButton colPrevButton;
  private JButton rowPrevButton;
  private JButton colNextButton;
  private JButton rowNextButton;
  private JLabel rowLabel;
  private JTextField colValue;
  private JTextField rowValue;
  private JLabel rValue;
  private JLabel gValue;
  private JLabel bValue;
  private JLabel colorLabel;
  private JPanel colorPanel;

  private JPanel inputPanel;
  
  private JMenuBar menuBar;
  private JMenu zoomMenu;
  private int[] levels = {25, 50, 75, 100, 150, 200, 500};
  private JMenuItem[] zoomMenuItems = new JMenuItem[levels.length];
  
  private DigitalPicture picture;
  
  private ImageDisplay imageDisplay;
  
  private double zoomFactor;
  
  private int numberBase=0;
  
  /**
   * Constructs a PictureExplorer object.
   * 
   * @param picture  the picture to explore
   */
  public PictureExplorerAndTester(DigitalPicture picture) {
    this.picture=picture;
    zoomFactor=1;
    createWindow();
  }
  
  /**
   * Changes the number system to start at one.
   */
  public void changeToBaseOne() {
    numberBase = 1;
  }
  
  /**
   * Sets the title of the frame.
   * 
   * @param title  the title to use in the JFrame
   */
  public void setTitle(String title) {
    pictureFrame.setTitle(title);
  }
  
  public Color inputColor() {
      return ColorChooser.pickAColor();
  }

  /**
   * Creates and initializes the picture frame
   */
  private void createAndInitPictureFrame() {
    pictureFrame = new JFrame(); // creates the JFrame
    pictureFrame.setResizable(true);  // allows the user to resize it
    pictureFrame.getContentPane().setLayout(new BorderLayout()); // uses border layout
    pictureFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // when closed stop
    pictureFrame.setTitle(picture.getTitle());
    PictureExplorerFocusTraversalPolicy newPolicy = new PictureExplorerFocusTraversalPolicy();
    pictureFrame.setFocusTraversalPolicy(newPolicy);
  }
  
  /**
   * Creates the menu bar, menus, and menu items
   */
  private void setUpMenuBar() {
    // creates menu
    menuBar = new JMenuBar();
    zoomMenu = new JMenu("Zoom");
    for (int levelIndex = 0; levelIndex < levels.length; levelIndex++) {
        JMenuItem item = new JMenuItem(levels[levelIndex] + "%");
        zoomMenuItems[levelIndex] = item;
        item.addActionListener(this);
        zoomMenu.add(item);
    }
    menuBar.add(zoomMenu);

    // tester bar
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
    pictureFrame.setJMenuBar(menuBar);
  }

  private void dispose() {
    pictureFrame.dispose();
  }
  
  
  /**
   * Creates and initializes the scrolling image
   */
  private void createAndInitScrollingImage() {
    scrollPane = new JScrollPane();
    
    BufferedImage bimg = picture.getBufferedImage();
    imageDisplay = new ImageDisplay(bimg);
    imageDisplay.addMouseMotionListener(this);
    imageDisplay.addMouseListener(this);
    imageDisplay.setToolTipText("Click a mouse button on a pixel to see the pixel information");
    scrollPane.setViewportView(imageDisplay);
    pictureFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
  }
  
  /**
   * Creates the JFrame and sets everything up.
   */
  private void createWindow() {
    createAndInitPictureFrame();
    setUpMenuBar();
    createInfoPanel();
    createAndInitScrollingImage();
    createAndInitInputBox();
    
    String pictureName = picture.getFileName().substring(picture.getFileName().lastIndexOf("/") + 1);
    inputPanel.add(new JLabel("<html>Viewing " + pictureName + "</html>"));

    pictureFrame.pack();
    pictureFrame.setVisible(true);
  }
  
  /**
   * Sets up the next and previous buttons for the pixel location information.
   */
  private void setUpNextAndPreviousButtons() {
    Icon prevIcon = new ImageIcon(DigitalPicture.class.getResource("leftArrow.gif"), 
                                  "previous index");
    Icon nextIcon = new ImageIcon(DigitalPicture.class.getResource("rightArrow.gif"), 
                                  "next index");

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
  
  /**
   * Creates the pixel location panel.
   * 
   * @param labelFont  the font for the labels
   * @return           the location panel
   */
  public JPanel createLocationPanel(Font labelFont) {
    JPanel locationPanel = new JPanel();
    locationPanel.setLayout(new FlowLayout());
    Box hBox = Box.createHorizontalBox();
    
    rowLabel = new JLabel("Row:");
    colLabel = new JLabel("Column:");
    
    colValue = new JTextField(Integer.toString(colIndex + numberBase),6);
    colValue.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        displayPixelInformation(colValue.getText(),rowValue.getText());
      }
    });
    rowValue = new JTextField(Integer.toString(rowIndex + numberBase),6);
    rowValue.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        displayPixelInformation(colValue.getText(),rowValue.getText());
      }
    });
    
    setUpNextAndPreviousButtons();
    
    colLabel.setFont(labelFont);
    rowLabel.setFont(labelFont);
    colValue.setFont(labelFont);
    rowValue.setFont(labelFont);
    
    hBox.add(Box.createHorizontalGlue());
    hBox.add(rowLabel);
    hBox.add(rowPrevButton);
    hBox.add(rowValue);
    hBox.add(rowNextButton);
    hBox.add(Box.createHorizontalStrut(10));
    hBox.add(colLabel);
    hBox.add(colPrevButton);
    hBox.add(colValue);
    hBox.add(colNextButton);
    locationPanel.add(hBox);
    hBox.add(Box.createHorizontalGlue());
    
    return locationPanel;
  }

  public void createAndInitInputBox() {
    inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
    inputPanel.setPreferredSize(new Dimension(200, 1));
    JScrollPane inputScrollPane = new JScrollPane(inputPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    pictureFrame.getContentPane().add(inputScrollPane, BorderLayout.EAST);
  }

  public void log(String s) {
    inputPanel.add(new JLabel("<html>" + s + "</html>"));
  }

  public String input(String prompt) {
    log(prompt);
    JTextField inputField = new JTextField();
    inputPanel.add(inputField);
    CountDownLatch latch = new CountDownLatch(1);
    inputField.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            latch.countDown();
        }
    });
    try {
      latch.await();
    } catch(InterruptedException e) {
        e.printStackTrace();
    }
    return inputField.getText().strip();
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
  
  /**
   * Creates the color information panel.
   * 
   * @param labelFont  the font to use for labels
   * @return           the color information panel
   */
  private JPanel createColorInfoPanel(Font labelFont) {
    JPanel colorInfoPanel = new JPanel();
    colorInfoPanel.setLayout(new FlowLayout());
    
    Pixel pixel = new Pixel(picture,colIndex,rowIndex);
    
    rValue = new JLabel("R: " + pixel.getRed());
    gValue = new JLabel("G: " + pixel.getGreen());
    bValue = new JLabel("B: " + pixel.getBlue());
    
    colorLabel = new JLabel("Color at location: ");
    colorPanel = new JPanel();
    colorPanel.setBorder(new LineBorder(Color.black,1));
    
    colorPanel.setBackground(pixel.getColor());
    
    rValue.setFont(labelFont);
    gValue.setFont(labelFont);
    bValue.setFont(labelFont);
    colorLabel.setFont(labelFont);
    colorPanel.setPreferredSize(new Dimension(25,25));
    
    colorInfoPanel.add(rValue);
    colorInfoPanel.add(gValue);
    colorInfoPanel.add(bValue);
    colorInfoPanel.add(colorLabel);
    colorInfoPanel.add(colorPanel);
    
    return colorInfoPanel; 
  }
  
  /**
   * Creates the North JPanel with all the pixel location and color information.
   */
  private void createInfoPanel() {
    // creates the info panel and set the layout
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BorderLayout());
    
    Font largerFont = new Font(infoPanel.getFont().getName(),
                               infoPanel.getFont().getStyle(), 14);
    
    JPanel locationPanel = createLocationPanel(largerFont);
    
    JPanel colorInfoPanel = createColorInfoPanel(largerFont);
    
    infoPanel.add(BorderLayout.NORTH,locationPanel);
    infoPanel.add(BorderLayout.SOUTH,colorInfoPanel); 
    
    pictureFrame.getContentPane().add(BorderLayout.NORTH,infoPanel);
  } 
  
  /**
   * Checks that the current position is in the viewing area and if
   * not scrolls to center the current position if possible.
   */
  public void checkScroll() {
    // gets the x and y position in pixels
    int xPos = (int) (colIndex * zoomFactor); 
    int yPos = (int) (rowIndex * zoomFactor); 
    
    // only does this if the image is larger than normal
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
  
  /**
   * Zooms in the on picture by scaling the image.  It is extremely memory intensive.
   * 
   * @param factor  the amount to zoom by
   */
  public void zoom(double factor) {
    // saves the current zoom factor
    zoomFactor = factor;
    
    // calculates the new width and height and get an image that size
    int width = (int) (picture.getWidth()*zoomFactor);
    int height = (int) (picture.getHeight()*zoomFactor);
    BufferedImage bimg = picture.getBufferedImage();
    
    // sets the scroll image icon to the new image
    imageDisplay.setImage(bimg.getScaledInstance(width, height, Image.SCALE_DEFAULT));
    imageDisplay.setCurrentX((int) (colIndex * zoomFactor));
    imageDisplay.setCurrentY((int) (rowIndex * zoomFactor));
    imageDisplay.revalidate();
    checkScroll();  // checks if need to reposition scroll
  }
  
  /**
   * Repaints the image on the scrollpane.  
   */
  public void repaint() {
    pictureFrame.repaint();
  }
  
  //****************************************//
  //               Event Listeners          //
  //****************************************//
  
  /**
   * Called when the mouse is dragged (button held down and moved)
   * 
   * @param e  the mouse event
   */
  public void mouseDragged(MouseEvent e) {
    displayPixelInformation(e);
  }
  
  /**
   * Checks if the given x and y are in the picture.
   * 
   * @param column  the horizontal value
   * @param row     the vertical value
   * @return        true if the row and column are in the picture;
   *                false otherwise
   */
  private boolean isLocationInPicture(int column, int row) {
    boolean result = false; // the default is false
    if (column >= 0 && column < picture.getWidth() &&
        row >= 0 && row < picture.getHeight())
      result = true;
    
    return result;
  }
  
  /**
   * Displays the pixel information from the passed x and y but
   * also converts x and y from strings.
   * 
   * @param xString  the x value as a string from the user
   * @param yString  the y value as a string from the user
   */
  public void displayPixelInformation(String xString, String yString) {
    int x = -1;
    int y = -1;
    try {
      x = Integer.parseInt(xString);
      x = x - numberBase;
      y = Integer.parseInt(yString);
      y = y - numberBase;
    } catch (Exception ex) {
    }
    
    if (x >= 0 && y >= 0) {
      displayPixelInformation(x,y);
    }
  }
  
  /**
   * Displays pixel information for the passed x and y.
   * 
   * @param pictureX  the x value in the picture
   * @param pictureY  the y value in the picture
   */
  private void displayPixelInformation(int pictureX, int pictureY) {
    // checks that this x and y are in range
    if (isLocationInPicture(pictureX, pictureY)) {
      // saves the current x and y index
      colIndex = pictureX;
      rowIndex = pictureY;
      
      // gets the pixel at the x and y
      Pixel pixel = new Pixel(picture,colIndex,rowIndex);
      
      // sets the values based on the pixel
      colValue.setText(Integer.toString(colIndex  + numberBase));
      rowValue.setText(Integer.toString(rowIndex + numberBase));
      rValue.setText("R: " + pixel.getRed());
      gValue.setText("G: " + pixel.getGreen());
      bValue.setText("B: " + pixel.getBlue());
      colorPanel.setBackground(new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue())); 
    } else {
      clearInformation();
    }
    
    // notifies the image display of the current x and y
    imageDisplay.setCurrentX((int) (colIndex * zoomFactor));
    imageDisplay.setCurrentY((int) (rowIndex * zoomFactor));
  }
  
  /**
   * Displays pixel information based on a mouse event.
   * 
   * @param e  a mouse event
   */
  private void displayPixelInformation(MouseEvent e) {
    // gets the cursor x and y
    int cursorX = e.getX();
    int cursorY = e.getY();
    
    // gets the x and y in the original (not scaled image)
    int pictureX = (int) (cursorX / zoomFactor + numberBase);
    int pictureY = (int) (cursorY / zoomFactor + numberBase);
    
    // displays the information for this x and y
    displayPixelInformation(pictureX,pictureY);
  }
  
  /**
   * Clears the labels and current color and resets the current index to -1.
   */
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
  
  /**
   * Called when the mouse is moved with no buttons down.
   * 
   * @param e  the mouse event
   */
  public void mouseMoved(MouseEvent e)
  {}
  
  /**
   * Called when the mouse is clicked.
   * 
   * @param e  the mouse event
   */
  public void mouseClicked(MouseEvent e) {
    displayPixelInformation(e);
  }
  
  /**
   * Called when the mouse button is pushed down.
   * 
   * @param e  the mouse event
   */ 
  public void mousePressed(MouseEvent e) {
    displayPixelInformation(e);
  }
  
  /**
   * Called when the mouse button is released.
   * 
   * @param e  the mouse event
   */
  public void mouseReleased(MouseEvent e)
  {}
  
  /**
   * Called when the component is entered (mouse moves over it).
   * 
   * @param e  the mouse event
   */
  public void mouseEntered(MouseEvent e)
  {}
  
  /**
   * Called when the mouse moves over the component.
   * @param e the mouse event
   */
  public void mouseExited(MouseEvent e)
  {}
  
  /**
   * Enables all menu commands.
   */
  private void enableZoomItems() {
    for (JMenuItem item : zoomMenuItems) {
        item.setEnabled(true);
    }
  }
  
  /**
   * Controls the zoom menu bar.
   *
   * @param a  the ActionEvent 
   */
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
        } catch (Exception e) {
            // do nothing
        }
    }
  }
  
  /**
   * PictureExplorerFocusTraversalPolicy establishes the focus for the textfields.
   */
  private class PictureExplorerFocusTraversalPolicy extends FocusTraversalPolicy {
    /**
     * Gets the next component for focus.
     */
    public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
      if (aComponent.equals(colValue))
        return rowValue;
      else 
        return colValue;
    }
    
    /**
     * Gets the previous component for focus.
     */
    public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
      if (aComponent.equals(colValue))
        return rowValue;
      else 
        return colValue;
    }
    
    public Component getDefaultComponent(Container focusCycleRoot) {
      return colValue;
    }
    
    public Component getLastComponent(Container focusCycleRoot) {
      return rowValue;
    }
    
    public Component getFirstComponent(Container focusCycleRoot) {
      return colValue;
    }
  }
  
  /**
   * Main method to test a Picture object.
   * 
   * The tester constructs a Picture object and invokes the explore method.
   */
  public static void main( String args[]) {
    Picture pic = new Picture("images/beach.jpg");
    pic.explore();
  }
}
