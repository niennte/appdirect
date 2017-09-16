package com.samplecompany.teapot.authorization.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.samplecompany.teapot.authorization.entity.User;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findOneByAppDirectOpenId(String appDirectOpenId);

    void deleteUserByAppDirectUuidAndAccountId(String appDirectUuid, Long accountId);

}
