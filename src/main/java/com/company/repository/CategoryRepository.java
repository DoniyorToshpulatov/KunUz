package com.company.repository;

import com.company.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByKey(String key);

    @Transactional
    @Modifying
    @Query("update ArticleTypeEntity set visible =: bool where id =: aId")
    void updateVisible(@Param("bool") Boolean bool, @Param("aId") Integer aId);

    List<CategoryEntity> findAllByVisible(boolean b);

    Optional<CategoryEntity> findByIdAndVisible(Integer regionId, boolean b);

}
