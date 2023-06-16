package com.hygge.shop.common.codeGenerator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

/**
 * @program: shop
 * @description: Mybatis Plus代码生成器AutoGenerator
 * @author: hygge
 * @create: 2023/03/22
 */
public class CodeGenerator {
  //获取当前git分支名称
  public static String getCurrentGitBranch() throws IOException, InterruptedException {
    Process process = Runtime.getRuntime().exec( "git rev-parse --abbrev-ref HEAD" );
    process.waitFor();

    BufferedReader reader = new BufferedReader(
      new InputStreamReader( process.getInputStream() ) );

    return reader.readLine();
  }

  public static void main(String[] args) {
    // 数据源配置
    FastAutoGenerator.create("jdbc:mysql://116.62.140.246:3306/shop?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false", "root", "123456")
      .globalConfig(builder -> {
        builder.author("hygge")        // 设置作者
          .enableSwagger()        // 开启 swagger 模式 默认值:false
          .disableOpenDir()       // 禁止打开输出目录 默认值:true
          .commentDate("yyyy-MM-dd") // 注释日期
          .dateType(DateType.ONLY_DATE)   //定义生成的实体类中日期类型 DateType.ONLY_DATE 默认值: DateType.TIME_PACK
          .outputDir(System.getProperty("user.dir") + "/shop-mall/src/main/java"); // 指定输出目录
      })

      .packageConfig(builder -> {
        builder.parent("com.hygge.shop.mall") // 父包模块名
          .controller("controller")   //Controller 包名 默认值:controller
          .entity("entity")           //Entity 包名 默认值:entity
          .service("service")         //Service 包名 默认值:service
          .mapper("mapper")           //Mapper 包名 默认值:mapper
          .other("model")
          //.moduleName("xxx")        // 设置父包模块名 默认值:无
          .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/shop-mall/src/main/resources/mapper")); // 设置mapperXml生成路径
        //默认存放在mapper的xml下
      })

      .strategyConfig(builder -> {
        builder.addInclude("oms_order_item") // 设置需要生成的表名 可边长参数“user”, “user1”
//          .addTablePrefix("t_") // 设置过滤表前缀

          .entityBuilder()// 实体类策略配置
          .idType(IdType.ASSIGN_ID)//主键策略  雪花算法自动生成的id
          .versionColumnName("version") // 乐观锁数据库字段
          .versionPropertyName("version") // 乐观锁实体类名称
          .addTableFills(new Column("create_time", FieldFill.INSERT)) // 自动填充配置
          .addTableFills(new Property("update_time", FieldFill.INSERT_UPDATE))
          .enableLombok() //开启lombok
          .logicDeleteColumnName("deleted")// 说明逻辑删除是哪个字段
          .enableTableFieldAnnotation()// 属性加上注解说明
          .formatFileName("%s") // Entity 文件名称
          .naming(NamingStrategy.underline_to_camel) // 表名 下划线 -> 驼峰命名
          .columnNaming(NamingStrategy.underline_to_camel) // 字段名 下划线 -> 驼峰命名

          .controllerBuilder() //controller 策略配置
          .formatFileName("%sController")
          .enableRestStyle() // 开启RestController注解

          .serviceBuilder()//service策略配置
          .formatServiceFileName("%sService")
          .formatServiceImplFileName("%sServiceImpl")

          .mapperBuilder()// mapper策略配置
          .formatMapperFileName("%sMapper")
          .enableMapperAnnotation()//@mapper注解开启
          .formatXmlFileName("%sMapper");
      })
      .execute();

  }
}
