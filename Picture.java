import java.awt.image.BufferedImage;

/**
 * Represents a picture.  This class inherits from SimplePicture and
 * allows the student to add functionality to the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture {
  
  /**
   * Default constructor.  Not needed but shows students the implicit
   * call to super().  Child constructors always call a parent constructor.
   */
  public Picture () {
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture.
   * 
   * @param fileName  the name of the file to create the picture from
   */
  public Picture(String fileName) {
    // lets the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height.
   * 
   * @param height  the height of the desired picture
   * @param width   the width of the desired picture
   */
  public Picture(int height, int width) {
    // lets the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a copy of that picture.
   * 
   * @param copyPicture  the picture to copy
   */
  public Picture(Picture copyPicture) {
    // lets the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image.
   * 
   * @param image  the buffered image to use
   */
  public Picture(BufferedImage image) {
    super(image);
  }
  
  /**
   *  Copies from the passed fromPic to the specified startRow and
   *  startCol in the current picture.
   * 
   * @param fromPic   the picture to copy from
   * @param startRow  the start row to copy to
   * @param startCol  the start col to copy to
   */
  public void copy(Picture fromPic, int startRow, int startCol) {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length && toRow < toPixels.length; 
         fromRow++, toRow++) {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length && toCol < toPixels[0].length;  
           fromCol++, toCol++) {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Creates a collage of several pictures. */
  public void createCollage() {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.write("collage.jpg");
  }

  /**
   * Returns a string with information about this picture.
   * 
   * @return  a string with fileName, height, and width
   */
  public String toString() {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() + " width " + getWidth();
    return output;
  }
  
  /************************************ 6.C Lab ************************************/
  
  /** 
   * Sets the blue to 0.
   */
  public void zeroBlue() {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels) {
      for (Pixel pixelObj : rowArray) {
        pixelObj.setBlue(0);
      }
    }
  }

  /** 
   * Sets the red and green values of each pixel to 0.
   */
  public void keepOnlyBlue() {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] row : pixels) {
      for (Pixel pixel : row) {
        pixel.setGreen(0);
        pixel.setRed(0);
      }
    }
  }
  
  
  /** 
   * Sets all red, green, and blue values of each pixel to 255 - current value.
   */
  public void negate() {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] row : pixels) {
      for (Pixel pixel : row) {
        pixel.setRed(255 - pixel.getRed());
        pixel.setGreen(255 - pixel.getGreen());
        pixel.setBlue(255 - pixel.getBlue());
      }
    }
  }
  
  
  /** 
   * Sets all red, green, and blue values of each pixel to the average of the current values.
   */
  public void grayscale() {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] row : pixels) {
      for (Pixel pixel : row) {
        int average = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
        pixel.setRed(average);
        pixel.setGreen(average);
        pixel.setBlue(average);
      }
    }
  }
  
  
  /** 
   * Enhances the pixels that are the least like water.
   */
  public void fixUnderwater() {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] row : pixels) {
      for (Pixel pixel : row) {
        if (pixel.getBlue() < 170 && pixel.getRed() < 20) {
          pixel.setGreen(pixel.getGreen() * 3 / 4);
        } else {
          pixel.setBlue(230);
        }
      }
    }
  }
  

  /************************************ 6.D Lab ************************************/

  /** 
   * Mirrors the picture around a vertical mirror in the center of the picture from left to right.
   */
  public void mirrorVerticalLeftToRight() {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < width / 2; col++) {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** 
   * Mirrors the picture around a vertical mirror in the center of the picture from right to left.
   */
  public void mirrorVerticalRightToLeft() {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < width / 2; col++) {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        leftPixel.setColor(rightPixel.getColor());
      }
    } 
  }
  
  
  /** 
   * Mirrors the picture around a horizontal mirror in the center of the picture from top to bottom.
   */
  public void mirrorHorizontalTopToBottom() {
    Pixel[][] pixels = this.getPixels2D();
    for (int row = 0; row < pixels.length / 2; row++) {
      for (int col = 0; col < pixels[0].length; col++) {
        Pixel topPixel = pixels[row][col];
        Pixel bottomPixel = pixels[pixels.length - 1 - row][col];
        bottomPixel.setColor(topPixel.getColor());
      }
    }
  }
  
  
  /** 
   * Mirrors the picture around a horizontal mirror in the center of the picture from bottom to top.
   */
  public void mirrorHorizontalBottomToTop() {
    Pixel[][] pixels = this.getPixels2D();
    for (int row = 0; row < pixels.length / 2; row++) {
      for (int col = 0; col < pixels[0].length; col++) {
        Pixel topPixel = pixels[row][col];
        Pixel bottomPixel = pixels[pixels.length - 1 - row][col];
        topPixel.setColor(bottomPixel.getColor());
      }
    }
  }
  
  
  /** 
   * Mirrors the picture around a diagonal mirror starting in the upper-left corner and continuing at a 45-degree angle.
   */
  public void mirrorDiagonal() {
    Pixel[][] pixels = this.getPixels2D();
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < row; col++) {
        Pixel pixel = pixels[row][col];
        Pixel mirror = pixels[col][row];
        mirror.setColor(pixel.getColor());
      }
    }
  }
  

  /************************************ 6.E Lab ************************************/

  /**
   * Mirrors just part of a picture of a temple.
   */
  public void mirrorTemple() {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    
    // loops through the rows
    for (int row = 27; row < 97; row++) {
      // loops from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++) {
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** 
   * Mirrors just part of a picture of a snowman.
   */
  public void mirrorArms() {
    Pixel[][] pixels = this.getPixels2D();
    
    // loops through the rows
    for (int row = 163; row < 194; row++) {
      for (int col = 100; col < 300; col++) {
        Pixel topPixel = pixels[row][col];      
        Pixel bottomPixel = pixels[194 + (194 - row)][col];
        bottomPixel.setColor(topPixel.getColor());
      }
    }
  }
  
  
  /** 
   * Mirrors just part of a picture of a swan.
   */
  public void mirrorSwan() {
    mirrorVerticalRightToLeft();

    Pixel[][] pixels = this.getPixels2D();
    for (int row = 0; row < 185; row++) {
      for (int col = 291; col < 403; col++) {
        Pixel rightPixel = pixels[row][col];
        Pixel leftPixel = pixels[row][291 - (col - 291)];
        leftPixel.setColor(rightPixel.getColor());
      }
    }
  }


  /************************************ 6.F Lab ************************************/
  
  /**
   * Method to combine two images. Each pixel of the new image is 50% of each of the two original images.
   */
  public void change1() {
      Picture other = new Picture("Chapter-6-Extension/images/koala.jpg");
      Pixel[][] pixels = getPixels2D();
      Pixel[][] otherPixels = other.getPixels2D();
      for (int row = 0; row < pixels.length && row < otherPixels.length; row++) {
        for (int col = 0; col < pixels[0].length && col < otherPixels[0].length; col++) {
          Pixel pixel1 = pixels[row][col];
          Pixel pixel2 = otherPixels[row][col];
          pixel1.setRed((pixel1.getRed() + pixel2.getRed()) / 2);
          pixel1.setGreen((pixel1.getGreen() + pixel2.getGreen()) / 2);
          pixel1.setBlue((pixel1.getBlue() + pixel2.getBlue()) / 2);
        }
      }
  }
  
  /**
   * Method to make an image stripy
   * I purposely made it blue, pink, and yellow and not red, green, and blue because I think it looks cooler
   */
  public void change2() {
    Pixel[][] pixels = getPixels2D();
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < pixels[0].length; col++) {
        if (row % 100 < 33) {
          pixels[row][col].setRed(0);
        } else if (row % 100 < 66) {
          pixels[row][col].setGreen(0);
        } else {
          pixels[row][col].setBlue(0);
        }
      }
    }
  }

  /**
   * Method to do a green screen
   */
  public void change3() {
    Picture background = new Picture("Chapter-6-Extension/images/beach.jpg");
    Pixel[][] pixels = getPixels2D();
    Pixel[][] backgroundPixels = background.getPixels2D();
    for (int row = 0; row < pixels.length && row < backgroundPixels.length; row++) {
      for (int col = 0; col < pixels[0].length && col < backgroundPixels[0].length; col++) {
        Pixel pixel1 = pixels[row][col];
        Pixel pixel2 = backgroundPixels[row][col];
        if (pixel1.getGreen() > pixel1.getRed() && pixel1.getGreen() > pixel1.getBlue()) {
          pixel1.setColor(pixel2.getColor());
        }
      }
    }
  }

  /**
   * Method to make an image have a color fade from right to left
   */
  public void change4() {
    Pixel[][] pixels = getPixels2D();
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < pixels[0].length; col++) {
        Pixel pixel = pixels[row][col];
        pixel.setRed((int) (pixel.getRed() * ((double) col / pixels[0].length)));
        pixel.setBlue((int) (pixel.getBlue() * ((double) (pixels[0].length - col) / pixels[0].length)));
      }
    }
  }

  /**
   * Method to pixelate an image using color averages
   */
  public void change5() {
    Pixel[][] pixels = getPixels2D();
    int chunkSize = 10;
    for (int rowChunk = 0; rowChunk < pixels.length / chunkSize; rowChunk++) {
      for (int colChunk = 0; colChunk < pixels[0].length / chunkSize; colChunk++) {    
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        for (int row = rowChunk * chunkSize; row < rowChunk * chunkSize + chunkSize; row++) {
          for (int col = colChunk * chunkSize; col < colChunk * chunkSize + chunkSize; col++) {
            redSum += pixels[row][col].getRed();
            greenSum += pixels[row][col].getGreen();
            blueSum += pixels[row][col].getBlue();
          }
        }
        int redAverage = redSum / (chunkSize * chunkSize);
        int greenAverage = greenSum / (chunkSize * chunkSize);
        int blueAverage = blueSum / (chunkSize * chunkSize);
        for (int row = rowChunk * chunkSize; row < rowChunk * chunkSize + chunkSize; row++) {
          for (int col = colChunk * chunkSize; col < colChunk * chunkSize + chunkSize; col++) {
            pixels[row][col].setRed(redAverage);
            pixels[row][col].setGreen(greenAverage);
            pixels[row][col].setBlue(blueAverage);
          }
        }
      }
    }
  }
}