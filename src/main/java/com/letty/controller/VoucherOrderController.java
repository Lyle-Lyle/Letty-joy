package com.letty.controller;


import com.letty.dto.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Letty
 * @since 2022-12-22
 */
@RestController
@RequestMapping("/voucher-order")
public class VoucherOrderController {
    @PostMapping("seckill/{id}")
    public Result seckillVoucher(@PathVariable("id") Long voucherId) {
        return Result.fail("功能未完成");
    }
}
