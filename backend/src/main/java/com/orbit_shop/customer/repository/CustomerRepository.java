package com.orbit_shop.customer.repository;

import com.orbit_shop.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
