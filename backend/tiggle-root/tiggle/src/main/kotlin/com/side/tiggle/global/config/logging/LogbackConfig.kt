package com.side.tiggle.global.config.logging

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import ch.qos.logback.core.util.FileSize
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.nio.file.Files
import java.nio.file.Paths

@Configuration
class LogbackConfig {

    @Value("\${log.rolling.directory}")
    private lateinit var logDirectory: String

    @Value("\${log.rolling.file-name}")
    private lateinit var logFileName: String

    @Value("\${log.rolling.pattern}")
    private lateinit var logPattern: String

    @Value("\${log.rolling.max-history}")
    private var maxHistory: Int = 0

    @Value("\${log.rolling.total-size-cap}")
    private lateinit var totalSizeCap: String

    @PostConstruct
    fun configure() {
        val context = initializeLoggerContext()
        createLogDirectory()

        val consoleAppender = createConsoleAppender(context)
        val fileAppender = createFileAppender(context)

        configureRootLogger(context, consoleAppender, fileAppender)
    }

    private fun initializeLoggerContext(): LoggerContext {
        val factory = LoggerFactory.getILoggerFactory()
        if (factory !is LoggerContext) {
            throw IllegalStateException(
                "LoggerFactory는 LoggerContext여야 합니다. 현재 타입: ${factory::class.java.name}"
            )
        }

        return factory
    }

    private fun createLogDirectory() {
        val logPath = Paths.get(logDirectory)
        try {
            if (!Files.exists(logPath)) {
                Files.createDirectories(logPath)
            }
        } catch (e: Exception) {
            throw RuntimeException("로그 디렉토리 생성 실패", e)
        }
    }

    private fun createConsoleAppender(context: LoggerContext): ConsoleAppender<ILoggingEvent> {
        val appender = ConsoleAppender<ILoggingEvent>()
        appender.context = context
        appender.encoder = createEncoder(context)
        appender.start()
        return appender
    }

    private fun createFileAppender(context: LoggerContext): RollingFileAppender<ILoggingEvent> {
        val appender = RollingFileAppender<ILoggingEvent>()
        appender.context = context
        appender.file = "$logDirectory/$logFileName"
        appender.isAppend = true
        appender.encoder = createEncoder(context)

        val rollingPolicy = createRollingPolicy(context, appender)
        appender.rollingPolicy = rollingPolicy
        rollingPolicy.start()

        appender.start()
        return appender
    }

    private fun createEncoder(context: LoggerContext): PatternLayoutEncoder {
        val encoder = PatternLayoutEncoder()
        encoder.context = context
        encoder.pattern = logPattern
        encoder.start()
        return encoder
    }

    private fun createRollingPolicy(
        context: LoggerContext,
        parent: RollingFileAppender<ILoggingEvent>
    ): TimeBasedRollingPolicy<ILoggingEvent> {
        val policy = TimeBasedRollingPolicy<ILoggingEvent>()
        policy.context = context
        policy.setParent(parent)
        policy.fileNamePattern = createRollingFileNamePattern()
        policy.maxHistory = maxHistory
        policy.setTotalSizeCap(FileSize.valueOf(totalSizeCap))
        return policy
    }

    private fun createRollingFileNamePattern(): String {
        val baseName = if (logFileName.endsWith(".log")) {
            logFileName.removeSuffix(".log")
        } else {
            logFileName
        }
        return "$logDirectory/${baseName}.%d{yyyy-MM-dd}.log"
    }

    private fun configureRootLogger(
        context: LoggerContext,
        consoleAppender: ConsoleAppender<ILoggingEvent>,
        fileAppender: RollingFileAppender<ILoggingEvent>
    ) {
        val logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)
        if (logger is ch.qos.logback.classic.Logger) {
            val rootLogger = logger as ch.qos.logback.classic.Logger
            rootLogger.level = Level.INFO
            rootLogger.addAppender(consoleAppender)
            rootLogger.addAppender(fileAppender)
        }
    }
}