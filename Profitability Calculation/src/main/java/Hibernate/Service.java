package Hibernate;

import DataBaseEntity.DataBaseEntity;

import java.util.List;

public class Service {

    public Service(){

    }

    public List findByName(IDao d, String name){
        return d.findAllByName(name);

    }

    public List getAll(IDao d){

        return d.getAll();
    }

    public void saveObj(IDao d, DataBaseEntity dataBaseEntity){
        d.save(dataBaseEntity);
    }

    public void updateObj(IDao d, DataBaseEntity dataBaseEntity){
        d.update(dataBaseEntity);
    }

    public void deleteObj(IDao d, DataBaseEntity dataBaseEntity){
        d.delete(dataBaseEntity);
    }
}
