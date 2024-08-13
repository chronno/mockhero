package com.chronno.mockhero.configuration.threading;

import org.apache.tomcat.util.security.PrivilegedSetAccessControlContext;
import org.apache.tomcat.util.security.PrivilegedSetTccl;
import org.apache.tomcat.util.threads.Constants;
import org.apache.tomcat.util.threads.TaskThread;
import org.apache.tomcat.util.threads.TaskThreadFactory;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.atomic.AtomicInteger;

public class MockingThreadFactory extends TaskThreadFactory {

    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;
    private final boolean daemon;
    private final int threadPriority;

    public MockingThreadFactory(String namePrefix, boolean daemon, int priority) {
        super(namePrefix, daemon, priority);
        SecurityManager s = System.getSecurityManager();
        this.group = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix;
        this.daemon = daemon;
        this.threadPriority = priority;
    }

    @Override
    public Thread newThread(Runnable r) {
        String var10004 = this.namePrefix;
        TaskThread t = new MockingThread(this.group, r, namePrefix + this.threadNumber.getAndIncrement());
        t.setDaemon(this.daemon);
        t.setPriority(this.threadPriority);
        if (Constants.IS_SECURITY_ENABLED) {
            PrivilegedAction<Void> paTccl = new PrivilegedSetTccl(t, this.getClass().getClassLoader());
            AccessController.doPrivileged(paTccl);
            PrivilegedAction<Void> pa = new PrivilegedSetAccessControlContext(t);
            AccessController.doPrivileged(pa);
        } else {
            t.setContextClassLoader(this.getClass().getClassLoader());
        }
        return t;
    }
}
