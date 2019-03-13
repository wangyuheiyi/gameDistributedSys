package com.gamelogservice.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.Assert;

import com.gamelogservice.dao.repository.impl.BaseJpaRepositorympl;

/**
 * 自定义获取数据库实例bean的工厂类 这样可以调用我们自己的工厂实现
 * @author Administrator
 *
 * @param <R> jpa的数据库谅解
 * @param <T>
 * @param <I>
 */
public class BaseRepositoryFactoryBean <R extends JpaRepository<T, I>, T,
I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I>{

	public BaseRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	//@SuppressWarnings("rawtypes") 传递参数类型
	@Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new BaseRepositoryFactory<T,I>(em);
    }
	
	//创建一个内部类，该类不用在外部访问
    private static class BaseRepositoryFactory<T, I extends Serializable>
            extends JpaRepositoryFactory {

        public BaseRepositoryFactory(EntityManager em) {
            super(em);
        }

        //设置具体的实现类是BaseRepositoryImpl
        @Override
        protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information,
    			EntityManager entityManager) {
    		JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
    		Object repository = getTargetRepositoryViaReflection(information, entityInformation, entityManager);
    		Assert.isInstanceOf(BaseJpaRepositorympl.class, repository);
    		return (JpaRepositoryImplementation<?, ?>) repository;
    	}

        //设置具体的实现类的class
        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseJpaRepositorympl.class;
        }
    }

}
