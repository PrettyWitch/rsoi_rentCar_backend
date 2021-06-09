package com.yuhan.rent.service;

import com.yuhan.rent.entity.Office;
import com.yuhan.rent.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author yuhan
 * @date 06.05.2021 - 18:53
 * @purpose
 */
@Service
public class OfficeServiceImpl implements OfficeService {

    @Autowired
    OfficeRepository officeRepository;


    @Override
    public List<Office> findAll(){
        return officeRepository.findAll();
    }

    @Override
    public Office findById(int officeUid){
        return officeRepository.findByOfficeUid(officeUid)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Office with uid %s not found", officeUid)));
    }

    @Override
        public String CreateOffice(Office office){
        int officeUid = (int) (Math.random() * 100);
        office.setOfficeUid(officeUid);
        officeRepository.save(office);
        if (office.getId() > 0){
            return "success";
        }else {
            return "error";
        }
    }

    @Override
    public void deleteOffice( int officeUid){
        officeRepository.deleteById(officeUid);
    }

    @Override
    public String update( Office office){
        Office o  = officeRepository.save(office);
        if (o.getId() > 0 ){
            return "success";
        }else {
            return "error";
        }
    }


}
