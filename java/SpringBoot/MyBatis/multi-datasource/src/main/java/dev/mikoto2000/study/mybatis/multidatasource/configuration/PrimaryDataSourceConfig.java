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
import org.springframework.context.annotation.Primary;

/**
 * PrimaryDataSourceConfig
 */
@Configuration
@MapperScan(basePackages = PrimaryDataSourceConfig.BASE_PACKAGES
            , sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryDataSourceConfig {
  public static final String BASE_PACKAGES = "dev.mikoto2000.study.mybatis.multidatasource.primary";

  @Primary
  @Bean(name = "primaryDataSource")
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean(name = "primarySqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource)
    throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(primaryDataSource);
    return bean.getObject();
  }

  @Bean(name = "primarySqlSessionTemplate")
  public SqlSessionTemplate sqlSessionTemplate(
      @Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
      return new SqlSessionTemplate(sqlSessionFactory);
      }
}
