package com.philips.src.ai.platform.camunda

import io.pivotal.cfenv.core.CfCredentials
import io.pivotal.cfenv.core.CfService
import io.pivotal.cfenv.spring.boot.CfEnvProcessor
import io.pivotal.cfenv.spring.boot.CfEnvProcessorProperties
import java.util.logging.Logger

class HSDPDatabaseConfig: CfEnvProcessor {
    private val logger = Logger.getLogger(this.javaClass.name)
    private var serviceName = ""


    override fun accept(service: CfService?): Boolean {
        val exists = service?.existsByLabelStartsWith("hsdp-rds") == true
        if(exists) serviceName = service?.name ?: ""
        return exists
    }

    override fun process(cfCredentials: CfCredentials?, properties: MutableMap<String, Any>?) {
        val host = cfCredentials?.host
        val url= if(serviceName.contains("-ms-"))
            "jdbc:mysql://${host}:3306/camundadb?createDatabaseIfNotExist=true"
        else
            "jdbc:postgresql://${host}:5432/camundadb"
        properties?.put("spring.datasource.url", url)
        properties?.put("spring.datasource.username", "${cfCredentials?.username}")
        properties?.put("spring.datasource.password", "${cfCredentials?.password}")
        println(properties)
        logger.info("Datasource properties updated. url=$url")
    }

    override fun getProperties(): CfEnvProcessorProperties {
        return CfEnvProcessorProperties.builder().propertyPrefixes("spring.datasource")
                .serviceName(serviceName).build()
    }
}