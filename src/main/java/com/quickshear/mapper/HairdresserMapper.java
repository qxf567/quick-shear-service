package com.quickshear.mapper;

import com.quickshear.domain.Hairdresser;
import com.quickshear.domain.HairdresserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HairdresserMapper {
    int countByExample(HairdresserExample example);

    int deleteByExample(HairdresserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Hairdresser record);

    int insertSelective(Hairdresser record);

    List<Hairdresser> selectByExample(HairdresserExample example);

    Hairdresser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Hairdresser record, @Param("example") HairdresserExample example);

    int updateByExample(@Param("record") Hairdresser record, @Param("example") HairdresserExample example);

    int updateByPrimaryKeySelective(Hairdresser record);

    int updateByPrimaryKey(Hairdresser record);
}