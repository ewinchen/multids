package dev.ewin.multids.config.datasource;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan(basePackages = "dev.ewin.multids.mapper.zprn", sqlSessionFactoryRef = "sqlSessionFactoryZprn", sqlSessionTemplateRef = "sqlSessionTemplateZprn")
public class ZprnDataSourceConfig {

	@Bean(name = "dataSourceZprn")
	@ConfigurationProperties(prefix = "app.datasource.zprn")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "jdbcTemplateZprn")
	public JdbcTemplate jdbcTemplate(@Qualifier("dataSourceZprn") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean(name = "sqlSessionFactoryZprn")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceZprn") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config-zprn.xml"));
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:dev/ewin/multids/mapper/zprn/**/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "sqlSessionTemplateZprn")
	public SqlSessionTemplate sqlSessionTemplate(
	        @Qualifier("sqlSessionFactoryZprn") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = "transactionManagerZprn")
	public PlatformTransactionManager transactionManager(@Qualifier("dataSourceZprn") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
