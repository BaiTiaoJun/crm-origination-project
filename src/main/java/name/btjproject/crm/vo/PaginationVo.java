package name.btjproject.crm.vo;

import java.util.List;

public class PaginationVo<T> {
    private List<T> dataList;
    private int total;

    public List<T> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int pageTotal) {
        this.total = pageTotal;
    }
}