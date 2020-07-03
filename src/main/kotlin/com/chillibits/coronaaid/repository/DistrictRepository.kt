package com.chillibits.coronaaid.repository

import com.chillibits.coronaaid.model.db.District
import com.chillibits.coronaaid.model.dto.DistrictAnalyticsDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DistrictRepository: JpaRepository<District, Int> {

    @Query(
            "SELECT " +
            "NEW com.chillibits.coronaaid.model.dto.DistrictAnalyticsDto(dis.id, dis.name, dis.city, 0.0+ST_Area(dis.geometry), dis.postalCode, COUNT(inf), dis.geometry) " +
            "FROM District dis LEFT JOIN Infected inf ON dis.city=inf.city " +
            "WHERE inf.postalCode=dis.postalCode AND true=ST_Contains(dis.geometry, ST_SRID(POINT(inf.lon, inf.lat), 4326)) " +
            "GROUP BY dis.id")
    fun getAnalyzedDistrictData() : Set<DistrictAnalyticsDto>

}