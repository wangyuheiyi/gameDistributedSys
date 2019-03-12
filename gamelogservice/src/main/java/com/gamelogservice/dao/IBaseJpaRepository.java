package com.gamelogservice.dao;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
/**
 * 
 * @author Administrator
 *
 * @param <T>
 * @param <ID>
 * 添加@NoRepositoryBean标注，这样Spring Data Jpa在启动时就不会去实例化BaseRepository这个接口
 */
@NoRepositoryBean
public interface IBaseJpaRepository<T,ID extends Serializable> extends JpaRepository<T, ID>{
	public T getOneBySql(Class<T> clazz, String sql, Map<String, Object> params)
			throws Exception;
}
