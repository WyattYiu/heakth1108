package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import com.itheima.health.service.OrderService;
import com.itheima.health.service.ReportService;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    MemberService memberService;

    @Reference
    OrderService orderService;

    @Reference
    ReportService reportService;


    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){

        try {
            Calendar calendar = Calendar.getInstance();
            // 前移12个月
            calendar.add(Calendar.MONTH,-12);

            HashMap<String, Object> map = new HashMap<>();

            List<String> months = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                calendar.add(Calendar.MONTH,1);
                String date = new SimpleDateFormat("YYYY-MM").format(calendar.getTime());
                months.add(date);
            }
            map.put("months",months);

            List<Integer> memberCount = memberService.getMemberReport(months);
            map.put("memberCount",memberCount);

            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }


    @RequestMapping("/getSetmealReport")
    public Result getOrderMember(){

        // 查询出所有的套餐名称和id
        try {
            Map<String,Object> map = orderService.findAll();
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }


    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        try {
            Map<String,Object> map = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String,Object> map = reportService.getBusinessReportData();
            String reportDate = (String) map.get("reportDate");
            Integer todayNewMember = (Integer) map.get("todayNewMember");
            Integer totalMember = (Integer) map.get("totalMember");
            Integer thisWeekNewMember = (Integer) map.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) map.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) map.get("todayOrderNumber");
            Integer todayVisitsNumber = (Integer) map.get("todayVisitsNumber");
            Integer thisWeekOrderNumber = (Integer) map.get("thisWeekOrderNumber");
            Integer thisWeekVisitsNumber = (Integer) map.get("thisWeekVisitsNumber");
            Integer thisMonthOrderNumber = (Integer) map.get("thisMonthOrderNumber");
            Integer thisMonthVisitsNumber = (Integer) map.get("thisMonthVisitsNumber");
            List<Map<String,Object>> hotSetmeal = (List<Map<String, Object>>) map.get("hotSetmeal");


            // 获取模版文件路径
            String realPath = request.getSession().getServletContext().getRealPath("/template/report_template.xlsx");

            // 获取 workbook对象
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(realPath)));

            // 获取工作表 1
            XSSFSheet sheet = workbook.getSheetAt(0);

            // 日期
            sheet.getRow(2).getCell(5).setCellValue(reportDate);
            // 今日新增会员数
            sheet.getRow(4).getCell(5).setCellValue(todayNewMember);
            // 总会员数
            sheet.getRow(4).getCell(7).setCellValue(totalMember);
            // 本周新增会员数
            sheet.getRow(5).getCell(5).setCellValue(thisWeekNewMember);
            // 本月新增会员数
            sheet.getRow(5).getCell(7).setCellValue(thisMonthNewMember);
            // 今日预约数
            sheet.getRow(7).getCell(5).setCellValue(todayOrderNumber);
            // 今日到诊数
            sheet.getRow(7).getCell(7).setCellValue(todayVisitsNumber);
            // 本周预约数
            sheet.getRow(8).getCell(5).setCellValue(thisWeekOrderNumber);
            // 本周到诊数
            sheet.getRow(8).getCell(7).setCellValue(thisWeekVisitsNumber);
            // 本月预约数
            sheet.getRow(9).getCell(5).setCellValue(thisMonthOrderNumber);
            // 本月到诊数
            sheet.getRow(9).getCell(7).setCellValue(thisMonthVisitsNumber);


            // 热门套餐
            int row = 12;
            for (Map<String, Object> SetmealMap : hotSetmeal) {
                sheet.getRow(row).getCell(4).setCellValue((String) SetmealMap.get("name"));
                sheet.getRow(row).getCell(5).setCellValue((Long) SetmealMap.get("setmeal_count"));
                sheet.getRow(row).getCell(6).setCellValue(((BigDecimal)SetmealMap.get("proportion")).doubleValue());
                row++;
            }
            //通过输出流进行文件下载
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(out);

            out.flush();
            out.close();
            workbook.close();

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }
}
