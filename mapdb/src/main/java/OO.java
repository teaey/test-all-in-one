import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaofei.wxf
 */
public class OO implements Serializable{
    public OO(int i, int j, String b, Date d) {
        this.i = i;
        this.j = j;
        this.b = b;
        this.d = d;
    }

    private int i;
    private int j;
    private String b;
    private Date d;
}
