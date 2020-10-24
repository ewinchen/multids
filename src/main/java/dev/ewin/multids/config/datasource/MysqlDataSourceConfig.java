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
@MapperScan(basePackages = "dev.ewin.multids.mapper.mysql", sqlSessionFactoryRef = "sqlSessionFactoryMysql", sqlSessionTemplateRef = "sqlSessionTemplateMysql")
public class MysqlDataSourceConfig {

	@Bean(name = "dataSourceMysql")
	@ConfigurationProperties(prefix = "app.datasource.mysql")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "jdbcTemplateMysql")
	public JdbcTemplate jdbcTemplate(@Qualifier("dataSourceMysql") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean(name = "sqlSessionFactoryMysql")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceMysql") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config-mysql.xml"));
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:dev/ewin/multids/mapper/mysql/**/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "sqlSessionTemplateMysql")
	public SqlSessionTemplate sqlSessionTemplate(
	        @Qualifier("sqlSessionFactoryMysql") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = "transactionManagerMysql")
	public PlatformTransactionManager transactionManager(@Qualifier("dataSourceMysql") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
