package com.example.dtt.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.HashMap;

/**
 * 任务运行状态
 */
public enum TaskStatus {
    SUBMITTED_SUCCESS(0, "submit success"),
    RUNNING_EXECUTION(1, "running"),
    READY_PAUSE(2, "ready pause"),
    PAUSE(3, "pause"),
    READY_STOP(4, "ready stop"),
    STOP(5, "stop"),
    FAILURE(6, "failure"),
    SUCCESS(7, "success"),
    NEED_FAULT_TOLERANCE(8, "need fault tolerance"),
    KILL(9, "kill"),
    WAITING_THREAD(10, "waiting thread"),
    WAITING_DEPEND(11, "waiting depend node complete"),
    DELAY_EXECUTION(12, "delay execution"),
    FORCED_SUCCESS(13, "forced success"),
    SERIAL_WAIT(14, "serial wait"),
    READY_BLOCK(15, "ready block"),
    BLOCK(16, "block"),
    DISPATCH(17, "dispatch"),
    ;

    TaskStatus(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    @EnumValue
    private final int code;
    private final String descp;

    private static HashMap<Integer, TaskStatus> EXECUTION_STATUS_MAP = new HashMap<>();

    static {
        for (TaskStatus executionStatus : TaskStatus.values()) {
            EXECUTION_STATUS_MAP.put(executionStatus.code, executionStatus);
        }
    }

    /**
     * status is success
     *
     * @return status
     */
    public boolean typeIsSuccess() {
        return this == SUCCESS || this == FORCED_SUCCESS;
    }

    /**
     * status is failure
     *
     * @return status
     */
    public boolean typeIsFailure() {
        return this == FAILURE || this == NEED_FAULT_TOLERANCE;
    }

    /**
     * status is finished
     *
     * @return status
     */
    public boolean typeIsFinished() {
        return typeIsSuccess() || typeIsFailure() || typeIsCancel() || typeIsPause()
                || typeIsStop() || typeIsBlock();
    }

    /**
     * status is waiting thread
     *
     * @return status
     */
    public boolean typeIsWaitingThread() {
        return this == WAITING_THREAD;
    }

    /**
     * status is pause
     *
     * @return status
     */
    public boolean typeIsPause() {
        return this == PAUSE;
    }

    /**
     * status is pause
     *
     * @return status
     */
    public boolean typeIsStop() {
        return this == STOP;
    }

    /**
     * status is running
     *
     * @return status
     */
    public boolean typeIsRunning() {
        return this == RUNNING_EXECUTION || this == WAITING_DEPEND || this == DELAY_EXECUTION;
    }

    /**
     * status is block
     *
     * @return status
     */
    public boolean typeIsBlock() {
        return this == BLOCK;
    }

    /**
     * status is cancel
     *
     * @return status
     */
    public boolean typeIsCancel() {
        return this == KILL || this == STOP;
    }

    public int getCode() {
        return code;
    }

    public String getDescp() {
        return descp;
    }

    public static TaskStatus of(int status) {
        if (EXECUTION_STATUS_MAP.containsKey(status)) {
            return EXECUTION_STATUS_MAP.get(status);
        }
        throw new IllegalArgumentException("invalid status : " + status);
    }
}
