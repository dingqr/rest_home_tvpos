package com.yonyou.framework.library.http.concurrent;

/**
 * 作者：addison on 15/12/15 18:08
 * 邮箱：lsf@yonyou.com
 * Policy of thread-pool-executor overload.
 */
public enum OverloadPolicy {
    DiscardNewTaskInQueue,
    DiscardOldTaskInQueue,
    DiscardCurrentTask,
    CallerRuns,
    ThrowExecption
}
