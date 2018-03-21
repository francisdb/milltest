# milltest
reproducer for https://github.com/lihaoyi/mill/issues/211

This shows that duplicate dependencies within a module are cleaned up (even transitive) but not when there are inter-module duplicates

```
➜  milltest git:(master) ✗ mill sameWithin.validateDeps
[11/11] sameWithin.validateDeps
OK

➜  milltest git:(master) ✗ mill sameTransitive.validateDeps
[11/11] sameTransitive.validateDeps
OK

➜  milltest git:(master) ✗ mill moduleTransitive.validateDeps
[39/39] moduleTransitive.validateDeps
1 targets failed
moduleTransitive.validateDeps java.lang.RuntimeException: Expected akka-actor_2.12-2.5.10.jar but found: Agg(akka-actor_2.12-2.5.10.jar, akka-actor_2.12-2.5.11.jar)
    scala.sys.package$.error(package.scala:27)
    ammonite.$file.build$BaseModule.$anonfun$validateDeps$2(build.sc:25)
    ammonite.$file.build$BaseModule.$anonfun$validateDeps$2$adapted(build.sc:20)
    mill.define.Task$Mapped.$anonfun$evaluate$5(Task.scala:354)

```
