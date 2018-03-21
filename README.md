# milltest
reproducer for https://github.com/lihaoyi/mill/issues/211

This shows that duplicate dependencies within a module are cleaned up but not when there are transitive duplicates or inter-module duplicates

```
➜  milltest git:(master) ✗ mill sameWithin.validateDeps
Compiling /Users/francisdb/workspace/milltest/build.sc
[11/11] sameWithin.validateDeps
OK

➜  milltest git:(master) ✗ mill sameTransitive.validateDeps
[11/11] sameTransitive.validateDeps
1 targets failed
sameTransitive.validateDeps java.lang.RuntimeException: Multiple akka dependencies found: Agg(akka-stream_2.12-2.5.9.jar, akka-actor_2.12-2.5.10.jar)
    scala.sys.package$.error(package.scala:27)
    ammonite.$file.build$BaseModule.$anonfun$validateDeps$2(build.sc:22)
    ammonite.$file.build$BaseModule.$anonfun$validateDeps$2$adapted(build.sc:17)
    mill.define.Task$Mapped.$anonfun$evaluate$5(Task.scala:354)
    
➜  milltest git:(master) ✗ mill moduleTransitive.validateDeps
[39/39] moduleTransitive.validateDeps
1 targets failed
moduleTransitive.validateDeps java.lang.RuntimeException: Multiple akka dependencies found: Agg(akka-actor_2.12-2.5.10.jar, akka-actor_2.12-2.5.11.jar)
    scala.sys.package$.error(package.scala:27)
    ammonite.$file.build$BaseModule.$anonfun$validateDeps$2(build.sc:22)
    ammonite.$file.build$BaseModule.$anonfun$validateDeps$2$adapted(build.sc:17)
    mill.define.Task$Mapped.$anonfun$evaluate$5(Task.scala:354)

```
