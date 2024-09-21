package com.etoak.java.controller;

import com.etoak.java.entity.Donate;
import com.etoak.java.feign.IBookServiceFeign;
import com.etoak.java.feign.IUserServiceFeign;
import com.etoak.java.service.impl.DonateServiceImpl;
import com.etoak.java.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/***
 * @Author 土豆真人 QQ:1972588587
 */
@RestController
@RequestMapping("/donate")
public class DonateController {

    @Autowired
    DonateServiceImpl donateService;
    // 如果feign接口注入时提示红线, 无法注入，则需要检查有么有开启spingCloud的feign支持
    @Autowired
    IUserServiceFeign userServiceFeign;
    @Autowired
    IBookServiceFeign bookServiceFeign;

    /**
     * 1.新增捐献记录
     *
     * @param donate
     * @return
     */
    @RequestMapping("/addDonate")
    public ResultVO addDonate(Donate donate) {
        System.out.println("=====donate:" + donate);
        Date date = new Date();
        donate.setDonateTime(date);
        return ResultVO.success(donateService.addDonate(donate));
    }

    /**
     * 2.删除捐赠记录（假删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteDonate")
    public ResultVO deleteDonate(int id) {
        System.out.println("=====donateId:" + id);
        return ResultVO.success(donateService.deleteDonate(id));
    }

    /**
     * 3.更新捐赠记录
     *
     * @param donate
     * @return
     */
    @RequestMapping("/updateDonate")
    public ResultVO updateDonate(Donate donate) {
        return ResultVO.success(donateService.updateDonate(donate));
    }

    /**
     * 4.查询捐赠记录（仅is_delete == 0
     *
     * @param donatorId
     * @return
     */
    @RequestMapping("/selectDonate")
    public ResultVO selectDonate(int donatorId) {
        return ResultVO.success(donateService.selectDonate(donatorId));
    }

    /**
     * 5.确认捐赠
     *
     * @param id
     * @return
     */
    @RequestMapping("/confirmDonate")
    public ResultVO confirmDonate(int id) {
        return ResultVO.success(donateService.confirmDonate(id));
    }

    /**
     * 6.驳回捐赠
     *
     * @param id
     * @return
     */
    @RequestMapping("/rejectDonate")
    public ResultVO rejectDonate(int id) {
        return ResultVO.success(donateService.rejectDonate(id));
    }

    /**
     * 7.使用积分兑换书籍
     *
     * @param bookNo
     * @param userId
     * @return
     */
    @RequestMapping("/redeemDonate")
    public ResultVO redeemBookByPoints(String bookNo, int userId) {
        int result = donateService.redeemBookByPoints(bookNo, userId);
        if (result == 1) {
            return ResultVO.success("借阅成功");
        } else if (result == 2) {
            return ResultVO.failed("积分不足");
        } else if (result == 3) {
            return ResultVO.failed("书籍已经借出或不可用");
        }
        return ResultVO.failed("未知错误");
    }

}