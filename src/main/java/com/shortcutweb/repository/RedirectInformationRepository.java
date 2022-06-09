package com.shortcutweb.repository;

import com.shortcutweb.entity.RedirectInformation;
import com.shortcutweb.entity.RedirectUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;

public interface RedirectInformationRepository extends JpaRepository<RedirectInformation, Long> {

    @Query("select r from RedirectInformation r where r.redirectUrl = :redirectUrl and r.redirectDate between :startDate and :endDate order by r.redirectDate asc")
    List<RedirectInformation> searchByConvertUrl(@Param("redirectUrl") RedirectUrl convertUrl,
                                                 @Param("startDate") LocalDate redirectDateStart,
                                                 @Param("endDate") LocalDate redirectDateEnd);

    @Query("select r from RedirectInformation r where r.redirectUrl = :redirectUrl and r.redirectDate = :searchDate")
    List<RedirectInformation> searchByConvertUrl(@Param("redirectUrl") RedirectUrl redirectUrl, @Param("searchDate") LocalDate redirectDate);


}