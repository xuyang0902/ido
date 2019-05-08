
### Spring-实例化Bean过程
    如何实例化一个bean？

#### 假如要我们自己来实例化一个对象，我们会怎么做？

```
//如此简单 new一个对象呗
SimpleBean simpleBean = new SimpleBean();
simpleBean.setXXX();
```

#### bean管理是Spring Ioc核心的思想：beanFacotry≈类似哆唻A梦的口袋

    简单bean debug请参考 com.ido.popular.spring.ioc.Main
    FactoryBean debug请参考  com.ido.popular.spring.aop.proxyfactory.Main 【AOP的一种实现方式】
    
    >>> 源码解析
    org.springframework.beans.factory.support.AbstractBeanFactory#doGetBean

```
    protected <T> T doGetBean(
			final String name, final Class<T> requiredType, final Object[] args, boolean typeCheckOnly)
			throws BeansException {


        //获取真正的beanName
        final String beanName = transformedBeanName(name);
		Object bean;

		//先从缓存尝试获取bean实例
		Object sharedInstance = getSingleton(beanName);
		if (sharedInstance != null && args == null) {
		    //非FactoryBean直接返回，FactoryBean需要加工
			bean = getObjectForBeanInstance(sharedInstance, name, beanName, null);
		} else {
		
			if (isPrototypeCurrentlyInCreation(beanName)) {
				throw new BeanCurrentlyInCreationException(beanName);
			}

            //当前容器没有该bean的信息，交给parentIoC处理
 			BeanFactory parentBeanFactory = getParentBeanFactory();
			if (parentBeanFactory != null && !containsBeanDefinition(beanName)) {
				String nameToLookup = originalBeanName(name);
				if (args != null) {
					return (T) parentBeanFactory.getBean(nameToLookup, args);
				}
				else {
					return parentBeanFactory.getBean(nameToLookup, requiredType);
				}
			}

			if (!typeCheckOnly) {
				markBeanAsCreated(beanName);
			}

			try {
			    
			    //先从mergedBeanDefinitions拿，拿不到从beanDefinitionMap拿放到mergedBeanDefinitions中
				final RootBeanDefinition mbd = getMergedLocalBeanDefinition(beanName);
				checkMergedBeanDefinition(mbd, beanName, args);

				// 检查依赖  这个在本篇文章里不做分析
				String[] dependsOn = mbd.getDependsOn();
				if (dependsOn != null) {
					for (String dependsOnBean : dependsOn) {
						if (isDependent(beanName, dependsOnBean)) {
							throw new BeanCreationException(mbd.getResourceDescription(), beanName,
									"Circular depends-on relationship between '" + beanName + "' and '" + dependsOnBean + "'");
						}
						registerDependentBean(dependsOnBean, beanName);
						getBean(dependsOnBean);
					}
				}

				
				if (mbd.isSingleton()) {
				
				    //单列 创建单列实例【主要看这个】
					sharedInstance = getSingleton(beanName, new ObjectFactory<Object>() {
						@Override
						public Object getObject() throws BeansException {
							try {
								return createBean(beanName, mbd, args);
							}
							catch (BeansException ex) {
								// Explicitly remove instance from singleton cache: It might have been put there
								// eagerly by the creation process, to allow for circular reference resolution.
								// Also remove any beans that received a temporary reference to the bean.
								destroySingleton(beanName);
								throw ex;
							}
						}
					});
					
					//非FactoryBean直接返回，FactoryBean需要加工
					bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
					
				}else if (mbd.isPrototype()) {
					// prototype 每次请求都会创建一个实例【本篇不分析】
				}else {
				    //根据作用域来获取实例【本篇不分析】
				}
			}
			catch (BeansException ex) {
				cleanupAfterBeanCreationFailure(beanName);
				throw ex;
			}
		}
        
        //返回实例化对象
		return (T) bean;
	}


```

```
    public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
		Assert.notNull(beanName, "'beanName' must not be null");
		synchronized (this.singletonObjects) {
		    //缓存先取
			Object singletonObject = this.singletonObjects.get(beanName);
			if (singletonObject == null) {
				
				//singletonsCurrentlyInCreation缓存记下正在创建bean
				beforeSingletonCreation(beanName);
				
				boolean newSingleton = false;
				try {
				     
				    //工厂获取bean 核心还是要关注createBean(beanName, mbd, args);
					singletonObject = singletonFactory.getObject();
					newSingleton = true;
				}
				catch (Exception ex) {
					//简洁代码...  
					throw ex;
				}
				finally {
					
					//singletonsCurrentlyInCreation缓存移除 bean创建好了
					afterSingletonCreation(beanName);
				}
				if (newSingleton) {
				
				    /**
				     *   缓存处理
				     *  【循环依赖篇 singletonObjects && singletonFactories && earlySingletonObjects三个缓存会特别展开，这里不展开】    
				     *   this.singletonObjects.put(beanName, (singletonObject != null ? singletonObject : NULL_OBJECT));
                     *   this.singletonFactories.remove(beanName);
                     *   this.earlySingletonObjects.remove(beanName);
                     *   this.registeredSingletons.add(beanName);
				     */
					addSingleton(beanName, singletonObject);
				}
			}
			return (singletonObject != NULL_OBJECT ? singletonObject : null);
		}
	}

```

