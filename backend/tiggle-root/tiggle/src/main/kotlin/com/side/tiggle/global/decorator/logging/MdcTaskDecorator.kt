package com.side.tiggle.global.decorator.logging

import org.slf4j.MDC
import org.springframework.core.task.TaskDecorator
import org.springframework.stereotype.Component

@Component
class MdcTaskDecorator : TaskDecorator {

    override fun decorate(runnable: Runnable): Runnable {
        val contextMap = MDC.getCopyOfContextMap()
        return Runnable {
            val originalContext = MDC.getCopyOfContextMap()
            if (contextMap != null) {
                MDC.setContextMap(contextMap)
            }
            try {
                runnable.run()
            } finally {
                if (originalContext != null) {
                    MDC.setContextMap(originalContext)
                    } else {
                        MDC.clear()
                    }
            }
        }
    }
}

