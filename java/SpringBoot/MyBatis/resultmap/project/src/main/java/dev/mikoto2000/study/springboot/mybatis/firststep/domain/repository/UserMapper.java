package dev.mikoto2000.study.springboot.mybatis.firststep.domain.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity.UserSearchResult;


@Mapper
public interface UserMapper {
  @Select("""
          select
            u.*,
            i.id as d_id,
            i.user_id as d_user_id,
            i.description as d_description
          from
            "user" as u
          left outer join
            user_info as i
          on
            u.id = i.user_id;
          """)
  @ResultMap("UserSearchResultMap")
  List<UserSearchResult> findAllUserDetails();
}

