package com.scxxwb.net.admin.modules.community.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.scxxwb.net.admin.common.annotation.DataFilter;
import com.scxxwb.net.admin.common.utils.Constant;
import com.scxxwb.net.admin.common.utils.PageUtils;
import com.scxxwb.net.admin.common.utils.Query;
import com.scxxwb.net.admin.modules.community.dao.CommunitySysDeptDao;
import com.scxxwb.net.admin.modules.community.entity.CommunitySysDeptEntity;
import com.scxxwb.net.admin.modules.community.service.CommunitySysDeptService;

import com.scxxwb.net.admin.modules.sys.entity.SysUserEntity;
import com.scxxwb.net.admin.modules.sys.entity.TWbAreaEntity;
import com.scxxwb.net.admin.modules.sys.service.SysUserService;
import com.scxxwb.net.admin.modules.sys.service.TWbAreaService;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.scxxwb.net.admin.common.kafka.KafkaProducer;

@Service("communitySysDeptService")
public class CommunitySysDeptServiceImpl extends ServiceImpl<CommunitySysDeptDao, CommunitySysDeptEntity> implements CommunitySysDeptService {
    @Autowired
    protected KafkaProducer kafkaProducer;
    @Autowired
    protected CommunitySysDeptService communitySysDeptService;
    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected TWbAreaService tWbAreaService;

    @Override
    /*@DataFilter(subDept = true, user = false)*/
    public PageUtils queryPage(Map<String, Object> params) {
        //数据权限管理
        String deptIds = null;
        if(params.get(Constant.SQL_FILTER) != null){
            String num =  params.get(Constant.SQL_FILTER).toString();
            num = num.substring(13, num.length() - 2);
            String[] strs = num.split(",");
            List<String> list = Arrays.asList(strs);
            deptIds = StringUtils.join(list, ',');
        }

        //查询方法
        Page<CommunitySysDeptEntity> page = this.selectPage(
                new Query<CommunitySysDeptEntity>(params).getPage(),
                new EntityWrapper<CommunitySysDeptEntity>()
                        .like(StringUtils.isNotBlank((String)params.get("communityName")), "name", String.valueOf(params.get("communityName")))
                        .addFilterIfNeed(StringUtils.isNotBlank((String)params.get("status")), "status = " + String.valueOf(params.get("status")))
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, "dept_id in (" + deptIds + ")")
                        .addFilter("del_flag =" + 0)
                        .orderBy("status DESC, cre_time DESC")
        );
        List<CommunitySysDeptEntity> list = page.getRecords();
        //社区名称长度控制在15个字
        for(CommunitySysDeptEntity communitySysDeptEntity : list) {
            String name = communitySysDeptEntity.getName();
            if(name != null && name != "" && name.length() >= 15 ){
                    String newName = name.substring(0, 15) + "...";
                    communitySysDeptEntity.setName(newName);
            }
            //获取创建用户id
            long id = communitySysDeptEntity.getCreUserId();
            //获取创建用户信息
            SysUserEntity sysUserEntity = sysUserService.selectById(id);
            //添加创建人名称
            communitySysDeptEntity.setCreUserName(sysUserEntity.getUserName());
            //查询省市区信息
            String province = String.valueOf(communitySysDeptEntity.getProvince());
            String city = String.valueOf(communitySysDeptEntity.getCity());
            String area = String.valueOf(communitySysDeptEntity.getArea());
            String town = String.valueOf(communitySysDeptEntity.getTown());
            StringBuffer address = new StringBuffer();
                if(province != null && province != "null" && Integer.valueOf(province) != -1){
                    TWbAreaEntity tWbAreaEntity = tWbAreaService.selectOne(
                                new EntityWrapper<TWbAreaEntity>()
                                        .addFilter("area_code=" + province)
                    );
                    if(tWbAreaEntity != null){
                        address.append(tWbAreaEntity.getName());
                    }
                }
                if(city != null && city != "null" && Integer.valueOf(city) != -1){
                    TWbAreaEntity tWbAreaEntity = tWbAreaService.selectOne(
                            new EntityWrapper<TWbAreaEntity>()
                                    .addFilter("area_code=" + city)
                    );
                    if(tWbAreaEntity != null){
                        address.append(tWbAreaEntity.getName());
                    }
                }
                if(area != null && area != "null" && Integer.valueOf(area) != -1){
                    TWbAreaEntity tWbAreaEntity = tWbAreaService.selectOne(
                            new EntityWrapper<TWbAreaEntity>()
                                    .addFilter("area_code=" + area)
                    );
                    if(tWbAreaEntity != null) {
                        address.append(tWbAreaEntity.getName());
                    }
                }
                if(town != null && town != "null" && Integer.valueOf(town) != -1){
                    TWbAreaEntity tWbAreaEntity = tWbAreaService.selectOne(
                            new EntityWrapper<TWbAreaEntity>()
                                    .addFilter("area_code=" + town)
                    );
                    if(tWbAreaEntity != null) {
                        address.append(tWbAreaEntity.getName());
                    }
                }
            communitySysDeptEntity.setAddress(String.valueOf(address));
        }
        return new PageUtils(page);
    }

    @Override
    /*@DataFilter(subDept = true, user = false)*/
    public List<CommunitySysDeptEntity> queryList(Map<String, Object> params){
        String deptIds = null;
            if(params.get(Constant.SQL_FILTER) != null){
                String num =  params.get(Constant.SQL_FILTER).toString();
                num = num.substring(13, num.length() - 2);
                String[] strs = num.split(",");
                List<String> list = Arrays.asList(strs);
                deptIds = StringUtils.join(list, ',');
            }
        List<CommunitySysDeptEntity> deptList =
                this.selectList(new EntityWrapper<CommunitySysDeptEntity>()
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, "dept_id in (" + deptIds + ")"));

        return deptList;
    }
}
