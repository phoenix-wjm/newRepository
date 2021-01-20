package com.sfac.geniusdirecruit.modules.backstagesystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.dao.CompanyDao;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Company;
import com.sfac.geniusdirecruit.modules.backstagesystem.service.CompanyService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:24
 * 概要：
 * XXXXX
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyDao companyDao;
    @Override
    public List<Company> selectCompanies() {
        return companyDao.selectCompanies();
    }

    @Override
    public PageInfo<Company> getCompaniesBySearchBean(SearchBean searchBean) {
        searchBean.initSearchBean();
        PageHelper.startPage(searchBean.getCurrentPage(), searchBean.getPageSize());
        return new PageInfo<>(Optional
                .ofNullable(companyDao.getCompaniesBySearchBean(searchBean))
                .orElse(Collections.emptyList()));
    }

    @Override
    public Company getCompanyByCompanyId(int companyId) {
        return companyDao.getCompanyByCompanyId(companyId);
    }

    @Override
    public ResultEntity<Company> editCompany(Company company) {
        companyDao.editCompany(company);
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                "update success",company);
    }

    @Override
    public ResultEntity<Company> insertCompany(Company company) {
        companyDao.insertCompany(company);
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                "insert success",company);
    }

    @Override
    public Company getCompanyByUser() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        int userId = (int)session.getAttribute("userId");
        System.err.println("-------------------"+userId);
        Company company = companyDao.selectCompanyByUserId(userId);
        return company;
    }

    @Override
    public ResultEntity<Company> editorAddCompany(Company company) {
        if (company.getCompanyId()==null){
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            int userId = (int)session.getAttribute("userId");
            company.setUserId(userId);
            companyDao.insertCompany(company);
        }else{
            companyDao.editCompany(company);
        }
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                "success",company);
    }
}
