package com.letty.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.letty.dto.LoginFormDTO;
import com.letty.dto.Result;
import com.letty.dto.UserDTO;
import com.letty.entity.User;
import com.letty.mapper.UserMapper;
import com.letty.service.IUserService;
import com.letty.utils.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.letty.utils.SystemConstants.USER_NICK_NAME_PREFIX;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Letty
 * @since 2022-12-22
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Result sendCode(String phone, HttpSession session) {
        // 1. validate phone number
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 2.no, return
            return Result.fail("手机号格式错误！");
        }
        // generate code
        String code = RandomUtil.randomNumbers(4);
        session.setAttribute("code", code);

        // send code to user
        log.debug("sent code to user: {}", code);
        return Result.ok();
    }

    @Override
    public Result login(LoginFormDTO loginFormDTO, HttpSession session) {
        // 1. validate phone number
        String phone = loginFormDTO.getPhone();
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 2. no, return
            return Result.fail("phone no is invalid！");
        }
        // 2. validate code
        Object codeSession = session.getAttribute("code");
        String code = loginFormDTO.getCode();
        if(codeSession == null || !codeSession.toString().equals(code)){
            return Result.fail("code is invalid");
        }
        // query user by phone number
        User user = query().eq("phone", phone).one();
        if (user == null) {
            user = creatUserWithPhone(phone);
        }

        // store user in session
        // copy properties of user to UserDTO
        session.setAttribute("user", BeanUtil.copyProperties(user, UserDTO.class));
        return Result.ok();
    }


    private User creatUserWithPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setNickName(USER_NICK_NAME_PREFIX + RandomUtil.randomString(8));
        save(user);
        return user;
    }




}
