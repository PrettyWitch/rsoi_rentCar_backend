//package com.yuhan.rent.repository;
//
//import com.yuhan.rent.entity.OfficeCars;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//import java.util.Optional;
//
///**
// * @author yuhan
// * @date 01.06.2021 - 16:24
// * @purpose
// */
//public interface OfficeCarsRepository extends JpaRepository<OfficeCars, Integer> {
//    public List<OfficeCars> findByOfficeUid(int officeUid);
//
//    public List<OfficeCars> findByCarUid(int carUid);
//
//    @Query(value = "select c from OfficeCars c where c.officeUid = :officeUid and c.carUid = :carUid")
//    public Optional<OfficeCars> findByOfficeUidAndCarUid(@Param("officeUid") int officeUid, @Param("carUid") int carUid);
//
//
//    @Modifying
//    @Query("delete from OfficeCars p where p.officeUid = :officeUid and p.carUid = :carUid")
//    public void deleteCar(@Param("officeUid") int officeUid, @Param("carUid") int carUid);
//
//}
