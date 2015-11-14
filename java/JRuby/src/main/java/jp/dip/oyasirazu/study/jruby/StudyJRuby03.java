package jp.dip.oyasirazu.study.jruby;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.ast.Node;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * StudyJRuby03
 */
public class StudyJRuby03 {

    /**
     * Constructor
     */
    public StudyJRuby03() {

    }

    public static void main(String[] args) throws IOException {

        final String scriptPath = args[0];
        final String gemsPath = args[1];

        final Path basePath = Paths.get(gemsPath);
        final List<String> loadPaths = new ArrayList<>();
        Files.walkFileTree(basePath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path file, BasicFileAttributes attrs) throws IOException {
                String fileStr = file.toString();
                if (fileStr.endsWith("lib")) {
                    loadPaths.add(fileStr);
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }
        });

        RubyInstanceConfig config = new RubyInstanceConfig();
        config.setLoadPaths(loadPaths);

        Ruby ruby = Ruby.newInstance(config);

        Path p = Paths.get(scriptPath);
        Node node = ruby.parseFile(
                Files.newInputStream(p),
                p.toString(),
                ruby.getCurrentContext().getCurrentScope(),
                0);

        IRubyObject obj = ruby.runNormally(node);
    }
}

