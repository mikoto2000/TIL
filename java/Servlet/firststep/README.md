# JSP & Servlet & Tomcat firststep

## 開発環境起動

```sh
devcontainer.vim start .
```

## プロジェクト作成

```sh
mvn archetype:generate -DgroupId=dev.mikoto2000.study.servlet -DartifactId=firststep -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false
```

## `pom.xml` にサーブレット・JSP の依存を追加

```xml
    <!-- https://mvnrepository.com/artifact/jakarta.servlet/jakarta.servlet-api -->
    <dependency>
      <groupId>jakarta.servlet</groupId>
      <artifactId>jakarta.servlet-api</artifactId>
      <version>6.1.0</version>
      <scope>provided</scope>
    </dependency>

    <!-- https://mvnrepository.com/artifact/jakarta.servlet.jsp.jstl/jakarta.servlet.jsp.jstl-api -->
    <dependency>
      <groupId>jakarta.servlet.jsp.jstl</groupId>
      <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
      <version>3.0.2</version>
    </dependency>
```

## `web.xml` のバージョン変更

before:

```xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>
</web-app>
```

after:

```xml
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
         http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
         version="3.0">
    <display-name>MyApp</display-name>
</web-app>
```


## war の作成

```sh
mvn package
```

## Tomcat へのデプロイ

```sh
sudo cp firststep/target/firststep.war /tomcat-webapps/
```
