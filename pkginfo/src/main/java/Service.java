import java.lang.annotation.Annotation;

/**
 * User: xiaofei.wxf
 * Date: 13-11-26 上午9:46
 */
public class Service {
    public static void main(String[] args) {
        final String pkg = "xiaofei.pkginfo";
        Package p = Package.getPackage(pkg);
        for (Annotation each : p.getAnnotations()) {
            System.out.println(each);
        }
        Annotation anno = p.getAnnotation(PkgAnno.class);
        System.out.println(anno);
    }
}
