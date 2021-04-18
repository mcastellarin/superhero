package com.hiberus.superhero.aspects;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.math.NumberUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MethodExecutionInformationAspect {
	private final Logger LOGGER = LoggerFactory.getLogger(MethodExecutionInformationAspect.class);

	private StringBuilder str;

	@Before("@annotation(com.hiberus.superhero.aspects.annotation.MethodExecutionInformationAnnotation)")
	public void showLogMethodExecution(JoinPoint joinPoint) {
		MethodSignature method = (MethodSignature) joinPoint.getSignature();

		str = new StringBuilder()
				.append("Class [ ")
				.append(method.getDeclaringType().getSimpleName())
				.append(" ] ")
				.append(" - Method [ ")
				.append(method.getName())
				.append(" ]");

		if(!NumberUtils.INTEGER_ZERO.equals(method.getParameterNames().length)) {
			String paramsName = Stream.of(method.getParameterNames())
					.map(paramName -> paramName.toString())
	                .collect(Collectors.joining(","));

			String paramsValue = Stream.of(joinPoint.getArgs())
					.map(arg -> arg.toString())
	                .collect(Collectors.joining(","));

			str.append(" gets called with parameters of names [ ")
			   .append(paramsName)
			   .append(" ] with values [ ")
			   .append(paramsValue)
			   .append(" ]");
		}

		LOGGER.info(str.toString());
	}

	@Around("@annotation(com.hiberus.superhero.aspects.annotation.MethodExecutionInformationAnnotation)")
	public Object showLogExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object proceed = joinPoint.proceed();
		long duration = System.currentTimeMillis() - startTime;

		str = new StringBuilder()
				.append("Execution took [ ")
				.append(duration)
				.append(" ms ]");

		LOGGER.info(str.toString());
		return proceed;
	}
}
