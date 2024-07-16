package com.side.tiggle.batch

import org.springframework.data.jpa.repository.JpaRepository

interface ExampleRepository: JpaRepository<Example, Long> {
}