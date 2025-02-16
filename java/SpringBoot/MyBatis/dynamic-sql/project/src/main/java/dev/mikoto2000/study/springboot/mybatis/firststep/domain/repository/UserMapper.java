package dev.mikoto2000.study.springboot.mybatis.firststep.domain.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity.User;
import dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity.UserSearchRequest;

@Mapper
public interface UserMapper {
    @Select("SELECT id, name FROM user1")
    List<User> searchAll();

    @MapKey("id")
    @Select("SELECT id, name FROM user1")
    Map<Long, User> searchAllMap();

    @Select("SELECT id, name FROM user1 WHERE id = #{id}")
    User search(UserSearchRequest user);

    @Insert("INSERT INTO user1 VALUES (#{id}, #{name})")
    int insert(long id, String name);

    @Insert("""
            <script>
            INSERT INTO user1
            (
             id,
             name
            )
            VALUES
            <foreach item="item" collection="collection" separator=", ">
              (#{item.id}, #{item.name})
            </foreach>
            </script>
            """)
    int insertBulk(List<User> users);

    @Delete("DELETE FROM user1 WHERE id = #{id}")
    void delete(long id);
}

