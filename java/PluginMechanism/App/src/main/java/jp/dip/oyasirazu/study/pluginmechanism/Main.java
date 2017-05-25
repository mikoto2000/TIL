package jp.dip.oyasirazu.study.pluginmechanism;

import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Main
 */
public class Main {
    public static void main(String args[]) {
        System.out.println("start!");

        List<HelloPlugin> list = new ArrayList<HelloPlugin>();
        ServiceLoader<HelloPlugin> loader = ServiceLoader.load(
                HelloPlugin.class,
                Thread.currentThread().getContextClassLoader());

        for (HelloPlugin hello: loader) {
            System.out.println(hello.hello());
        }

        System.out.println("end!");
    }
}

