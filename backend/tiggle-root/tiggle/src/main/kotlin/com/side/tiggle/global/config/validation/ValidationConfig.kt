package com.side.tiggle.global.config.validation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.Validator
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 모든 Validator(JSR-303 및 커스텀)를 글로벌 범위에서 WebDataBinder에 등록하는 클래스입니다.
 *
 * @since 2025-06-20
 */
@Configuration
class ValidationConfig {

    @Bean
    fun jsrValidator(): LocalValidatorFactoryBean {
        return LocalValidatorFactoryBean()
    }

    @Bean
    fun customValidators(): List<Validator> {
        return listOf(
            // 커스텀 validator용
            // TransactionValidator(),
            // MemberValidator() 등
        )
    }
}

@RestControllerAdvice
class GlobalValidationAdvice(
    private val jsrValidator: LocalValidatorFactoryBean,
    private val customValidators: List<Validator>
) {

    /**
     * WebDataBinder에 JSR-303 및 커스텀 Validator를 등록합니다.
     *
     * @param binder WebDataBinder
     * @since 2025-06-20
     */
    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.addValidators(jsrValidator)

        val target = binder.target?.javaClass ?: return

        customValidators
            .filter { it.supports(target) }
            .forEach { binder.addValidators(it) }
    }
}