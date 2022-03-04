import java.awt.Color;

/**
 * Pixel references a pixel in a picture. Pixel stands for picture element 
 * where picture is abbreviated pix.  A pixel has a column (x) and row (y)
 * location in a picture.  A pixel knows how to get and set the red, green,
 * blue, and alpha values in the picture.  A pixel also knows how to get 
 * and set the color using a Color object.
 * 
 * @author Barb Ericson ericson@cc.gatech.edu
 */
public class Pixel {
  /** the digital picture this pixel belongs to */
  private DigitalPicture picture;
  
  /** the x (column) location of this pixel in the picture; (0,0) is top left */
  private int x; 
  
  /** the y (row) location of this pixel in the picture; (0,0) is top left */
  private int y; 
  
  /** 
   * A constructor that takes the x and y location for the pixel and
   * the picture the pixel is coming from.
   * 
   * @param picture  the picture that the pixel is in
   * @param x        the x location of the pixel in the picture
   * @param y        the y location of the pixel in the picture
   */
  public Pixel(DigitalPicture picture, int x, int y) {
    // sets the picture
    this.picture = picture;
    
    // sets the x location
    this.x = x;
    
    // sets the y location
    this.y = y;
    
  }
  
  /**
   * Gets the x location of this pixel.
   * 
   * @return  the x location of the pixel in the picture
   */
  public int getX() { return x; }
  
  /**
   * Gets the y location of this pixel.
   * 
   * @return  the y location of the pixel in the picture
   */
  public int getY() { return y; }
  
  /** 
   * Gets the row (y value).
   * 
   * @return  the row (y value) of the pixel in the picture
   */
  public int getRow() { return y; }
  
  /** 
   * Gets the column (x value).
   * 
   * @return  the column (x value) of the pixel 
   */
  public int getCol() { return x; }
  
  /**
   * Gets the amount of alpha (transparency) at this pixel.
   * It will be from 0-255.
   * 
   * @return  the amount of alpha (transparency)
   */
  public int getAlpha() {
    /* gets the value at the location from the picture as a 32 bit int
     * with alpha, red, green, blue each taking 8 bits from left to right
     */
    int value = picture.getBasicPixel(x,y);

    // gets the alpha value (starts at 25 so shift right 24)
    // then AND it with all 1's for the first 8 bits to keep
    // end up with from 0 to 255 
    int alpha = (value >> 24) & 0xff;
    
    return alpha;
  }
    
  
  /**
   * Gets the amount of red at this pixel.  It will be from 0-255 with
   * 0 being no red and 255 being as much red as you can have.
   * 
   * @return  the amount of red from 0 for none to 255 for max
   */
  public int getRed() { 
    /* gets the value at the location from the picture as a 32 bit int
     * with alpha, red, green, blue each taking 8 bits from left to right
     */
    int value = picture.getBasicPixel(x,y);

    // gets the red value (starts at 17 so shift right 16)
    // then AND it with all 1's for the first 8 bits to 
    // end up with a resulting value from 0 to 255 
    int red = (value >> 16) & 0xff;
    
    return red;
  }
  
  /**
   * Gets the red value from a pixel represented as an int.
   * 
   * @param value  the color value as an int
   * @return       the amount of red
   */
  public static int getRed(int value) {
    int red = (value >> 16) & 0xff;
    return red;
  }
  
  /**
   * Gets the amount of green at this pixel.  It will be from 0-255 with
   * 0 being no green and 255 being as much green as you can have.
   * 
   * @return  the amount of green from 0 for none to 255 for max
   */
  public int getGreen() { 
    /* gets the value at the location from the picture as a 32 bit int
     * with alpha, red, green, blue each taking 8 bits from left to right
     */
    int value = picture.getBasicPixel(x,y);

    // gets the green value (starts at 9 so shift right 8)
    int green = (value >>  8) & 0xff;
    
    return green;
  }
  
  /**
   * Gets the green value from a pixel represented as an int.
   * 
   * @param value  the color value as an int
   * @return       the amount of green
   */
  public static int getGreen(int value) {
    int green = (value >> 8) & 0xff;
    return green;
  }
  
  /**
   * Gets the amount of blue at this pixel.  It will be from 0-255 with 
   * 0 being no blue and 255 being as much blue as you can have.
   * 
   * @return  the amount of blue from 0 for none to 255 for max
   */
  public int getBlue() { 
    /* gets the value at the location from the picture as a 32 bit int
     * with alpha, red, green, blue each taking 8 bits from left to right
     */
    int value = picture.getBasicPixel(x,y);

    // gets the blue value (starts at 0 so no shift required)
    int blue = value & 0xff;
    
    return blue;
  }
  
  /**
   * Gets the blue value from a pixel represented as an int
   * 
   * @param value  the color value as an int
   * @return       the amount of blue
   */
  public static int getBlue(int value) {
    int blue = value & 0xff;
    return blue;
  }
  
