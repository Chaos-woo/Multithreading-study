1.Java应用程序的main函数是一个线程，是被JVM启动的时候调用，线程的名字叫main

2.实现一个线程，必须创建Thread实例，覆盖run()方法，并且调用start()方法，调用start()方法时，会调用一个native方法start0()，而start0()方法会调用覆盖的run()方法

3.当JVM启动后，实际上有多个线程，但是至少有一个非守护线程

4.当你调用一个线程start()方法的时候，此时至少有两个线程，一个是你调用的线程，还有一个是执行run()方法的线程

5.线程的生命周期分为new，runnable，running，block，terminate