@echo off
chcp 65001 > nul
echo 正在编译项目...
powershell -Command "mvnd clean compile"

echo 正在打包项目...
powershell -Command "mvnd package"

echo 正在启动应用程序...
powershell -Command "java '-Dfile.encoding=UTF-8' '-Dconsole.encoding=UTF-8' -cp target/mybatis-demo-1.0-SNAPSHOT.jar com.example.demo.DemoApplication"