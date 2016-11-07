package com.quickshear.common.enumeration;


/**
 * 角色定义 三个API共用一个体系的角色 
 * 
 */
public enum RoleEnum {
	
    CUSTOMER(1, "web用户"),
    ENGINEER(2, "工程师"),
    CUSTOMER_SERVICE(3,"客服"),
    FINANCE(4, "财务"),
    SUPER_ADMIN(999,"超级管理员");
    

    private Integer id;

    private String desc;


    private RoleEnum(Integer id, String desc){
        this.id = id;
        this.desc = desc;
    }
    public static RoleEnum getRoleEnum(Integer id) {
        if(id == null){
            return null;
        }
        RoleEnum[] enumArray = RoleEnum.values();
        for(RoleEnum c :  enumArray){
            if(c.id.equals(id)){
                return c;
            }
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }
}
