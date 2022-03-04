/**
 * This class contains class methods that will help you test the Picture class 
 * methods.  Uncomment the methods and the code in the main method to test.
 * 
 * @author Barbara Ericson 
 */
public class PictureTester {
  /** 
   * Main method for testing.
   */
  public static void main(String[] args) {
    Picture pic = new Picture("Chapter-6-Extension/images/beach.jpg");
    pic.explore();
  }

  public static void run(String method) {
    if (method.equals("testZeroBlue")) {
      PictureTester.testZeroBlue();
    }  else if (method.equals("testKeepOnlyBlue")) {
        PictureTester.testKeepOnlyBlue();
    }  else if (method.equals("testNegate")) {
        PictureTester.testNegate();
    }  else if (method.equals("testGrayscale")) {
        PictureTester.testGrayscale();
    }  else if (method.equals("testFixUnderwater")) {
        PictureTester.testFixUnderwater();
    }  else if (method.equals("testMirrorVerticalLeftToRight")) {  
        PictureTester.testMirrorVerticalLeftToRight();
    }  else if (method.equals("testMirrorVerticalRightToLeft")) {  
        PictureTester.testMirrorVerticalRightToLeft();
    }  else if (method.equals("testMirrorHorizontalTopToBottom")) {
        PictureTester.testMirrorHorizontalTopToBottom();       
    }  else if (method.equals("testMirrorHorizontalBottomToTop")) {
        PictureTester.testMirrorHorizontalBottomToTop();
    }  else if (method.equals("testMirrorDiagonal")) {
        PictureTester.testMirrorDiagonal();
    }  else if (method.equals("testMirrorTemple")) {
        PictureTester.testMirrorTemple();
    }  else if (method.equals("testMirrorArms")) {
        PictureTester.testMirrorArms();
    }  else if (method.equals("testMirrorSwan")) {
        PictureTester.testMirrorSwan();
    }  else if (method.equals("testChange1")) {
        PictureTester.testChange1();
    }  else if (method.equals("testChange2")) {
        PictureTester.testChange2();
    }
  }
  
  
  /****************************** Private Helper Methods *****************************/
  /************************************* 6.C Lab *************************************/
  
  /**
   * Tests zeroBlue method.
   */
  private static void testZeroBlue() {
    Picture pic = new Picture("Chapter-6-Extension/images/beach.jpg");
    pic.explore(); // displays original picture
    pic.zeroBlue();
    pic.explore(); // displays picture revised by zeroBlue method
  }
  
  /**
   * Tests keepOnlyBlue method.
   */
  private static void testKeepOnlyBlue() {
    Picture pic = new Picture("Chapter-6-Extension/images/beach.jpg");
    pic.explore(); // displays original picture
    pic.keepOnlyBlue();
    pic.explore(); // displays picture revised by keepOnlyBlue method
  }
  
  /**
   * Tests negate method.
   */
  private static void testNegate() {
    Picture pic = new Picture("Chapter-6-Extension/images/gorge.jpg");
    pic.explore(); // displays original picture
    pic.negate();
    pic.explore(); // displays picture revised by negate method
  }
  
  /**
   * Tests grayscale method.
   */
  private static void testGrayscale() {
    Picture pic = new Picture("Chapter-6-Extension/images/arch.jpg");
    pic.explore(); // displays original picture
    pic.grayscale();
    pic.explore(); // displays picture revised by grayscale method
  }
  
  /**
   * Tests fixUnderwater method.
   */
  private static void testFixUnderwater() {
    Picture pic = new Picture("Chapter-6-Extension/images/water.jpg");
    pic.explore(); // displays original picture
    pic.fixUnderwater();
    pic.explore(); // displays picture revised by fixUnderwater method
  }
  
  /************************************* 6.D Lab *************************************/
  
  /** 
   * Tests mirrorVerticalLeftToRight method.
   */
  private static void testMirrorVerticalLeftToRight() {
    Picture pic = new Picture("Chapter-6-Extension/images/caterpillar.jpg");
    pic.explore(); // displays original picture
    pic.mirrorVerticalLeftToRight();
    pic.explore(); // displays picture revised by mirrorVertical method
  }
  
