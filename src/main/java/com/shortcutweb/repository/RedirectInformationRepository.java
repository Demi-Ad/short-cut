package com.shortcutweb.repository;

import com.shortcutweb.entity.RedirectInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;

public interface RedirectInformationRepository extends JpaRepository<RedirectInformation, Long> {
    @Query("select r from RedirectInformation r where r.redirectUrl.convertUrl = :convertUrl")
    List<RedirectInformation> searchByConvertUrl(@NonNull @Param("convertUrl") String convertUrl);

    @Query("select r from RedirectInformation r where r.redirectUrl.convertUrl = :convertUrl and r.redirectDate between :startDate and :endDate")
    List<RedirectInformation> searchByConvertUrl(@Param("convertUrl") String convertUrl,
                                                 @Param("startDate") LocalDate redirectDateStart,
                                                 @Param("endDate") LocalDate redirectDateEnd);

    @Query("select r from RedirectInformation r where r.redirectUrl.convertUrl = :convertUrl and r.redirectDate = :searchDate")
    List<RedirectInformation> searchByConvertUrl(@Param("convertUrl") String convertUrl, @Param("searchDate") LocalDate redirectDate);


}