package com.study.springboot03.easyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * 客户excel对象
 * @author yangyanbin
 * @since 20230309
 **/
@Data
public class CustomerExcelDto {


    @ExcelProperty(index = 0,value = "客户全称")
    @ColumnWidth(30)
    private String name;

    /**
     * 客户简称
     */
    @ExcelProperty(index = 1,value = "客户简称")
    @ColumnWidth(30)
    @Length(max = 100)
    private String shortName;

    /**
     * 客户编码
     */
    @ExcelProperty(index = 2,value = "客户编码(导入必填)")
    @ColumnWidth(30)
    private String code;

    /**
     * 客户分类
     */
    @ExcelProperty(index = 3,value = "客户分类")
    private String custclassName;


    @ExcelProperty(index = 4,value = "法定代表人")
    @Length(max = 100)
    private String legalbody;



    @ExcelProperty(index = 5,value = "营业期限(导入格式：yyyy-MM-dd)")
    @Pattern(regexp = ExcelPatternMsg.DATE2,message = ExcelPatternMsg.DATE2_MSG)
    private String businessEndDate;

}
