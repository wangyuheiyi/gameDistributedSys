package com.gamelogservice.dao;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;
/**
 * 
 * @author Administrator
 * 查询的自定义基类 可以扩展自己的查询越剧的接口
 * @param <T>
 * @param <ID>
 * 添加@NoRepositoryBean标注，这样Spring Data Jpa在启动时就不会去实例化BaseRepository这个接口
 */
@NoRepositoryBean
public interface IBaseJpaRepository<T,ID extends Serializable> extends JpaRepositoryImplementation<T, ID>{
	public T getOneBySql(Class<T> clazz, String sql, Map<String, Object> params)
			throws Exception;
}
