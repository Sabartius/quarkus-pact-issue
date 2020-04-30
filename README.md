# description

We're using Pact-JVM (https://github.com/DiUS/pact-jvm) to specify and test consumer driven contracts.  
We implement those tests by running Junit Jupiter tests with a combination of `@QuarkusTest` 
and Pact-JVM Jupiter extensions.

Unfortunately since Quarkus `1.3.0.Final` those tests will fail because of `ClassCastException`.
After a short research I've seen that those exceptions will be thrown, because the test encounters multiple ClassLoaders. 
 
The issue still appears when we use Quarkus `1.4.1.Final`.   
When we use Quarkus `1.2.1.Final` everything works fine.

# steps to reproduce

Clone this repo

     $ git clone git@github.com:tgippi/quarkus-pact-issue.git
      
Run the tests either with `1.3.2.Final` or with `1.4.1.Final`:

    $ mvn -P1.3 clean test
    
or
    
    $ mvn -P1.4 clean test

See that there will a ClassCastException be thrown.

<pre>
 java.lang.ClassCastException: class au.com.dius.pact.core.model.messaging.Message cannot be cast to class au.com.dius.pact.core.model.messaging.Message (au.com.dius.pact.core.model.messaging.Message is in unnamed module of loader 'app'; au.com.dius.pact.core.model.messaging.Message is in unnamed module of loader io.quarkus.bootstrap.classloading.QuarkusClassLoader @1f97cf0d)
 
 	at de.tgippi.pact.ConsumerPactTest.test(ConsumerPactTest.java:42)
 	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
 	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
 	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
 	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
 	at io.quarkus.test.junit.QuarkusTestExtension.runExtensionMethod(QuarkusTestExtension.java:509)
 	at io.quarkus.test.junit.QuarkusTestExtension.interceptTestMethod(QuarkusTestExtension.java:410)
 	at org.junit.jupiter.engine.execution.ExecutableInvoker$ReflectiveInterceptorCall.lambda$ofVoidMethod$0(ExecutableInvoker.java:115)
 	at org.junit.jupiter.engine.execution.ExecutableInvoker.lambda$invoke$0(ExecutableInvoker.java:105)
 	at org.junit.jupiter.engine.execution.InvocationInterceptorChain$InterceptedInvocation.proceed(InvocationInterceptorChain.java:106)
 	at org.junit.jupiter.engine.extension.TimeoutExtension.intercept(TimeoutExtension.java:149)
 	at org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestableMethod(TimeoutExtension.java:140)
 	at org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestMethod(TimeoutExtension.java:84)
 	at org.junit.jupiter.engine.execution.ExecutableInvoker$ReflectiveInterceptorCall.lambda$ofVoidMethod$0(ExecutableInvoker.java:115)
 	at org.junit.jupiter.engine.execution.ExecutableInvoker.lambda$invoke$0(ExecutableInvoker.java:105)
 	at org.junit.jupiter.engine.execution.InvocationInterceptorChain$InterceptedInvocation.proceed(InvocationInterceptorChain.java:106)
 	at org.junit.jupiter.engine.execution.InvocationInterceptorChain.proceed(InvocationInterceptorChain.java:64)
 	at org.junit.jupiter.engine.execution.InvocationInterceptorChain.chainAndInvoke(InvocationInterceptorChain.java:45)
 	at org.junit.jupiter.engine.execution.InvocationInterceptorChain.invoke(InvocationInterceptorChain.java:37)
 	at org.junit.jupiter.engine.execution.ExecutableInvoker.invoke(ExecutableInvoker.java:104)
 	at org.junit.jupiter.engine.execution.ExecutableInvoker.invoke(ExecutableInvoker.java:98)
 	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.lambda$invokeTestMethod$6(TestMethodTestDescriptor.java:212)
 	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
 	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.invokeTestMethod(TestMethodTestDescriptor.java:208)
 	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:137)
 	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:71)
 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:135)
 	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:125)
 	at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:135)
 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:123)
 	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:122)
 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:80)
 	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
 	at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:38)
 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:139)
 	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:125)
 	at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:135)
 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:123)
 	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:122)
 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:80)
 	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
 	at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:38)
 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:139)
 	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:125)
 	at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:135)
 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:123)
 	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:122)
 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:80)
 	at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.submit(SameThreadHierarchicalTestExecutorService.java:32)
 	at org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor.execute(HierarchicalTestExecutor.java:57)
 	at org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine.execute(HierarchicalTestEngine.java:51)
 	at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:248)
 	at org.junit.platform.launcher.core.DefaultLauncher.lambda$execute$5(DefaultLauncher.java:211)
 	at org.junit.platform.launcher.core.DefaultLauncher.withInterceptedStreams(DefaultLauncher.java:226)
 	at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:199)
 	at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:132)
 	at com.intellij.junit5.JUnit5IdeaTestRunner.startRunnerWithArgs(JUnit5IdeaTestRunner.java:69)
 	at com.intellij.rt.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:33)
 	at com.intellij.rt.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:230)
 	at com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:58)
</pre>

Run the tests again with 1.2.1.Final

    $ mvn -P1.2 clean test

See that everything works fine.
