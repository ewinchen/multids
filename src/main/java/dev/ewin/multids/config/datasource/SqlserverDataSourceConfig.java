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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan(basePackages = "dev.ewin.multids.mapper.sqlserver", sqlSessionFactoryRef = "sqlSessionFactorySqlserver", sqlSessionTemplateRef = "sqlSessionTemplateSqlserver")
public class SqlserverDataSourceConfig {

	@Bean(name = "dataSourceSqlserver")
	@Primary
	@ConfigurationProperties(prefix = "app.datasource.sqlserver")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "jdbcTemplateSqlserver")
	@Primary
	public JdbcTemplate jdbcTemplate(@Qualifier("dataSourceSqlserver") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean(name = "sqlSessionFactorySqlserver")
	@Primary
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceSqlserver") DataSource dataSource)
	        throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "sqlSessionTemplateSqlserver")
	@Primary
	public SqlSessionTemplate sqlSessionTemplate(
	        @Qualifier("sqlSessionFactorySqlserver") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = "transactionManagerSqlserver")
	@Primary
	public PlatformTransactionManager transactionManager(@Qualifier("dataSourceSqlserver") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
