package dev.mikoto2000.study.mybatis.multidatasource.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SecondaryDataSourceConfig
 */
@Configuration
@MapperScan(basePackages = SecondaryDataSourceConfig.BASE_PACKAGES
            , sqlSessionTemplateRef = "secondarySqlSessionTemplate")
public class SecondaryDataSourceConfig {
  public static final String BASE_PACKAGES = "dev.mikoto2000.study.mybatis.multidatasource.secondary";

  @Bean(name = "secondaryDataSource")
  @ConfigurationProperties(prefix = "secondary.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "secondarySqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory(@Qualifier("secondaryDataSource") DataSource secondaryDataSource)
    throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(secondaryDataSource);
    return bean.getObject();
  }

  @Bean(name = "secondarySqlSessionTemplate")
  public SqlSessionTemplate sqlSessionTemplate(
      @Qualifier("secondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
      return new SqlSessionTemplate(sqlSessionFactory);
      }
}

