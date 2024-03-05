package com.example.app3.m1.repo;

import com.example.app3.m1.entity.Courier;
import com.example.app3.m1.entity.dto.BaseCourierDTO;
import com.example.app3.m1.entity.embadedable.CourierPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourierRepository extends JpaRepository<Courier, Long> {

    <T extends BaseCourierDTO> T findById(Long id, Class<T> clazz);

    <T extends BaseCourierDTO> List<T> findAllBy(Class<T> clazz);


    @Query("""
            select c.courierPackages
            FROM Courier c
                        
            WHERE
            c.id = ?1
            """)
    List<CourierPackage> findCourierPackagesById(Long id);


    @Query(value = """
            SELECT c.package_id FROM public.courier_courier_packages c
            WHERE c.courier_id = ?  ORDER BY c.insert_date ASC LIMIT 1;
            """, nativeQuery = true)
    Long findCourierPackagesSelectPackageIdById(Long id);


}
