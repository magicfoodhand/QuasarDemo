# QuasarDemo

## Create Project Using quasar-mvn-archetype
- ```git clone https://github.com/puniverse/quasar-mvn-archetype```
- ```cd quasar-mvn-archetype```
- ```mvn install```
- ```cd ..```
- ```mvn archetype:generate -DarchetypeGroupId=co.paralleluniverse -DarchetypeArtifactId=quasar-mvn-archetype -DarchetypeVersion=0.7.4 -DgroupId=tech.lavabear -DartifactId=quasardemo```

## Add quasar-maven-plugin for AOT Quasar Instrumentation
- Originally from: https://github.com/vy/quasar-maven-plugin
```
<plugin>
    <groupId>com.vlkan</groupId>
    <artifactId>quasar-maven-plugin</artifactId>
    <version>0.7.5</version>
    <configuration>
        <check>true</check>
        <debug>true</debug>
        <verbose>true</verbose>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>instrument</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```


