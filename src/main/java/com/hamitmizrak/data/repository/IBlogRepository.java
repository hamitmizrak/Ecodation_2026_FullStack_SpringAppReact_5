package com.hamitmizrak.data.repository;

import com.hamitmizrak.data.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBlogRepository extends JpaRepository<BlogEntity,Long> {
    // Ek sorgular gerekirse yazacağız
}
