package com.sxu.fdsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sxu.fdsystem.common.R;
import com.sxu.fdsystem.entity.OrderDetail;
import com.sxu.fdsystem.entity.OrderDish;
import com.sxu.fdsystem.entity.Orders;
import com.sxu.fdsystem.entity.Table;
import com.sxu.fdsystem.service.OrderDishService;
import com.sxu.fdsystem.service.OrdersService;
import com.sxu.fdsystem.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDishService orderDishService;

    @Autowired
    private TableService tableService;

    //查表
    @GetMapping("/list")
    public R<List<Orders>> list(){
        try{
            return R.success(ordersService.list());
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
    }

    //查单体
    @GetMapping
    public R<Orders> get(int ordersId){
        try{
            return R.success(ordersService.getById(ordersId));
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
    }

    //查细节
    @GetMapping("/detail/{uid}")
    public R<List<OrderDetail>> detail(@PathVariable("uid") Integer userId){
        try{
            List<OrderDetail> details = new ArrayList<>();
            LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Orders::getUserId,userId);
            List<Orders> os = ordersService.list(wrapper);
            for(Orders o : os){
                OrderDetail detail = new OrderDetail();
                detail.setOrderId(o.getOrderId());
                detail.setAmount(o.getAmount());
                String tas = "";
                LambdaQueryWrapper<Table> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Table::getOrderId,o.getOrderId());
                List<Table> t = tableService.list(queryWrapper);
                for(Table tt : t){
                    tas +=  tt.getTableId().toString() + ",";
                }
                detail.setTables(tas);
                details.add(detail);
            }
            return R.success(details);
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
    }

    //增
    @PostMapping
    @Transactional
    public R<String> add(String preorderTime,String note,Integer peopleNum,
                         Float amount, Integer userid,
                         @RequestParam(value = "ids",required = false) List<Integer> ids,
                         @RequestParam(value = "size",required = false) List<String> size){
        try{
            Integer id = new Random().nextInt(Integer.MAX_VALUE);
            Orders orders  = new Orders();
            orders.setOrderId(id);
            orders.setOrderTime(LocalDateTime.now());
            orders.setPreorderTime(LocalDateTime.parse(preorderTime,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
            orders.setPayAmount(amount);
            orders.setPeopleNum(peopleNum);
            orders.setNote(note);
            orders.setStatus("0");
            orders.setUserId(userid);
            ordersService.save(orders);
            int length = ids.size();
            for(int i = 0;i< length;i++){
                orderDishService.save(new OrderDish(ids.get(i),id,size.get(i)));
            }

        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause() == null ? String.valueOf(e) : e.getCause().getMessage());
        }
        return  R.success("添加成功!");
    }

    //改（修改实付金额、上菜时间、结账时间和状态）
    @PostMapping("/update")
    public R<String> update(int orderId, float pay_amount, String status){
        try{
            // 创建一个 UpdateWrapper 对象，设置需要更新的条件和更新的字段
            UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("order_id", orderId); // 设置更新条件，例如根据用户ID更新
            // 设置需要更新的字段和值
            Orders orders = new Orders();
            orders.setPayAmount(pay_amount);
            orders.setStatus(status);
            if(Objects.equals(status, "2"))
                orders.setStartTime(LocalDateTime.now());
            if(Objects.equals(status, "3"))
                orders.setEndTime(LocalDateTime.now());
            // 执行更新操作
            ordersService.update(orders, updateWrapper);
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
        return  R.success("修改成功!");
    }

    //改（发评论）
    @PostMapping("/comment")
    public R<String> update(int orderId, int score,String text){
        try{
            // 创建一个 UpdateWrapper 对象，设置需要更新的条件和更新的字段
            UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("order_id", orderId); // 设置更新条件，例如根据用户ID更新
            // 设置需要更新的字段和值
            Orders orders = new Orders();
            orders.setScore(score);
            orders.setCommentText(text);
            // 执行更新操作
            ordersService.update(orders, updateWrapper);
        }catch (Exception e){
            //mysql报错结果
            return R.error(e.getCause().getMessage());
        }
        return  R.success("修改成功!");
    }

}
