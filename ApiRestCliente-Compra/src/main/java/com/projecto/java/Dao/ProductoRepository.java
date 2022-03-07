package com.projecto.java.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projecto.java.model.Producto;

@Repository
public interface ProductoRepository  extends CrudRepository<Producto, Long>{

}
