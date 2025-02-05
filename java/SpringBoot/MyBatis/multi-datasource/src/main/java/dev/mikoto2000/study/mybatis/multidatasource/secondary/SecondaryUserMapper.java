package dev.mikoto2000.study.mybatis.multidatasource.secondary;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

/**
 * SecondaryUserMapper
 */
public interface SecondaryUserMapper {
   @Select("SELECT * FROM user1")
    List<Map<String, String>> findAll();
}

