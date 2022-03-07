package com.projecto.java.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projecto.java.model.Venta;

@Repository
public interface VentasRepository extends CrudRepository<Venta, Long> {

}
