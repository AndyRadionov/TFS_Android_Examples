package com.radionov.asynchandlerlooper;

/**
 * @author Vladimir Kokhanov
 */
class ThreadUtils {

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