  /** 
   * Tests mirrorVerticalRightToLeft method.
   */
  private static void testMirrorVerticalRightToLeft() {
    Picture pic = new Picture("Chapter-6-Extension/images/koala.jpg");
    pic.explore(); // displays original picture
    pic.mirrorVerticalRightToLeft();
    pic.explore(); // displays picture revised by mirrorVerticalRightToLeft method
  }
  
  /** 
   * Tests mirrorHorizontalTopToBottom method.
   */
  private static void testMirrorHorizontalTopToBottom() {
    Picture pic = new Picture("Chapter-6-Extension/images/redMotorcycle.jpg");
    pic.explore(); // displays original picture
    pic.mirrorHorizontalTopToBottom();
    pic.explore(); // displays picture revised by mirrorHorizontalTopToBottom method
  }
  
   /** 
   * Tests mirrorHorizontalBottomToTop method.
   */
  private static void testMirrorHorizontalBottomToTop() {
    Picture pic = new Picture("Chapter-6-Extension/images/redMotorcycle.jpg");
    pic.explore(); // displays original picture
    pic.mirrorHorizontalBottomToTop();
    pic.explore(); // displays picture revised by mirrorHorizontalBottomToTop method
  }
  
  /** 
   * Tests mirrorDiagonal method.
   */
  private static void testMirrorDiagonal() {
    Picture pic = new Picture("Chapter-6-Extension/images/beach.jpg");
    pic.explore(); // displays original picture
    pic.mirrorDiagonal();
    pic.explore(); // displays picture revised by mirrorDiagonal method
    pic = new Picture("Chapter-6-Extension/images/butterfly.jpg");
    pic.explore(); // displays original picture
    pic.mirrorDiagonal();
    pic.explore(); // displays picture revised by mirrorDiagonal method
  }
  
  /************************************* 6.E Lab *************************************/

  /** 
   * Tests mirrorTemple method.
   */
  private static void testMirrorTemple() {
    Picture pic = new Picture("Chapter-6-Extension/images/temple.jpg");
    pic.explore(); // displays original picture
    pic.mirrorTemple();
    pic.explore(); // displays picture revised by mirrorTemple method
  }
  
  /** 
   * Tests mirrorTemple method.
   */
  private static void testMirrorArms() {
    Picture pic = new Picture("Chapter-6-Extension/images/snowman.jpg");
    pic.explore(); // displays original picture
    pic.mirrorArms();
    pic.explore(); // displays picture revised by mirrorArms method
  }
  
  /** 
   * Tests mirrorSwan method.
   */
  private static void testMirrorSwan() {
    Picture pic = new Picture("Chapter-6-Extension/images/swan.jpg");
    pic.explore(); // displays original picture
    pic.mirrorSwan();
    pic.explore(); // displays picture revised by mirrorSwan method
  }
  
  /************************************* 6.F Lab *************************************/
  
  /** 
   * Tests change1 method.
   */
  private static void testChange1() {
    Picture pic = new Picture("Chapter-6-Extension/images/beach.jpg");
    pic.change1();
    pic.explore();
  }
  
  /**
   * Tests change2 method.
   */
  private static void testChange2() {
    Picture pic = new Picture("Chapter-6-Extension/images/butterfly.jpg");
    pic.explore();
    pic.change2();
    pic.explore();
  }

  /**
   * Tests change3 method.
   */
  private static void testChange3() {
    Picture pic = new Picture("Chapter-6-Extension/images/whiteFlower.jpg");
    pic.explore();
    pic.change3();
    pic.explore();
  }
  
  /**
   * Tests change4 method.
   */
  private static void testChange4() {
    Picture pic = new Picture("Chapter-6-Extension/images/beach.jpg");
    pic.explore();
    pic.change4();
    pic.explore();
  }

  /**
   * Tests change5 method.
   */
  private static void testChange5() {
    Picture pic = new Picture("Chapter-6-Extension/images/redMotorcycle.jpg");
    pic.explore();
    pic.change5();
    pic.explore();
  }
}