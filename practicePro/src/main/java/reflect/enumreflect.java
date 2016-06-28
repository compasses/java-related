package reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

import util.*;

/**
 * Created by i311352 on 6/27/16.
 */
enum Explore { HERE, THERE }

public class enumreflect {
    public static Set<String> analyze(Class<?> enumClass) {
        util.print("----- Analyzing " + enumClass + " -----");
        util.print("Interfaces:");
        for(Type t : enumClass.getGenericInterfaces())
            util.print(t);
        util.print("Base: " + enumClass.getSuperclass());
        util.print("Methods: ");
        Set<String> methods = new TreeSet<String>();
        for(Method m : enumClass.getMethods())
            methods.add(m.getName());
        util.print(methods);
        return methods;
    }
    public static void main(String[] args) {
        Set<String> exploreMethods = analyze(Explore.class);
        Set<String> enumMethods = analyze(Enum.class);
        util.print("Explore.containsAll(Enum)? " +
                exploreMethods.containsAll(enumMethods));
        util.print("Explore.removeAll(Enum): ");
        exploreMethods.removeAll(enumMethods);
        util.print(exploreMethods);
        // Decompile the code for the enum:
        OSExecute.command("javap Explore");
    }
}