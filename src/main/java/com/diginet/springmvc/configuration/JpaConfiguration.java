package com.diginet.springmvc.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.swing.JOptionPane;

import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.digicon.util.ConfigHelper;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class JpaConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public DataSource dataSource() throws SQLException {
		DriverManagerDataSource dataSource = null;
		dataSource = new DriverManagerDataSource() {
			protected Connection getConnectionFromDriverManager(String url, Properties props) throws SQLException {
				Connection conn = null;
				try {
					conn = super.getConnectionFromDriverManager(url, props);
				} catch (Exception e) {
					try {
						Class.forName("org.h2.Driver");
						JdbcConnectionPool cp = JdbcConnectionPool.create("jdbc:h2:~/runtime", "", "");
						
						conn = cp.getConnection();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} 
				}
				return conn;
			}
		};
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));  
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url").replace("{DBPORT}", ConfigHelper.getDbPortFromConfigFile())); 
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));  
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException, SQLException {
		LocalContainerEntityManagerFactoryBean factoryBean = null;
		try {
			factoryBean = new LocalContainerEntityManagerFactoryBean();
			factoryBean.setDataSource(dataSource());
			factoryBean.setPackagesToScan(new String[] { "com.diginet.springmvc.entity" });
			factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
			factoryBean.setJpaProperties(jpaProperties());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Banco caiu!!! LocalContainerEntityManagerFactoryBean (" + e.getMessage() + ")");
		}
		return factoryBean;
	}

	/*
	 * Provider specific adapter.
	 */ 
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		return hibernateJpaVendorAdapter;
	}

	/*
	 * Here you can specify any provider specific properties.
	 */
	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		return properties;
	}

	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

}
