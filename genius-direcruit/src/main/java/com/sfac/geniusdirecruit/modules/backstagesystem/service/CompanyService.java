package com.sfac.geniusdirecruit.modules.backstagesystem.service;

import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Company;

import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:24
 * 概要：
 * XXXXX
 */
public interface CompanyService {
    List<Company> selectCompanies();

    PageInfo<Company> getCompaniesBySearchBean(SearchBean searchBean);

    Company getCompanyByCompanyId(int companyId);

    ResultEntity<Company> editCompany(Company company);

    ResultEntity<Company> insertCompany(Company company);

    Company getCompanyByUser();

    ResultEntity<Company> editorAddCompany(Company company);
}
