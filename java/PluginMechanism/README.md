PluginMechanism
===============

```sh
$ cd App
$ ./gradlew build
$ cd ..

$ java -cp App/build/libs/PluginMechanism.jar jp.dip.oyasirazu.study.pluginmechanism.Main
start!
ハロー
end!

$ cd Plgins/english
$ ./gradlew build
$ cd ../..

$ java -cp 'App/build/libs/PluginMechanism.jar;Plugins/english/build/libs/english.jar' jp.dip.oyasirazu.study.pluginmechanism.Main
start!
ハロー
Hello.
end!
```

