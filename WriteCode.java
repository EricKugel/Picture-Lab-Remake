public class WriteCode {
    public static void main(String[] arg0) {
        String[][] methods = {{"testZeroBlue", "testKeepOnlyBlue", "testNegate", "testGrayscale", "testFixUnderwater"}, {"testMirrorVerticalLeftToRight", "testMirrorVerticalRightToLeft", "testMirrorHorizontalTopToBottom", "testMirrorHorizontalBottomToTop", "testMirrorDiagonal"}, {"testMirrorTemple", "testMirrorArms", "testMirrorSwan"}, {"testChange1", "testChange2"}};
        String output = "";
        for (int labIndex = 0; labIndex < methods.length; labIndex++) {
            for (int methodIndex = 0; methodIndex < methods[labIndex].length; methodIndex++) {
                String method = methods[labIndex][methodIndex];
                output += "else if (method.equals(\"" + method + "\")) {\n";
                output += "\tPictureTester." + method + "();\n}  ";
            }
        }
        System.out.println(output);
    }
}
