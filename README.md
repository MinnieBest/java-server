# HTTP Server

```
java Main
```

## Options

Port
```
-p <port>
```

Directory
```
-d <directory>
```

###Commands

javac -d bin/main src/main/* && javac -d bin/test src/test/* && java org.junit.runner.JUnitCore AllTests

compile main

javac -d bin/main src/main/*

compile test

javac -d bin/test src/test/*

create jar

jar cmvf META-INF:MANIFEST.MF Directory.jar -C bin/main .

run tests

java org.junit.runner.JUnitCore AllTests

classpath

export CLASSPATH="bin/main:bin/test:lib/*"

emma

java -XX:-UseSplitVerifier emmarun -cp bin/main:bin/test:lib/junit-4.11.jar -ix 'kevin.*' org.junit.runner.JUnitCore AllTests
