package jp.dip.oyasirazu.study.jruby;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class StudyJRuby02 {

    public static void main(String[] args) throws Exception {
        ScriptEngine jruby = new ScriptEngineManager().getEngineByName("jruby");
        Object obj = jruby.eval(getFileContents("./src/main/resource/helloworld.rb"));
        System.out.println(obj);
    }

    private static String getFileContents(String filename) throws IOException {
        return Files.lines(Paths.get(filename)).collect(Collectors.joining("\n"));
    }
}
