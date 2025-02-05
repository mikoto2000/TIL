package dev.mikoto2000.study.mybatis.multidatasource.primary;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

/**
 * PrimaryUserMapper
 */
public interface PrimaryUserMapper {
   @Select("SELECT * FROM user1")
    List<Map<String, String>> findAll();
}
