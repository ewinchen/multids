package dev.ewin.multids;

import java.util.List;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dev.ewin.multids.mapper.mysql.MysqlMapper;
import dev.ewin.multids.mapper.sqlserver.SqlserverMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

	private final Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

	@Autowired
	@Qualifier("sqlSessionTemplateMysql")
	private SqlSessionTemplate sqlSessionTemplteMysql;

	@Autowired
	@Qualifier("sqlSessionTemplateZprn")
	private SqlSessionTemplate sqlSessionTemplteZprn;

	@Autowired
	private SqlserverMapper sqlserverMapper;

	@Autowired
	private MysqlMapper mysqlMapper;

	@Test
	public void testSqlserver() {
		Map result = sqlserverMapper.selectUserById();
		logger.info(result.toString());
	}

	@Test
	public void testHlt() {
		List resultList = mysqlMapper.selectCity();
		logger.info(resultList.toString());
		MatcherAssert.assertThat(resultList, Is.isA(List.class));
	}

	@Test
	public void testZprn() {
		List resultList = sqlSessionTemplteZprn.selectList("test");
		logger.info(resultList.toString());
		MatcherAssert.assertThat(resultList, Is.isA(List.class));
	}

}
