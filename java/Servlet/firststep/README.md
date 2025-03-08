# JSP & Servlet & Tomcat firststep

## 開発環境起動

```sh
devcontainer.vim start .
```

## プロジェクト作成

```sh
mvn archetype:generate -DgroupId=dev.mikoto2000.study.servlet -DartifactId=firststep -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false
```

## war の作成

```sh
mvn package
```

## Tomcat へのデプロイ

```sh
sudo cp firststep/target/firststep.war /tomcat-webapps/
```
