package com.yuhan.rent.service;

import com.yuhan.rent.entity.Office;

import java.util.List;

/**
 * @author yuhan
 * @date 07.05.2021 - 10:27
 * @purpose
 */
public interface OfficeService {

    public List<Office> findAll();

    public Office findById(int officeUid);

    public String CreateOffice(Office office);

    public void deleteOffice(int officeUid);

    public String update(Office office);


}
