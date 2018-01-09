package Test;
import coop.Nodelist;
import coop.RandWfile;

/**
 * Created by liangdengyu on 2017/12/9.
 */
public class DataTest {
    public static void main(String [] args){
        double arg =0.1;
        StringBuffer str = new StringBuffer();
        double data =0;
        Nodelist test =  Nodelist.init();
        for (int i=0;i<=100;i++) {
          double res=test.utility_src(data);
          data+=0.01;
            str.append(data+" "+res+"\r\n");

        }
        RandWfile.writefile("datatest\\srctest"+ ".txt", str.toString(), false);



    }
}
