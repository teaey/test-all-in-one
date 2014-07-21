import java.io.*;

/**
 * <p>Company: www.taobao.com</p>
 *
 * @author wxf
 * @date 14-6-16
 */
public class JdkSeril implements Serializable {
    private String message;

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        System.out.println("writeObject invoked");
        out.writeObject(this.message == null ? "hohohahaha" : this.message);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException,
        ClassNotFoundException {
        System.out.println("readObject invoked");
        this.message = (String) in.readObject();
        System.out.println("got message:" + message);
    }

    private Object writeReplace() throws ObjectStreamException {
        System.out.println("writeReplace invoked");
        JdkSeril ret = new JdkSeril();
        return ret;
    }

    private Object readResolve() throws ObjectStreamException {
        System.out.println("readResolve invoked");
        return new JdkSeril();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(new JdkSeril());
        out.flush();
        out.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bais);
        JdkSeril jdkSeril = (JdkSeril) in.readObject();
        System.out.println(jdkSeril);
    }

//    @Override
//    public void writeExternal(ObjectOutput out) throws IOException {
//        System.out.println("readResolve invoked");
//    }
//
//    @Override
//    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//        System.out.println("readResolve invoked");
//    }
}
