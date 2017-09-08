package com.imooc.controller;

import com.imooc.converter.OrderForm2OrderDTOConver;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exceptions.SellException;
import com.imooc.form.OrderForm;
import com.imooc.service.BuyerService;
import com.imooc.service.impl.BuyerServiceImpl;
import com.imooc.service.impl.OrderServiceImpl;
import com.imooc.util.ResultVOUtil;
import com.imooc.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CZ on 2017/9/4.
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private BuyerServiceImpl buyerService;

    // 创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[创建订单] 表单错误 orderForm: {}", orderForm);
            throw new SellException(ResultEnum.FORM_PARAMS_ERR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConver.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[创建订单] 订单购物车列表为空 OrderDTO: {}", orderDTO);
            throw new SellException(ResultEnum.EMPTY_CART);
        }
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());
        return ResultVOUtil.success(map);

    }


    // 订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("[查询订单列表] OpenId不能为空");
            throw new SellException(ResultEnum.FORM_PARAMS_ERR);
        }
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, new PageRequest(page, size));
        return ResultVOUtil.success(orderDTOPage.getContent());

    }


    // 订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderid") String orderid) {

        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderid);
        return ResultVOUtil.success(orderDTO);

    }


    // 取消订单
    @PostMapping("/cancel")
    public ResultVO<OrderDTO> cancel(@RequestParam("openid") String openid,
                                     @RequestParam("orderid") String orderid) {

        buyerService.cancelOrder(openid, orderid);
        return ResultVOUtil.success();

    }
}
