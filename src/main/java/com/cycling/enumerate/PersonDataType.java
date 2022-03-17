package com.cycling.enumerate;


/**
 * @Author xpdxz
 * @ClassName PersonDataType
 * @Description TODO 使用enum来区分四种类型的请求，减少接口冗余
 * @Date 2021/11/23 17:40
 */
public enum PersonDataType {
    /**
     * 动态
     */
    DYNAMIC(0),
    /**
     * 活动
     */
    ACTIVE(1);

    private final int type;

    PersonDataType(int type) {
        this.type = type;
    }

    public int getValue() {
        return this.type;
    }
}
