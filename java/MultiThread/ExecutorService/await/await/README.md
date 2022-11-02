# await

## Create Project

```sh
mvn archetype:generate \
    -DarchetypeGroupId=org.apache.maven.archetypes \
    -DarchetypeArtifactId=maven-archetype-simple \
    -DgroupId=dev.mikoto2000.study.java.multithread.await \
    -Dpackage=dev.mikoto2000.study.java.multithread.await \
    -DartifactId=await \
    -DinteractiveMode=false
```

## Compile

```sh
mvn compile
```

## Run

```sh
mvn exec:java -Dexec.mainClass=dev.mikoto2000.study.java.multithread.await.App
```

