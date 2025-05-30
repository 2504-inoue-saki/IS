package com.example.IS.service;

import com.example.IS.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchService {
    @Autowired
    BranchRepository branchRepository;

}
