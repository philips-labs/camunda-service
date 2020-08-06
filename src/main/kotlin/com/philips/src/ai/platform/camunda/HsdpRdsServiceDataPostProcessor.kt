package com.philips.src.ai.platform.camunda

import org.springframework.cloud.cloudfoundry.CloudFoundryRawServiceData
import org.springframework.cloud.cloudfoundry.ServiceDataPostProcessor
import java.util.logging.Logger

class HsdpRdsServiceDataPostProcessor : ServiceDataPostProcessor {
    private val logger = Logger.getLogger(this.javaClass.name)

    override fun process(serviceData: CloudFoundryRawServiceData): CloudFoundryRawServiceData {
        logger.info("Correcting incorrect HSDP tags")
        val hsdpRdsDataList = serviceData["hsdp-rds"]
        if (hsdpRdsDataList != null) {
            for (hsdpRdsData in hsdpRdsDataList) {
                val plan = hsdpRdsData["plan"] as String?
                if (plan != null && plan.toLowerCase().contains("postgres")) {
                    hsdpRdsData["tags"] = listOf("PostgreSQL")
                }
            }
        }
        logger.info("Corrected HSDP tags: ${hsdpRdsDataList?.forEach { it["tags"] }}")
        return serviceData
    }
}