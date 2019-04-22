import java.io.File;

public class Test {
    public static void main(String[] args) {
        File file = new File(args[0]);
        String[] allFileName = file.list();
        int len = 0;
        if (allFileName != null) {
            len = allFileName.length;
        }
        for (int i = 0; i < len; i++) {
            if (allFileName[i].contains(".vm")) {
                System.out.println(allFileName[i]);
            }

        }
    }
}
