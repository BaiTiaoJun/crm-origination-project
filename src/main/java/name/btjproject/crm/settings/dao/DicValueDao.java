package name.btjproject.crm.settings.dao;

import name.btjproject.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getDicValue(String typeCode);
}