  /**
   * Gets a Color object that represents the color at this pixel.
   * 
   * @return a color object that represents the pixel color
   */
  public Color getColor() { 
    /* gets the value at the location from the picture as a 32 bit int
     * with alpha, red, green, blue each taking 8 bits from left to right
     */
    int value = picture.getBasicPixel(x,y);

    // gets the red value (starts at 17 so shift right 16)
    // then AND it with all 1's for the first 8 bits to 
    // end up with a resulting value from 0 to 255 
    int red = (value >> 16) & 0xff;
    
    // gets the green value (starts at 9 so shift right 8)
    int green = (value >>  8) & 0xff;
    
    // gets the blue value (starts at 0 so no shift required)
    int blue = value & 0xff;
    
    return new Color(red,green,blue);
  }
  
  /**
   * Sets the pixel color to the passed in color object.
   * 
   * @param newColor the new color to use
   */
  public void setColor(Color newColor) {
    // sets the red, green, and blue values
    int red = newColor.getRed();
    int green = newColor.getGreen();
    int blue = newColor.getBlue();
    
    // updates the associated picture
    updatePicture(this.getAlpha(),red,green,blue);
  }
  
  /**
   * Updates the picture based on the passed color values for this pixel
   * 
   * @param alpha  the alpha (transparency) at this pixel
   * @param red    the red value for the color at this pixel
   * @param green  the green value for the color at this pixel
   * @param blue   the blue value for the color at this pixel
   */
  public void updatePicture(int alpha, int red, int green, int blue) {
    // creates a 32 bit int with alpha, red, green blue from left to right
    int value = (alpha << 24) + (red << 16) + (green << 8) + blue;
    
    // updates the picture with the int value
    picture.setBasicPixel(x,y,value);
  }
  
  /**
   * Corrects a color value to be within 0 to 255.
   * 
   * @param value  the value to use
   * @return       a value within 0 to 255
   */
  private static int correctValue(int value) {
    if (value < 0)
      value = 0;
    if (value > 255)
      value = 255;
    return value;
  }
  
  /**
   * Sets the red to a new red value.
   * 
   * @param value  the new value to use
   */
  public void setRed(int value)
  {
    // sets the red value to the corrected value
    int red = correctValue(value);
    
    // updates the pixel value in the picture
    updatePicture(getAlpha(), red, getGreen(), getBlue());
  } 
  
  /**
   * Sets the green to a new green value.
   * 
   * @param value  the new value to use
   */
  public void setGreen(int value)
  {
    // sets the green value to the corrected value
    int green = correctValue(value);
    
    // updates the pixel value in the picture
    updatePicture(getAlpha(), getRed(), green, getBlue());
  } 
  
  /**
   * Sets the blue to a new blue value.
   * 
   * @param value  the new value to use
   */
  public void setBlue(int value) {
    // sets the blue value to the corrected value
    int blue = correctValue(value);
    
    // updates the pixel value in the picture
    updatePicture(getAlpha(), getRed(), getGreen(), blue);
  } 
  
  /**
   * Sets the alpha (transparency) to a new alpha value.
   * 
   * @param value  the new value to use
   */
  public void setAlpha(int value) {
    // makes sure that the alpha is from 0 to 255 
    int alpha = correctValue(value);
    
    // updates the associated picture
    updatePicture(alpha, getRed(), getGreen(), getBlue());
  } 
  
  /**
   * Gets the distance between this pixel's color and the passed color.
   *
   * @param testColor  the color to compare to
   * @return           the distance between this pixel's color and the passed color
   */
  public double colorDistance(Color testColor) {
    double redDistance = this.getRed() - testColor.getRed();
    double greenDistance = this.getGreen() - testColor.getGreen();
    double blueDistance = this.getBlue() - testColor.getBlue();
    double distance = Math.sqrt(redDistance * redDistance + 
                                greenDistance * greenDistance +
                                blueDistance * blueDistance);
    return distance;
  }
 
  /**
   * Computes the color distances between two color objects.
   *
   * @param color1  a color object
   * @param color2  a color object
   * @return        the distance between the two colors
   */
  public static double colorDistance(Color color1,Color color2) {
    double redDistance = color1.getRed() - color2.getRed();
    double greenDistance = color1.getGreen() - color2.getGreen();
    double blueDistance = color1.getBlue() - color2.getBlue();
    double distance = Math.sqrt(redDistance * redDistance + 
                                greenDistance * greenDistance +
                                blueDistance * blueDistance);
    return distance;
  }
 
  /**
   * Gets the average of the colors of this pixel.
   * 
   * @return  the average of the red, green, and blue values
   */
  public double getAverage() {
    double average = (getRed() + getGreen() + getBlue()) / 3.0;
    return average;
  }
  
  /**
   * Returns a string with information about this pixel.
   * 
   * @return  a string with information about this pixel
   */
  public String toString() {
    return "Pixel row=" + getRow() + " col=" + getCol() +
      " red=" + getRed() + " green=" + getGreen() + " blue=" + getBlue();
  }

}