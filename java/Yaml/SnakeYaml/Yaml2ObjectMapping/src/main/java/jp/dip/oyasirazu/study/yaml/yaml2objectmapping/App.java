/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package jp.dip.oyasirazu.study.yaml.yaml2objectmapping;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class App {
    public static void main(String[] args) throws IOException {
        {
            var yaml = new Yaml();

            var config = (StringListConfig) yaml.loadAs(
                    new InputStreamReader(
                        ClassLoader.getSystemResourceAsStream("StringListConfig.yaml"),
                        "UTF-8"), StringListConfig.class);

            System.out.println(config);

            System.out.println(config.getSnippets().get(0).getClass());
        }

        {
            var yaml = new Yaml();

            var config = (MapListConfig)yaml.loadAs(
                    new InputStreamReader(
                        ClassLoader.getSystemResourceAsStream("MapListConfig.yaml"),
                        "UTF-8"), MapListConfig.class);

            System.out.println(config);

            System.out.println(config.getSnippets().get(0).getClass());
        }

        {
            var yaml = new Yaml();

            var config = yaml.loadAs(
                    new InputStreamReader(
                        ClassLoader.getSystemResourceAsStream("MapListMapConfig.yaml"),
                        "UTF-8"), MapListMapConfig.class);

            System.out.println(config);

            // Snippet クラスへのマッピングができていないらしく、こういうことすると例外が発生する。
            // java.lang.ClassCastException: class java.util.LinkedHashMap cannot be cast to class jp.dip.oyasirazu.study.yaml.yaml2objectmapping.MapListMapConfig$Snippet
            // System.out.println(config.getSnippets().get("java").get(0).getClass());
        }

        {
            var constructor = new Constructor() {
                @Override
                protected Object constructObject(Node node) {

                    if (node.getTag() == Tag.MAP) {
                        var map = (Map<String, String>)(super.constructObject(node));

                        // YAML の Hash が、 Snippet の条件を満たしているかを確認し、
                        // 満たしていれば Snippet へ詰め替える。
                        if (map.containsKey("label")
                                    && map.containsKey("description")
                                    && map.containsKey("newText")) {
                            return new MapListMapConfig.Snippet(
                                    map.get("label"),
                                    map.get("description"),
                                    map.get("newText"));
                        }
                    }

                    // Snippet じゃなかった場合は、デフォルトの挙動
                    return super.constructObject(node);
                }
            };
            var yaml = new Yaml(constructor);

            var config = yaml.loadAs(
                    new InputStreamReader(
                        ClassLoader.getSystemResourceAsStream("MapListMapConfig.yaml"),
                        "UTF-8"), MapListMapConfig.class);

            System.out.println(config);

            // Snippet へのマッピングもできている
            System.out.println(config.getSnippets().get("java").get(0).getClass());
        }
    }
}