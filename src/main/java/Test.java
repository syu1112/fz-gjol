import com.fz.gjol.ts.DMPlug;
import com.jacob.com.Dispatch;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Dispatch.call(DMPlug.com, "Reg", "allen1112b46ddfa453d1dc7615fa8c255df64676", "pToc");
        System.out.println(Dispatch.call(DMPlug.com, "WaitKey", 81,0).getInt());

        String findPicE = Dispatch.call(DMPlug.com, "FindPicE", 0, 0, 1920, 1080, "D:/dy_0.bmp", "000000", 0.9, 0).getString();
        System.out.println("已定位!"+findPicE);
        String[] findPicEs = findPicE.split("\\|");

        int x1 = Integer.parseInt(findPicEs[1])-100;
        int y1 = Integer.parseInt(findPicEs[2])-100;
        int x2 = Integer.parseInt(findPicEs[1])+200;
        int y2 = Integer.parseInt(findPicEs[2])+200;
        System.out.println("开始寻找鱼鳔...");
        String findPicE2 = Dispatch.call(DMPlug.com, "FindPicE", x1, y1, x2, y2, "D:/dy_yb.bmp", "000000", 1.0, 0).getString();
        System.out.println(findPicE2);
        String[] split = findPicE2.split("\\|");
        Dispatch.call(DMPlug.com, "Capture", Integer.parseInt(split[1])-50, Integer.parseInt(split[2])-50, Integer.parseInt(split[1])-10, Integer.parseInt(split[2])-10, "D:/test.bmp");

        System.out.println("开始寻找鱼线...");
        String findPicE3 = Dispatch.call(DMPlug.com, "FindPicE", Integer.parseInt(split[1])-50, Integer.parseInt(split[2])-50, Integer.parseInt(split[1])-10, Integer.parseInt(split[2])-10, "D:/dy_2.bmp", "000000", 0.9, 0).getString();
        System.out.println(findPicE3);
        String[] findPicE3s = findPicE3.split("\\|");
        Dispatch.call(DMPlug.com, "Capture", Integer.parseInt(findPicE3s[1])-20, Integer.parseInt(findPicE3s[2])-20, Integer.parseInt(findPicE3s[1])+20, Integer.parseInt(findPicE3s[2])+20, "D:/test1.bmp");
//
//        Dispatch.call(DMPlug.com, "Capture",x1, y1, x2, y2, "D:/test.bmp");
//        if(Integer.parseInt(findPicEs[1]) > 0) {
//            String findColorE = Dispatch.call(DMPlug.com, "FindColorE", x1, y1, x2, y2, "ffee62-ffee62", 1.0, 0).getString();
//            System.out.println(findColorE);
//
////            String[] findColorEs = findColorE.split("\\|");
////            //Dispatch.call(DMPlug.com, "Capture", Integer.parseInt(findColorEs[0])-100, Integer.parseInt(findColorEs[1])-100, Integer.parseInt(findColorEs[0])+100, Integer.parseInt(findColorEs[1])+100, "D:/test.bmp");
////            if(Integer.parseInt(findColorEs[0]) < 0) {
////                System.out.println("出现鱼鳔!" + findColorEs[0] +":"+findColorEs[1]);
////                System.out.println(Dispatch.call(DMPlug.com, "GetColor", Integer.parseInt(findColorEs[0])-2, Integer.parseInt(findColorEs[1])-2));
////            }
//        }

    }
}
