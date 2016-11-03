package com.quickshear.mapper;

import com.quickshear.domain.OrderEvaluate;
import com.quickshear.domain.OrderEvaluateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderEvaluateMapper {
    int countByExample(OrderEvaluateExample example);

    int deleteByExample(OrderEvaluateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderEvaluate record);

    int insertSelective(OrderEvaluate record);

    List<OrderEvaluate> selectByExample(OrderEvaluateExample example);

    OrderEvaluate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderEvaluate record, @Param("example") OrderEvaluateExample example);

    int updateByExample(@Param("record") OrderEvaluate record, @Param("example") OrderEvaluateExample example);

    int updateByPrimaryKeySelective(OrderEvaluate record);

    int updateByPrimaryKey(OrderEvaluate record);
}