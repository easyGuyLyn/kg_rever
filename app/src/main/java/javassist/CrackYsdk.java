package javassist;


import java.io.File;
import java.io.FileOutputStream;

public class CrackYsdk {

    static final String Jar_dir = "/Users/ushee/AndroidStudioProjects/sports/kg_rever/app/src/main/assets/";

    static final String Jar_name = "ysdk.jar";

    static final String Package_name = "com.tencent.ysdk.module.hades";

    static final String Class_name = "c";


    public static void main(String args[]) {

        try {

            //1 修改

            ClassPool classPool = ClassPool.getDefault();
            classPool.appendClassPath(Jar_dir + Jar_name);

            CtClass ctClass = classPool.get(Package_name + "." + Class_name);
            CtMethod ctMethod = ctClass.getDeclaredMethod("run", null);
            ctMethod.setBody("{ }");



            //2   生成
            byte[] bytes = ctClass.toBytecode();

            String classPath = Package_name.replace(".", "/") + "/";
            File dirFile = new File(Jar_dir + classPath + Class_name + ".class");

            if (!dirFile.getParentFile().exists()) {
                dirFile.getParentFile().mkdirs();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(dirFile);
            fileOutputStream.write(bytes);
            System.out.print("  Char dirFile:  " + dirFile.getAbsolutePath());


            //3  替换

            String classPath1 = Package_name.replace(".", "/") + "/" + Class_name + ".class";

                //进入到目录
            Runtime.getRuntime().exec("cd " + Jar_dir);


            Process process = Runtime.getRuntime().exec("jar uvf " + Jar_name + " " + classPath1);

            System.out.print("  Char 执行命令行的结果  " + process.waitFor());


        } catch (Exception e) {
            e.printStackTrace();

            System.out.print("Char 报错了  " + e.getLocalizedMessage());

        }

    }


}
