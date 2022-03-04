import javax.swing.*;

/**
 * PictureFrame holds a digital picture and displays it using a JFrame.
 * 
 * @author Barb Ericson 
 */
public class PictureFrame {
  /** Main window used as the frame */
  JFrame frame = new JFrame();
  
  /** ImageIcon used to display the picture in the label*/
  ImageIcon imageIcon = new ImageIcon();
  
  /** Label used to display the picture */
  private JLabel label = new JLabel(imageIcon);
  
  /** Digital Picture to display */
  private DigitalPicture picture;
  
  /**
   * Default constructor - needed for subclasses of this class
   */
  public PictureFrame() {
    // sets up the frame
    initFrame();
  }
  
  /**
   * A constructor that takes a picture to display.
   * 
   * @param picture  the digital picture to display in the picture frame
   */
  public PictureFrame(DigitalPicture picture) {
    // sets the current object's picture to the passed in picture
    this.picture = picture;
    
    // sets up the frame
    initFrame();
  }
  
  /**
   * Sets the picture to show in this picture frame.
   * 
   * @param picture  the new picture to use
   */
  public void setPicture(Picture picture) {
    this.picture = picture;
    imageIcon.setImage(picture.getImage());
    frame.pack();
    frame.repaint();
  }
  
  /**
   * Updates the picture frame image with the image in the picture.
   */
  public void updateImage() {
    // only does this if there is a picture
    if (picture != null) {
      // sets the image for the image icon from the picture
      imageIcon.setImage(picture.getImage());
      
      // sets the title of the frame to the title of the picture
      frame.setTitle(picture.getTitle());
    }
  }
  
  /**
   * Updates the picture frame image with the image in the picture and shows it.
   */
  public void updateImageAndShowIt() {
    // first updates the image
    updateImage();
    
    // now makes sure it is shown
    frame.setVisible(true);
  }
  
  /**
   * Makes sure the frame is displayed.
   */
  public void displayImage() {
    frame.setVisible(true);
  }
  
  /**
   * Hides the frame.
   */
  public void hide() {
    frame.setVisible(false);
  }
  
  /**
   * Sets the visible flag on the frame.
   * 
   * @param flag  the flag to use
   */
  public void setVisible(boolean flag) { 
    frame.setVisible(flag);
  }
  
  /**
   * Closes a picture frame.
   */
  public void close() {
    frame.setVisible(false);
    frame.dispose();
  }
  
  /**
   * Sets the title for the picture frame.
   * 
   * @param title  the title to use
   */
  public void setTitle(String title) { 
    frame.setTitle(title);
  }
  
  /**
   * Forces the picture frame to repaint (redraw).
   */
  public void repaint() {
    // makes it visible
    frame.setVisible(true);
    
    // updates the image from the picture
    updateImage();
    
    // tells the JFrame to handle the repaint
    frame.repaint();
  }
  
  /**
   * Initializes the picture frame
   */
  private void initFrame() {
    // sets the image for the picture frame
    updateImage();
      
    // adds the label to the frame
    frame.getContentPane().add(label);
    
    // packs the frame (set the size to as big as it needs to be)
    frame.pack();
    
    // makes the frame visible
    frame.setVisible(true);
  }
  
}