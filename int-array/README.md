# int-array

## install antlr
- install it via [antlr-tools](https://github.com/antlr/antlr4-tools)
- or use 'brew install antlr'
- put antlr-<version>-complete.jar into your Java Classpath (export CLASSPATH=".:<path-to>/antlr-<version>-complete.jar")

## compile grammar
```shell
antlr <grammar-file-name>
javac <grammar-name>
```
or use Maven-Plugin
```xml
<plugin>
    <groupId>org.antlr</groupId>
    <artifactId>antlr4-maven-plugin</artifactId>
    <version>${antlr4.version}</version>
    <executions>
        <execution>
            <id>antlr</id>
            <goals>
                <goal>antlr4</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <visitor>true</visitor>
        <listener>true</listener>
        <options>
            <language>Java</language>
        </options>
    </configuration>
</plugin>
```

## run TestRig
grun <grammar-name> <start parser-rule> -tokens -tree <path-to-input-file>