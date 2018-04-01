package ru.krista.etl.cloveretl.agent;

import javassist.*;
import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class CloverAgent {
    public static void premain(String args, Instrumentation instrumentation){
        System.out.println("Hello I'm Agent");
        instrumentation.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) ->{

            if (className.contains("WatchDog") || className.contains("Tracking")){
                System.out.println(className);
            }



            if (className.equals("org/jetel/graph/runtime/WatchDog")){
                System.out.println("instrumenting");

                try{
                    System.out.println("HHHHHEEEEYY0");
                    ClassPool classPool = ClassPool.getDefault();
                    CtClass ctClass = classPool.get("org.jetel.graph.runtime.WatchDog");
                    //WatchDog#call

                    CtMethod callMethod = ctClass.getDeclaredMethod("call");

                    //ru.krista.etl.cloveretl.notification.Tracking.track(cloverJMX, graph);
                    //254 line
                    callMethod.insertAt(245,"ru.krista.etl.cloveretl.notification.Tracking.track(cloverJMX, graph);");

                    System.out.println(callMethod.toString());
                    System.out.println(ctClass.toString());
                     return ctClass.toBytecode();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CannotCompileException e) {
                    e.printStackTrace();
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println("FuCK");
            }
            return new byte[0];
        });
    }

}
