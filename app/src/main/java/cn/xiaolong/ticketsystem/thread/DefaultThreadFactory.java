package cn.xiaolong.ticketsystem.thread;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/5 16:51
 */

import java.util.concurrent.ThreadFactory;

public class DefaultThreadFactory implements ThreadFactory {
    int threadNum = 0;

    @Override
    public Thread newThread(Runnable runnable) {
        final Thread result = new Thread(runnable, "fifo-pool-thread-" + threadNum) {
            @Override
            public void run() {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                super.run();
            }
        };
        threadNum++;
        return result;
    }
}