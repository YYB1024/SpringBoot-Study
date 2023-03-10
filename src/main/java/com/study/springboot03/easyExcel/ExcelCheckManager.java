package com.study.springboot03.easyExcel;

import java.util.List;

/**
 * excel校验接口
 * excel数据业务校验接口ExcelCheckManager
 * @author yangyanbin
 * @since 20230309
 **/

public interface ExcelCheckManager<T> {


    /**
     * 检查导入excel
     *
     * @param objects 对象
     * @return {@link ExcelImportResult}
     */
    ExcelImportResult checkImportExcel(List<T> objects);
}
