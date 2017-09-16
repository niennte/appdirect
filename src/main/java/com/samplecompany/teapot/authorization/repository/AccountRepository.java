package com.samplecompany.teapot.authorization.repository;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.samplecompany.teapot.authorization.entity.Account;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
}