```
    org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#createBean()

    protected Object createBean(String beanName, RootBeanDefinition mbd, Object[] args) throws BeanCreationException {
		RootBeanDefinition mbdToUse = mbd;

        //加载class
		Class<?> resolvedClass = resolveBeanClass(mbd, beanName);
		if (resolvedClass != null && !mbd.hasBeanClass() && mbd.getBeanClassName() != null) {
			mbdToUse = new RootBeanDefinition(mbd);
			mbdToUse.setBeanClass(resolvedClass);
		}

		try {
		    //准备重写的方法
			mbdToUse.prepareMethodOverrides();
		}
		catch (BeanDefinitionValidationException ex) {
			throw new BeanDefinitionStoreException(mbdToUse.getResourceDescription(),
					beanName, "Validation of method overrides failed", ex);
		}

		try {
			// 1、在初始化之前给BeanPostProcessors一个机会 可以返回一个bean 【BeanPostProcessors扩展处】
			Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
			if (bean != null) {
				return bean;
			}
		}
		catch (Throwable ex) {
			throw new BeanCreationException(mbdToUse.getResourceDescription(), beanName,
					"BeanPostProcessor before instantiation of bean failed", ex);
		}

        // 2、实例化前bean并没有操作，那么我们正常流程 需要实例化bean
		Object beanInstance = doCreateBean(beanName, mbdToUse, args);
		return beanInstance;
	}
```

```
    1、 在bean正常真正实例化前开放的扩展点
    
	protected Object resolveBeforeInstantiation(String beanName, RootBeanDefinition mbd) {
		Object bean = null;
		if (!Boolean.FALSE.equals(mbd.beforeInstantiationResolved)) {
			// 如果beanFactory有InstantiationAwareBeanPostProcessor 在实例化前，可以骚操作一波。 直接自定义返回你想要的bean
			if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
				Class<?> targetType = determineTargetType(beanName, mbd);
				if (targetType != null) {
				    //交给InstantiationAwareBeanPostProcessor处理
					bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
					if (bean != null) {
					    //bean实例化好的话，需要交给beanProcessor处理postProcessAfterInitialization
						bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
					}
				}
			}
			mbd.beforeInstantiationResolved = (bean != null);
		}
		return bean;
	}
	
	//InstantiationAwareBeanPostProcessor实例化前操作一波
	protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName)
            throws BeansException {

        for (BeanPostProcessor bp : getBeanPostProcessors()) {
            if (bp instanceof InstantiationAwareBeanPostProcessor) {
                InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
                Object result = ibp.postProcessBeforeInstantiation(beanClass, beanName);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
    	
    //BeanPostProcessor实例化好后操作一波
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException {

        Object result = existingBean;
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            result = beanProcessor.postProcessAfterInitialization(result, beanName);
            if (result == null) {
                return result;
            }
        }
        return result;
    }


```

```
    //2、常规创建bean的流程 【重点关注】
    
    protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final Object[] args) {
		
		//beanName, mbd, args 包装了一个beanWrapper 主要是用来实例化bean的。构造器反射创建对象
		BeanWrapper instanceWrapper = null;
		if (mbd.isSingleton()) {
			instanceWrapper = this.factoryBeanInstanceCache.remove(beanName);
		}
		if (instanceWrapper == null) {
			instanceWrapper = createBeanInstance(beanName, mbd, args);
		}
		final Object bean = (instanceWrapper != null ? instanceWrapper.getWrappedInstance() : null);
		Class<?> beanType = (instanceWrapper != null ? instanceWrapper.getWrappedClass() : null);

		// MergedBeanDefinitionPostProcessor#postProcessMergedBeanDefinition开始秀了
		synchronized (mbd.postProcessingLock) {
			if (!mbd.postProcessed) {
				applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
				mbd.postProcessed = true;
			}
		}

        //解决循环依赖的问题，这里暂时不展开了。
		boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences &&
				isSingletonCurrentlyInCreation(beanName));
		if (earlySingletonExposure) {
			addSingletonFactory(beanName, new ObjectFactory<Object>() {
				@Override
				public Object getObject() throws BeansException {
					return getEarlyBeanReference(beanName, mbd, bean);
				}
			});
		}

		//初始化bean
		Object exposedObject = bean;
		try {
		    
		    //填充bean的属性  存在InstantiationAwareBeanPostProcessor 需要执行
			populateBean(beanName, mbd, instanceWrapper);
			
			if (exposedObject != null) {
			    /**
			     *  1、如果bean实现了XXXAware的接口的，set进去
			     *  2、执行beanProcessor.postProcessBeforeInitialization  初始化前调用
			     *  3、InitializingBean.afterPropertiesSet  && 调用init-method
			     *  4、执行 beanProcessor.postProcessAfterInitialization  初始化后调用
			     */
				exposedObject = initializeBean(beanName, exposedObject, mbd);
			}
		}
		catch (Throwable ex) {
			if (ex instanceof BeanCreationException && beanName.equals(((BeanCreationException) ex).getBeanName())) {
				throw (BeanCreationException) ex;
			}
			else {
				throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Initialization of bean failed", ex);
			}
		}

		try {
		    //注册disposable  bean销毁的时候回调
			registerDisposableBeanIfNecessary(beanName, bean, mbd);
		}
		catch (BeanDefinitionValidationException ex) {
			throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Invalid destruction signature", ex);
		}

		return exposedObject;
	}
	
	
	protected void applyMergedBeanDefinitionPostProcessors(RootBeanDefinition mbd, Class<?> beanType, String beanName)
    			throws BeansException {
    
        try {
            for (BeanPostProcessor bp : getBeanPostProcessors()) {
                if (bp instanceof MergedBeanDefinitionPostProcessor) {
                    MergedBeanDefinitionPostProcessor bdp = (MergedBeanDefinitionPostProcessor) bp;
                    bdp.postProcessMergedBeanDefinition(mbd, beanType, beanName);
                }
            }
        }
        catch (Exception ex) {
            throw new BeanCreationException(mbd.getResourceDescription(), beanName,
                    "Post-processing failed of bean type [" + beanType + "] failed", ex);
        }
    }
    	
    	
```

## about ME        
[雨人 github](https://github.com/xuyang0902)









