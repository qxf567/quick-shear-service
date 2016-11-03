package com.quickshear.mapper;

import com.quickshear.domain.Hairstyle;
import com.quickshear.domain.HairstyleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HairstyleMapper {
    int countByExample(HairstyleExample example);

    int deleteByExample(HairstyleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Hairstyle record);

    int insertSelective(Hairstyle record);

    List<Hairstyle> selectByExample(HairstyleExample example);

    Hairstyle selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Hairstyle record, @Param("example") HairstyleExample example);

    int updateByExample(@Param("record") Hairstyle record, @Param("example") HairstyleExample example);

    int updateByPrimaryKeySelective(Hairstyle record);

    int updateByPrimaryKey(Hairstyle record);
}