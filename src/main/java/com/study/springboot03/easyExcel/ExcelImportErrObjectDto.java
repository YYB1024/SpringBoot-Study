package com.study.springboot03.easyExcel;

import lombok.Data;

/**
 * excel单条数据导入结果
 * 失败结果集ExcelImportErrObjectDto
 * @author yangyanbin
 * @since 20230309
 **/
@Data
public class ExcelImportErrObjectDto {
    private Object object;

    private String errMsg;

    public ExcelImportErrObjectDto(){}

    public ExcelImportErrObjectDto(Object object,String errMsg){
        this.object = object;
        this.errMsg = errMsg;
    }
}
