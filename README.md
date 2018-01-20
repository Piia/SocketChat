# SocketChat

Simple chat for my self-studying purposes. Java application with TCP-sockets.


## Build using Gradle

```
gradle bigJar
```
Creates self contained runnable jar "SocketChat-all.jar" under build/libs.
Run using 
```
java -jar /build/libs/SocketChat-all.jar
```


## TODO

- Testing
- Thread management with an executor/threadpool/etc.
- Proper handling of exceptions
- GUI
- Separate connecting and asking IP-address/port
- Ask preferred server port number
- Recovering from faulty IP-address/port
- Better info messages
