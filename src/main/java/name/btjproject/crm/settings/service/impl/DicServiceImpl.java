package name.btjproject.crm.settings.service.impl;

import name.btjproject.crm.settings.dao.DicTypeDao;
import name.btjproject.crm.settings.dao.DicValueDao;
import name.btjproject.crm.settings.domain.DicType;
import name.btjproject.crm.settings.domain.DicValue;
import name.btjproject.crm.settings.service.DicService;
import name.btjproject.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {
    private final DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private final DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, Object> getAll() {
        List<DicType> listTyp = dicTypeDao.getDicType();
        Map<String, Object> map = new HashMap<>();
        for (DicType dicType:listTyp) {
            String code = dicType.getCode();
            List<DicValue> listVal = dicValueDao.getDicValue(code);
            map.put(code, listVal);
        }
        return map;
    }
}
