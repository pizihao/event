package com.deep.sparrow.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池来源于使用者注入容器中的{@link ThreadPoolExecutor}的实现类。
 * 或者是用户直接指定的一个线程池。
 *
 * @author Create by liuwenhao on 2022/7/6 23:01
 */
public class ExecutorUtil {

    /**
     * 获得一组线程池中相对空闲的那个。
     * 如果参数集合为null，则返回null，具体工作线程由调用方自行提供，方便其将线程池shutdown
     *
     * @param poolExecutors 线程池集合
     * @return 相对空闲的线程池
     */
    public static ThreadPoolExecutor idleService(List<ThreadPoolExecutor> poolExecutors) {
        if (Objects.isNull(poolExecutors) || poolExecutors.isEmpty()) {
            return null;
        }

        ThreadPoolExecutor poolExecutor = null;
        for (ThreadPoolExecutor p : poolExecutors) {
            if (Objects.isNull(poolExecutor)) {
                poolExecutor = p;
                continue;
            }
            // 判断当前正在活跃线程和最大线程的比例
            BigDecimal newActive = BigDecimal.valueOf(p.getActiveCount());
            BigDecimal newMax = BigDecimal.valueOf(p.getMaximumPoolSize());
            BigDecimal newProportion = newActive.divide(newMax, RoundingMode.CEILING);
            BigDecimal oldActive = BigDecimal.valueOf(poolExecutor.getActiveCount());
            BigDecimal oldMax = BigDecimal.valueOf(poolExecutor.getMaximumPoolSize());
            BigDecimal oldProportion = oldActive.divide(oldMax, RoundingMode.CEILING);

            if (newProportion.compareTo(oldProportion) < 1) {
                poolExecutor = p;
            }

        }
        return poolExecutor;
    }

}