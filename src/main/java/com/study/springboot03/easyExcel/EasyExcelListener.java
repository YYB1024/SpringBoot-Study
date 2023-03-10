package com.study.springboot03.easyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelDataConvertException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 *  有个很重要的点 EasyExcelListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
 * @author yangyanbin
 * @since 20230309
 **/
@Data
public class EasyExcelListener <T>  extends AnalysisEventListener<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EasyExcelListener.class);


    /**
     *  成功结果集
     */
    private List<ExcelImportSucObjectDto> successList = new ArrayList<>();

    /**
     * 失败结果集
     */
    private List<ExcelImportErrObjectDto> errList = new ArrayList<>();

    /**
     * 处理逻辑service
     */
    private ExcelCheckManager excelCheckManager;

    private List<T> list = new ArrayList<>();

    /**
     * excel对象的反射类
     */
    private Class<T> clazz;

    /**
     * 实际使用如果到了spring,请使用下面的有参构造函数
     * @param excelCheckManager
     */
    public EasyExcelListener(ExcelCheckManager excelCheckManager){
        this.excelCheckManager = excelCheckManager;
    }

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     * @param excelCheckManager
     * @param clazz
     */
    public EasyExcelListener(ExcelCheckManager excelCheckManager,Class<T> clazz){
        this.excelCheckManager = excelCheckManager;
        this.clazz = clazz;
    }

    /**
     * 第二步
     *  一行行读取表格内容
     *  这个每一条数据解析都会来调用
     * @param t
     * @param analysisContext
     */
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        String errMsg;
        try {
            //根据excel数据实体中的javax.validation + 正则表达式来校验excel数据
            errMsg = EasyExcelValiHelper.validateEntity(t);
        } catch (NoSuchFieldException e) {
            errMsg = "解析数据出错";
            e.printStackTrace();
        }
        if (!StringUtils.isBlank(errMsg)){
            ExcelImportErrObjectDto excelImportErrObjectDto = new ExcelImportErrObjectDto(t, errMsg);
            errList.add(excelImportErrObjectDto);
        }else{
            list.add(t);
        }
        //每1000条处理一次
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() > 1000){
            //校验(入库)
            ExcelImportResult result = excelCheckManager.checkImportExcel(list);
            successList.addAll(result.getSuccessDtos());
            errList.addAll(result.getErrDtos());
            list.clear();
        }
    }

    //所有数据解析完成了 都会来调用
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
       ExcelImportResult result = excelCheckManager.checkImportExcel(list);

        successList.addAll(result.getSuccessDtos());
        errList.addAll(result.getErrDtos());
        list.clear();
        LOGGER.info("所有数据解析完成！");

    }


    /**
     * 第一步
     * @description: 校验excel头部格式，必须完全匹配
     * @param headMap 传入excel的头部（第一行数据）数据的index,name
     * @param context
     * @throws
     * @return void
     * @author zhy
     * @date 2019/12/24 19:27
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
        if (clazz != null){
            try {
                // 获取ExcelProperty的value（map形式）
                Map<Integer, String> indexNameMap = getIndexNameMap(clazz);
                // 获取ExcelProperty的index(列号)
                Set<Integer> keySet = indexNameMap.keySet();
                for (Integer key : keySet) {
                    if (StringUtils.isBlank(headMap.get(key))){
                        throw new ExcelAnalysisException("解析excel出错，请传入正确格式的excel");
                    }
                    if (!headMap.get(key).equals(indexNameMap.get(key))){
                        throw new ExcelAnalysisException("解析excel出错，请传入正确格式的excel");
                    }
                }

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @description: 获取注解里ExcelProperty的value，用作校验excel
     * @param clazz
     * @throws
     * @return java.util.Map<java.lang.Integer,java.lang.String>
     * @author zhy
     * @date 2019/12/24 19:21
     */
    public Map<Integer,String> getIndexNameMap(Class<T> clazz) throws NoSuchFieldException {
        Map<Integer,String> result = new HashMap<>();
        Field field;
        Field[] fields=clazz.getDeclaredFields();
        for (int i = 0; i <fields.length ; i++) {
            field=clazz.getDeclaredField(fields[i].getName());
            field.setAccessible(true);
            ExcelProperty excelProperty=field.getAnnotation(ExcelProperty.class);
            if(excelProperty!=null){
                int index = excelProperty.index();
                String[] values = excelProperty.value();
                StringBuilder value = new StringBuilder();
                for (String v : values) {
                    value.append(v);
                }
                result.put(index,value.toString());
            }
        }
        return result;
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        //super.onException(exception, context);
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            LOGGER.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex());
        }
    }
}

