package com.github.axinger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.axinger.annotation.DS_2;
import com.github.axinger.domain.SysAnimalEntity;
import com.github.axinger.mapper.SysAnimalMapper;
import com.github.axinger.service.SysAnimalService;
import org.springframework.stereotype.Service;

/**
 * @author xing
 * @description 针对表【sys_animal】的数据库操作Service实现
 * @createDate 2024-12-28 15:27:03
 */
@Service
@DS_2
public class SysAnimalServiceImpl extends ServiceImpl<SysAnimalMapper, SysAnimalEntity>
        implements SysAnimalService {

}




