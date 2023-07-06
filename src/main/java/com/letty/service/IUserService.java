package com.letty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.letty.dto.LoginFormDTO;
import com.letty.dto.Result;
import com.letty.entity.User;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Letty
 * @since 2022-12-22
 */
public interface IUserService extends IService<User> {

    public Result sendCode(String phone, HttpSession session);

    public Result login(LoginFormDTO loginFormDTO, HttpSession session);
}
