package com.example.springutils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.SystemPropertyUtils;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class SpringutilsApplication {

  private static final Log log = LogFactory.getLog(SpringutilsApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(SpringutilsApplication.class, args);
  }

  @Bean
  DemoClass demoClass() {
    return new DemoClass();
  }

  @Bean
  CommandLineRunner demo(DemoClass demo) {
    return args -> {
      Assert.notNull(demo.getList(), "the list can't be null");
      beanUtils(demo);
      classUtils();
      systemPropertyUtils();
      fileCopyUtils();
    };
  }

  private void fileCopyUtils() {
    File file = new File(SystemPropertyUtils.resolvePlaceholders("${user.dir}"), "/build.gradle");
    try (Reader r = new FileReader(file)) {
      log.info("contents of file " + FileCopyUtils.copyToString(r));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void systemPropertyUtils() {
    String resolvedText = SystemPropertyUtils.resolvePlaceholders("my home directory is ${user.home}");
    log.info("resolved text: " + resolvedText);
  }

  private void classUtils() {
    Constructor<DemoClass> demoClassConstructor = ClassUtils.getConstructorIfAvailable(DemoClass.class);
    log.info("demoClassConstructor: " + demoClassConstructor);

    try {
      DemoClass demoClass = demoClassConstructor.newInstance();
      log.info("newInstance's demoClass: " + demoClass);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void beanUtils(DemoClass demo) {
    PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(demo.getClass());
    for (PropertyDescriptor pd : descriptors) {
      log.info("pd: " + pd.getName());
      log.info("pd.readMethod: " + pd.getReadMethod().getName());
    }
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class DemoClass {
    private List<Map<String, Object>> list = new ArrayList<>();
  }
}
