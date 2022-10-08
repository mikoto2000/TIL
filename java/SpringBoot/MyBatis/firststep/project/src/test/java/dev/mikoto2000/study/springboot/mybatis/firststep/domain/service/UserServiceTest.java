package dev.mikoto2000.study.springboot.mybatis.firststep.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity.User;
import dev.mikoto2000.study.springboot.mybatis.firststep.domain.entity.UserSearchRequest;
import dev.mikoto2000.study.springboot.mybatis.firststep.domain.repository.UserMapper;

public class UserServiceTest {

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUserServiceSearchAll() {
        // searchAll が実行されたら RuntimeException が発生する
        // when(userMapper.searchAll()).thenThrow(new RuntimeException("Mockito で差し込んだ例外！！！"));

        // サイズゼロのリストを返却
        doReturn(new ArrayList<User>()).when(userMapper).searchAll();

        List<User> users = userService.searchAll();

        // メソッド実行回数チェック
        verify(userMapper, times(1)).searchAll();
    }

    @Test
    public void testUserServiceSearch() {
        // searchAll が実行されたら RuntimeException が発生する
        // when(userMapper.searchAll()).thenThrow(new RuntimeException("Mockito で差し込んだ例外！！！"));

        // サイズゼロのリストを返却
        doReturn(new User(10L, "mikoto")).when(userMapper).search(any(UserSearchRequest.class));

        User users = userService.search(10L);

        // メソッド実行回数チェックと引数キャプチャ
        var argumentCapture = ArgumentCaptor.forClass(UserSearchRequest.class);
        verify(userMapper, times(1)).search(argumentCapture.capture());

        // 引数チェック
        var capturedItem = argumentCapture.getValue();
        assertEquals(10, capturedItem.getId());
    }
}

