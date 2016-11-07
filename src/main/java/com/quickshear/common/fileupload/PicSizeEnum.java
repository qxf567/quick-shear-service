/**
 * ToDoEnum.java
 * Copyright (c) 2013 by lashou.com
 */
package com.quickshear.common.fileupload;


/**
 * 图片尺寸枚举类  -- 可按实现情况进行修改
 * @since v 
 */
public enum PicSizeEnum {
	
    /** 主图 */
    MAIN_PIC(1, "主图尺寸", 440,280 ),
    /** 小图 */
    MIN_PIC(2, "小图尺寸",640, Integer.MAX_VALUE),
    MIN_PIC2(3, "小图尺寸",913, Integer.MAX_VALUE);
    
    /**
     * ID
     */
    private final int id;

    /**
     * 名称
     */
    private final String cname;

    /**
     * 宽
     */
    private final int width;

    /**
     * 高
     */
    private final int height;

	/**
	 * @param id
	 * @param cname
	 * @param width
	 * @param height
	 */
	private PicSizeEnum(int id, String cname, int width, int height) {
		this.id = id;
		this.cname = cname;
		this.width = width;
		this.height = height;
	}

	public int getId() {
		return id;
	}

	public String getCname() {
		return cname;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

    public static PicSizeEnum valueOf(int id) throws IllegalArgumentException{
        for (PicSizeEnum bt : values()) {
            if (bt.id == id) {
                return bt;
            }
        }
        return null;
    }
   

}
